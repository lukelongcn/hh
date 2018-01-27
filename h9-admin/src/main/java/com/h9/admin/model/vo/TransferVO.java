package com.h9.admin.model.vo;

import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.Transactions;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import static com.h9.common.db.entity.BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE;
import static com.h9.common.db.entity.BalanceFlow.BalanceFlowTypeEnum.USER_TRANSFER;

/**
 * Created by itservice on 2018/1/27.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("转账记录")
public class TransferVO {
    @ApiModelProperty("")
    private Long id;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("付款人")
    private String originUser;
    @ApiModelProperty("收款")
    private String targetUser;
    @ApiModelProperty("金额")
    private String money;
    @ApiModelProperty("时间")
    private String time;

    public TransferVO(Transactions transactions){
        Long balanceFlowType = transactions.getBalanceFlowType();
        if (balanceFlowType.equals(USER_TRANSFER.getId())) {
            this.setType(USER_TRANSFER.getName());
        }else if(balanceFlowType.equals(RED_ENVELOPE.getId())){
            this.setType(RED_ENVELOPE.getName());
        }
        String nickName = transactions.getNickName();
        if (StringUtils.isNotBlank(nickName)) {
            this.setOriginUser(nickName + "(" + transactions.getPhone() + ")");
        }

        String targetNickName = transactions.getTargetNickName();
        if (StringUtils.isNotBlank(targetNickName)) {
            this.setTargetUser(nickName + "(" + transactions.getPhone() + ")");
        }

        this.setMoney(MoneyUtils.formatMoney(transactions.getTransferMoney()));
        this.setId(transactions.getId());
        this.setTime(DateUtil.formatDate(transactions.getCreateTime(), DateUtil.FormatType.MINUTE));
    }
}
