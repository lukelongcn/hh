package com.h9.admin.model.vo;

import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.community.StickReward;
import com.h9.common.utils.DateUtil;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/14
 */
@Data
public class StickRewardVO {
    private Long id;
    private String ip;
    private BigDecimal reward = new BigDecimal(0);
    private long userId;
    private long stickId;
    private String createTime;
    private String nickName;
    private String payMethod;

    public StickRewardVO(StickReward stickReward){
        BeanUtils.copyProperties(stickReward,this);
        this.userId = stickReward.getUser().getId();
        this.stickId = stickReward.getStick().getId();
        this.createTime = DateUtil.formatDate(stickReward.getCreateTime(), DateUtil.FormatType.SECOND);
        this.nickName = stickReward.getUser().getNickName();
        payMethod = BalanceFlow.BalanceFlowTypeEnum.BALANCE_PAY.getName();
    }
}
