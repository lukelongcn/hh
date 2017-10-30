package com.h9.api.service;

import com.h9.api.model.vo.BalanceFlowVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.VCoinsFlow;
import com.h9.common.db.repo.BalanceFlowRepository;
import com.h9.common.db.repo.VCoinsFlowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * BalanceService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 16:50
 */
@Component
public class AccountService {
    @Resource
    private BalanceFlowRepository balanceFlowRepository;
    @Resource
    private VCoinsFlowRepository vCoinsFlowRepository;

    
    public Result getBalanceFlow(Long userId,int page,int limit){
        PageRequest pageRequest = balanceFlowRepository.pageRequest(page, limit);
        Page<BalanceFlow> balanceFlows = balanceFlowRepository.findByBalance(userId,pageRequest);
        PageResult<BalanceFlow> flowPageResult = new PageResult<>(balanceFlows);
        return Result.success(flowPageResult.result2Result(BalanceFlowVO::new));
    }

    public Result getVCoinsFlow(Long userId,int page,int limit){
        PageRequest pageRequest = balanceFlowRepository.pageRequest(page, limit);
        Page<VCoinsFlow> balanceFlows = vCoinsFlowRepository.findByBalance(userId,pageRequest);
        PageResult<VCoinsFlow> flowPageResult = new PageResult<>(balanceFlows);
        return Result.success(flowPageResult.result2Result(BalanceFlowVO::new));
    }

}
