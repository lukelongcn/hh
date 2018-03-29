package com.h9.admin.model.vo;

import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ln on 2018/3/28.
 */
@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class BigRichListVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("期数")
    private String number;

    @ApiModelProperty("参与人数")
    private String joinCount = "0";

    @ApiModelProperty("金额")
    private String money = "0";

    @ApiModelProperty("中奖人")
    private String winnerUser = "";

    @ApiModelProperty("活动开始时间")
    private String startTime;

    @ApiModelProperty("活动结束时间")
    private String endTime;

    @ApiModelProperty("开奖时间")
    private String startLotteryTime;


    /**
     * 状态 1 启用 0禁用 2结束
     */
    @ApiModelProperty("状态 1 启用 0禁用 2结束")
    private int status;


    public BigRichListVO(OrdersLotteryActivity ordersLotteryActivity, User winnerUser,long joinCount) {
        this.setId(ordersLotteryActivity.getId());
        this.setNumber(ordersLotteryActivity.getNumber());
        this.setJoinCount(joinCount+"");
        if (winnerUser != null) {

            this.setWinnerUser(winnerUser.getPhone());
        }


        Date startTime = ordersLotteryActivity.getStartTime();
        this.setStartTime(DateUtil.formatDate(startTime, DateUtil.FormatType.MINUTE));
        Date endTime = ordersLotteryActivity.getStartTime();
        this.setEndTime(DateUtil.formatDate(endTime, DateUtil.FormatType.MINUTE));
        Date startLotteryTime = ordersLotteryActivity.getStartLotteryTime();
        this.setStartLotteryTime(DateUtil.formatDate(startLotteryTime, DateUtil.FormatType.MINUTE));
        this.setStatus(ordersLotteryActivity.getStatus());
    }
}
