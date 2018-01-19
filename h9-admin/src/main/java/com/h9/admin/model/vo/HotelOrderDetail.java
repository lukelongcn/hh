package com.h9.admin.model.vo;

import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2018/1/18.
 */
@Data
@Accessors(chain = true)
@ApiModel("酒店订单详情")
public class HotelOrderDetail {
    private Long id;

    @ApiModelProperty("房间类型名")
    private String roomTypeName;


    @ApiModelProperty("入住、离店时间")
    private String comeAndOutDate;

    @ApiModelProperty("入住人")
    private String roomer;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("房间价格")
    private String roomPrice;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("酒店名")
    private String hotelName;

    @ApiModelProperty("酒店地址")
    private String hotelAddress;

    @ApiModelProperty("酒店电话")
    private String hotelPhone;

    @ApiModelProperty("含早")
    private String include;

    @ApiModelProperty("保留时间")
    private String keepTime;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("酒元支付金额")
    private String PayMoney4JiuYuan = "0";

    @ApiModelProperty("微信支付金额")
    private String PayMoney4Wechat = "0";

    @ApiModelProperty("房间数")
    private Integer roomCount;



    @ApiModelProperty("总金额")
    private String totalMoney;

    @ApiModelProperty("用户id")
    private Long userId;
    //待支付，【待确认，预订成功】，已退款，已取消

    @ApiModelProperty("eg：‘无烟房’")
    private String roomStyle;

    @ApiModelProperty("支付流水")
    private List<PayInfo> payInfoList = new ArrayList<>();

    public HotelOrderDetail(HotelOrder hotelOrder,List<PayInfo> payInfoList) {
        BeanUtils.copyProperties(hotelOrder, this);
        String comeRoomTime = DateUtil.formatDate(hotelOrder.getComeRoomTime(), DateUtil.FormatType.DAY);

        String outRoomTime = DateUtil.formatDate(hotelOrder.getOutRoomTime(), DateUtil.FormatType.DAY);

        String days = DateUtil.getEndDate(hotelOrder.getComeRoomTime(), hotelOrder.getOutRoomTime());

        String comeAndOutDate = comeRoomTime + "至" + outRoomTime + " 共(" +days+ "晚)";
        Integer orderStatus = hotelOrder.getOrderStatus();
        HotelOrder.OrderStatusEnum findStatusEnum = HotelOrder.OrderStatusEnum.findByCode(orderStatus);

        this
                .setComeAndOutDate(days)
                .setOrderStatus(findStatusEnum == null ? "" : findStatusEnum.getDesc())
                .setHotelPhone(hotelOrder.getHotel().getHotelPhone())
                .setKeepTime(getKeepTime())
                .setRoomStyle(hotelOrder.getRoomStyle())
                .setPayInfoList(payInfoList)
                .setComeAndOutDate(comeAndOutDate)
                .setRoomPrice(MoneyUtils.formatMoney(hotelOrder.getHotelRoomType().getRealPrice()))
                .setTotalMoney(MoneyUtils.formatMoney(hotelOrder.getTotalMoney()))
                .setRemarks(hotelOrder.getRemarks());

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel
    public static class PayInfo {
        @ApiModelProperty("流水id")
        private Long id;
        @ApiModelProperty("支付方式 ")
        private String payMethod;
        @ApiModelProperty("金额")
        private String money;
        @ApiModelProperty("时间")
        private String createTime;
        @ApiModelProperty("状态")
        private String status;

        public PayInfo(Long id, String payMethod, String money, String createTime, String status) {
            this.id = id;
            this.payMethod = payMethod;
            this.money = money;
            this.createTime = createTime;
            this.status = status;
        }

        public PayInfo( ) {
        }

        public PayInfo(BalanceFlow balanceFlow) {
            this.id = balanceFlow.getId();
            Long flowType = balanceFlow.getFlowType();
            BalanceFlow.BalanceFlowTypeEnum findEnum = BalanceFlow.BalanceFlowTypeEnum.findByCode(flowType);
            this.payMethod = findEnum.getName();
            this.money = MoneyUtils.formatMoney(balanceFlow.getMoney());
            this.createTime = DateUtil.formatDate(balanceFlow.getCreateTime(), DateUtil.FormatType.GBK_MINUTE);
            this.setStatus("正常");
        }
    }
}
