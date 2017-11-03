package com.h9.api.service;

import com.h9.api.model.vo.BalanceFlowVO;
import com.h9.api.model.vo.MyCouponsVO;
import com.h9.api.model.vo.UserAccountInfoVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:账号
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
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;

    @Resource
    private UserRepository userRepository;
    
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

    public BigDecimal getAccountBalance(Long userId){
        UserAccount userAccount = userAccountRepository.findOne(userId);
        if(userAccount == null) return new BigDecimal(0);

        return userAccount.getBalance();
    }

    public Result accountInfo(Long userId) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        User user = userRepository.findOne(userId);
        Object cardCount = orderItemReposiroty.findCardCount(userId, Orders.orderTypeEnum.DIDI_COUPON.getCode());

        UserAccountInfoVO userAccountInfoVO = new UserAccountInfoVO(user, userAccount,cardCount+"");
        return Result.success(userAccountInfoVO);
    }

    public Result couponeList(Long userId) {
//        User user = userRepository.findOne(userId);
        List<OrderItems> itemsList = orderItemReposiroty.findByUser(userId, Orders.orderTypeEnum.DIDI_COUPON.getCode());

        List<MyCouponsVO> voList = new ArrayList<>();
        for(OrderItems item : itemsList){
            MyCouponsVO vo = new MyCouponsVO(item,item.getGoods());
            voList.add(vo);
        }
        return Result.success(voList);
    }
}
