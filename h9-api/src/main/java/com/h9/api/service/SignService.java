package com.h9.api.service;

import com.h9.api.model.vo.SignVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.db.repo.UserSignRepository;
import com.h9.common.utils.DateUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;

/**
 * Created by 李圆 on 2018/1/2
 */
@Service
public class SignService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserSignRepository userSignRepository;

    public Result sign(long userId) {
        User user = userRepository.findOne(userId);
        UserSign userSign = userSignRepository.findLastSign(userId);
        // 第一次签到
        if (userSign == null){
            user.setSignDays(1);
            user.setSignCount(user.getSignCount()+1);
            user = userRepository.saveAndFlush(user);

            UserSign userSign1 = new UserSign();
            userSign1.setUserId(userId);
            userSign1.setCashBack(cashBack(user));
            userSign1 = userSignRepository.saveAndFlush(userSign1);

            SignVO signVO = new SignVO(user,userSign1);
            return Result.success("签到成功",signVO);
         }
        //获取当前时间
        Calendar checkdateCalendar = Calendar.getInstance();
        //获取用户上次签到时间
        Date checkDate = userSign.getCreateTime();
        Date today = DateUtil.getTimesMorning();
        // 判断用户上次签到时间是否是在今天凌晨之后
        if(checkDate.after(today)){
            return Result.success("您今天已经签过到了");
        }
        // 如果上次签到是今天凌晨之前，说明没有连续签到
        if(checkdateCalendar.before(DateUtil.getYesterdaymorning())){
            //将签到天数
            // 归为1
            user.setSignDays(1);
        }else{
            user.setSignDays(user.getSignDays()+1);
        }
        user.setSignCount(user.getSignCount()+1);
        user = userRepository.saveAndFlush(user);

        UserSign userSign1 = new UserSign();
        userSign1.setUserId(userId);
        userSign1.setCashBack(cashBack(user));
        userSign1 = userSignRepository.saveAndFlush(userSign1);

        SignVO signVO = new SignVO(user,userSign1);
        return Result.success("签到成功",signVO);
    }

    // 签到奖励规则
    public BigDecimal cashBack(User user){
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
}
