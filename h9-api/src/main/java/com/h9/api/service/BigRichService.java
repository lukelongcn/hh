package com.h9.api.service;

import com.h9.api.model.vo.BigRichRecordVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.OrdersLotteryActivity;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.OrdersLotteryActivityRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserRepository;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        UserAccount userAccount= userAccountRepository.findByUserId(userId);
        User user  = userRepository.findOne(userId);
        PageResult<OrdersLotteryActivity> pageResult = ordersLotteryActivityRepository.findAllDetail(page,limit);
        if (pageResult != null){
            PageResult<BigRichRecordVO> pageResultRecord = pageResult.result2Result(e->activityToRecord(e));
        }
        return null;
    }

    private BigRichRecordVO activityToRecord(OrdersLotteryActivity e) {
        return null;
    }
}
