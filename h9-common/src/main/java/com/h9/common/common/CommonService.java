package com.h9.common.common;

import com.h9.common.base.Result;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.BalanceFlowType;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.db.entity.UserRecord;
import com.h9.common.db.repo.BalanceFlowRepository;
import com.h9.common.db.repo.BalanceFlowTypeRepository;
import com.h9.common.db.repo.BannerTypeRepository;
import com.h9.common.db.repo.UserAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * CommonService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/6
 * Time: 14:50
 */
@Component
public class CommonService {

    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private BalanceFlowTypeRepository balanceFlowTypeRepository;
    @Resource
    private BalanceFlowRepository balanceFlowRepository;

    @Transactional
    public Result setBalance(Long userId, BigDecimal money, Long typeId,Long orderId,String orderNo,String remarks){
        UserAccount userAccount = userAccountRepository.findByUserIdLock(userId);
        BigDecimal balance = userAccount.getBalance();
        BigDecimal newbalance = balance.add(money);
        if(newbalance.compareTo(new BigDecimal(0))<0){
            return Result.fail("余额不足");
        }
        userAccount.setBalance(newbalance);
        BalanceFlow balanceFlow = new BalanceFlow();
        balanceFlow.setBalance(newbalance);
        balanceFlow.setMoney(money);
        balanceFlow.setFlowType(typeId);
        balanceFlow.setOrderId(orderId);
        balanceFlow.setOrderNo(orderNo);
        balanceFlow.setRemarks(remarks);
        balanceFlow.setUserId(userId);

        userAccountRepository.save(userAccount);
        balanceFlowRepository.save(balanceFlow);
        return Result.success();
    }


}
