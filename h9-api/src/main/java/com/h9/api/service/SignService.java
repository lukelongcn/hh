package com.h9.api.service;

import com.h9.api.model.vo.SelfSignVO;
import com.h9.api.model.vo.SignVO;
import com.h9.api.model.vo.UserSignMessageVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ServiceException;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserSign;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.db.repo.UserSignRepository;
import com.h9.common.utils.DateUtil;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 李圆 on 2018/1/2
 */
@Service
public class SignService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserSignRepository userSignRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private UserAccountRepository userAccountRepository;

    private Logger logger = Logger.getLogger(this.getClass());


    @Transactional
    @Description("每日签到")
    public Result sign(long userId) {
        User user = userRepository.findOne(userId);
        UserSign userSign = userSignRepository.findLastSign(userId);
        // 第一次签到
        if (userSign == null){
            user.setSignDays(1);
            user.setSignCount(1);
            user = userRepository.saveAndFlush(user);
         } else{
            //获取当前时间
            Calendar checkdateCalendar = Calendar.getInstance();
            //获取用户上次签到时间
            Date checkDate = userSign.getCreateTime();
            Date today = DateUtil.getTimesMorning();
            // 判断用户上次签到时间是否是在今天凌晨之后
            if(checkDate.after(today)){
                return Result.fail("您今天已经签过到了");
            }
            // 如果上次签到是昨天凌晨之前，说明没有连续签到
            if(checkdateCalendar.before(DateUtil.getYesterdaymorning())){
                //将签到天数
                // 归为1
                user.setSignDays(1);
            }else{
                user.setSignDays(user.getSignDays()+1);
            }
            user.setSignCount(user.getSignCount()+1);
            user = userRepository.saveAndFlush(user);
        }

        UserSign userSignNew = new UserSign();
        userSignNew.setUser(user);
        userSignNew.setCashBack(cashBack(user));
        userSignNew = userSignRepository.saveAndFlush(userSignNew);

        // 签到奖励金额加入到用户酒元余额中
        Result result = commonService.setBalance(userId,userSignNew.getCashBack(), BalanceFlow.BalanceFlowTypeEnum.SIGN.getId(),userSignNew.getId(),"","");
        if(result.getCode()==Result.FAILED_CODE){
            this.logger.errorf("签到奖励用户金额失败,msg:{0}",result.getMsg());
            throw new ServiceException("签到失败");
        }
        // 签到奖励金额累加到用户奖励金额累计中
        UserAccount userAccount = userAccountRepository.findOne(userId);
        userAccount.setCashBackMoney(userAccount.getCashBackMoney().add(userSignNew.getCashBack()));
        userAccountRepository.save(userAccount);

        return Result.success("签到成功");

    }


    /**
      * 签到奖励规则
      */
    private BigDecimal cashBack(User user){
        if (user.getSignDays() < 10){
            Double x = (Math.random()*2);
            BigDecimal bigDecimal = new BigDecimal(x);
            return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (user.getSignDays() < 30 && user.getSignDays() >= 10){
            Double x = (Math.random()*4);
            BigDecimal bigDecimal = new BigDecimal(x);
            return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if (user.getSignDays() >= 30){
            Double x=(Math.random()*8);
            BigDecimal bigDecimal = new BigDecimal(x);
            return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal(0);
    }

    /**
     * 获取签到页面信息
     * @return Result
     * @param userId 用户id
     */
    @Transactional
    @Description("最新签到列表显示十个")
    public Result getSign(long userId) {

        // 获取最新签到列表
        List<UserSign> listNewSign = userSignRepository.findListNewSign();
        List<SignVO> listSignVO = new ArrayList();
        if(!CollectionUtils.isEmpty(listNewSign)){
            listSignVO = listNewSign.stream().map(SignVO::new).collect(Collectors.toList());
        }

        // 获取头像  用户余额 奖励金  连续签到天数  总共签到天数
        User user = userRepository.findOne(userId);
        if (user == null){
            return Result.fail("用户不存在，请重新登录或注册");
        }
        UserAccount userAccount = userAccountRepository.findOne(userId);
        UserSign userSign = userSignRepository.findLastSign(userId);

        // 如果用户是第一次进入页面且今日没有签到
        if (userSign == null){
            UserSignMessageVO userSignMessageVO = new UserSignMessageVO(userAccount.getBalance(),user,
                    listSignVO,0);
            return Result.success(userSignMessageVO);
        }
        //获取用户上次签到时间
        Date checkDate = userSign.getCreateTime();
        Date today = DateUtil.getTimesMorning();
        // 如果用户不是第一次进入页面且今日没有签到
        UserSignMessageVO userSignMessageVO;
       if (checkDate.before(today)){
             userSignMessageVO = new UserSignMessageVO(userAccount.getBalance(),user,userSign,
                    listSignVO,0);
        }else{
           // 如果用户不是第一次进入页面且已签到
           userSignMessageVO = new UserSignMessageVO(userAccount.getBalance(),user,userSign,
                   listSignVO,1);
       }
        return Result.success(userSignMessageVO);
    }


    /**
     * 个人签到记录
     * @param userId 用户id
     * @param page 分页页数
     * @param limit 限制页数
     * @return
     */
    @Transactional
    @Description("个人签到记录")
    public Result selfSign(long userId, Integer page, Integer limit) {
        PageResult<UserSign> pageResult = userSignRepository.findUserSignList(userId, page, limit);
        if (pageResult == null){
            return Result.success("暂无签到记录");
        }
        return Result.success(pageResult.result2Result(SelfSignVO::new));
    }
}
