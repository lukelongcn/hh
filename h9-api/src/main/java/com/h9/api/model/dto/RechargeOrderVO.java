package com.h9.api.model.dto;

import com.h9.common.db.entity.RechargeOrder;
import com.h9.common.utils.DateUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * RechargeOrderVO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/12
 * Time: 10:19
 */
@Data
public class RechargeOrderVO {
    private Long id;
    private String time;
    private BigDecimal money;

    public RechargeOrderVO() {
    }

    public RechargeOrderVO(RechargeOrder rechargeOrder) {
        id = rechargeOrder.getId();
        time = DateUtil.formatDate(rechargeOrder.getCreateTime(), DateUtil.FormatType.MINUTE);
        money = rechargeOrder.getMoney();
    }
}
