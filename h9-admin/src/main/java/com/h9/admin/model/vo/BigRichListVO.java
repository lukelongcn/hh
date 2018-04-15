package com.h9.admin.model.vo;

import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
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
    @ApiModelProperty("状态 ")
    private String status;

    @ApiModelProperty("状态值(数字类型) 1 为启用 0为禁用")
    private int statusInt;

    @ApiModelProperty("待开始,进行中,已结束 ")
    private String activeStatus = "";

    @ApiModelProperty("能否能禁用 true 为可以，false 为不可以")
    private Boolean canBan = false;

    @ApiModelProperty("能否可以编辑 true 为可以，false 为不可以")
    private Boolean canEdit = false;

    @ApiModelProperty("能否可以添加用户 true 为可以，false 为不可以")
    private Boolean canAddUser = false;


    public BigRichListVO(OrdersLotteryActivity ordersLotteryActivity, User winnerUser, long joinCount) {
        this.setId(ordersLotteryActivity.getId());
        this.setNumber(ordersLotteryActivity.getNumber());
        this.setJoinCount(joinCount + "");
        if (winnerUser != null) {
            this.setWinnerUser(winnerUser.getPhone());
        }

        Date startTime = ordersLotteryActivity.getStartTime();
        this.setStartTime(DateUtil.formatDate(startTime, DateUtil.FormatType.MINUTE));
        Date endTime = ordersLotteryActivity.getEndTime();
        this.setEndTime(DateUtil.formatDate(endTime, DateUtil.FormatType.MINUTE));
        Date startLotteryTime = ordersLotteryActivity.getStartLotteryTime();
        this.setStartLotteryTime(DateUtil.formatDate(startLotteryTime, DateUtil.FormatType.MINUTE));

        int st = ordersLotteryActivity.getStatus();
        OrdersLotteryActivity.statusEnum statusEnum = OrdersLotteryActivity.statusEnum.findByCode(st);
        if (statusEnum != null) {

            if (statusEnum.getCode() == OrdersLotteryActivity.statusEnum.ENABLE.getCode()) {
                statusInt = 1;
                this.setStatus("启用");
            } else {
                this.setStatus("禁用");
                statusInt = 0;
            }
        }

        //编辑按纽
        Date now = new Date();

        if (statusEnum.getCode() == OrdersLotteryActivity.statusEnum.BAN.getCode()) {
            canEdit = true;
        }

        //添加用户按纽
        if (statusEnum.getCode() == OrdersLotteryActivity.statusEnum.ENABLE.getCode()) {
            if (now.after(startTime) && startLotteryTime.after(now)) {
                canAddUser = true;
            }
        }

        // 启用、禁用按纽
        canBan = true;
        this.money = MoneyUtils.formatMoney(ordersLotteryActivity.getMoney());


        if (ordersLotteryActivity.getId().intValue() == 9) {
            System.out.println();
        }

        if (now.after(startLotteryTime)) {
            activeStatus = "已结束";
        } else if (now.after(startTime) && startLotteryTime.after(now)) {
            activeStatus = "进行中";
        } else {
            activeStatus = "未开始";
        }

    }
}
