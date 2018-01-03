package com.h9.lottery.provider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * LotteryModel:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/8
 * Time: 18:15
 */
@Data
public class LotteryModel {
    /****State
     0：获取兑奖码对应奖金成功
     1：获取兑奖码对应积分成功
     2：兑奖码已兑奖
     3：兑奖码不存在
     4：接口调用错误
     */
    @JsonProperty("State")
    private int State;
    @JsonProperty("Msg")
    private String Msg;
    @JsonProperty("Bouns")
    private BigDecimal Bouns = new BigDecimal(0);
    @JsonProperty("SeedAmount")
    private BigDecimal SeedAmount = new BigDecimal(0);
    @JsonProperty("Intergal")
    private BigDecimal Intergal = new BigDecimal(0);
    @JsonProperty("PlanOID")
    private String planId;

}
