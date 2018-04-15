package com.h9.api.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
@Data
public class UserBigRichRecordVO {
    private Long ordersId;
    private String startLotteryTime;
    private String number;
    private String way;
    private Integer status;
    private String money;
}
