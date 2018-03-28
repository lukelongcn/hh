package com.h9.api.service;

import com.h9.api.model.vo.BigRichRecordVO;
import com.h9.api.model.vo.BigRichVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.OrdersLotteryActivityRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import lombok.Data;
import lombok.Setter;
import org.apache.poi.ss.formula.ptg.MemAreaPtg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
@Service
public class BigRichService {

    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private OrdersLotteryActivityRepository ordersLotteryActivityRepository;

    public Result getRecord(long userId,Integer page, Integer limit) {
        BigRichVO bigRichVO = new BigRichVO();
        UserAccount userAccount= userAccountRepository.findByUserId(userId);
        User user  = userRepository.findOne(userId);
        PageResult<OrdersLotteryActivity> pageResult = ordersLotteryActivityRepository.findAllDetail(page,limit);
        if (pageResult != null){
            PageResult<BigRichRecordVO> pageResultRecord = pageResult.result2Result(e->activityToRecord(e));
            bigRichVO.setRecordList(pageResultRecord);
        }
        bigRichVO.setBigRichMoney(userAccount.getBigRichMoney());
        if (user.getLotteryChance() == 1){
            bigRichVO.setLotteryChance(1);
        }
        return Result.success(bigRichVO);
    }

    @Transactional
    public BigRichRecordVO activityToRecord(OrdersLotteryActivity e) {
        // 创建记录对象
        BigRichRecordVO bigRichRecordVO = new BigRichRecordVO();
        User user = userRepository.findOne(e.getWinnerUserId());
        bigRichRecordVO.setUserName(user.getNickName());
        bigRichRecordVO.setLotteryMoney(e.getMoney());
        bigRichRecordVO.setEndTime(DateUtil.formatDate(e.getEndTime(), DateUtil.FormatType.MINUTE));


        return bigRichRecordVO;
    }


}
