package com.h9.api.service.handler;

import com.h9.api.model.dto.PayNotifyVO;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.RechargeOrder;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.repo.PayInfoRepository;
import com.h9.common.db.repo.RechargeOrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * RechargePayHandler:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 20:41
 */
@Component
public class RechargePayHandler extends AbPayHandler {

    @Resource
    private PayInfoRepository payInfoRepository;
    
    @Resource
    private RechargeOrderRepository rechargeOrderRepository;
     
    
    @Resource
    private CommonService commonService;
    
    

    @Override
    @Transactional
    public boolean callback(PayNotifyVO payNotifyVO, PayInfo payInfo) {
        Integer status = payInfo.getStatus();
        if(status  == 2){
            return true;
        }
        payInfo.setStatus(2);
        payInfoRepository.save(payInfo);
        RechargeOrder rechargeOrder = rechargeOrderRepository.findOne(payInfo.getOrderId());
        rechargeOrder.setStatus(2);
        rechargeOrderRepository.saveAndFlush(rechargeOrder);
        Result result = commonService.setBalance(rechargeOrder.getUser_id(), rechargeOrder.getMoney(), BalanceFlow.BalanceFlowTypeEnum.Recharge.getId(), rechargeOrder.getId(), null, "余额充值");
        return true;
    }
}
