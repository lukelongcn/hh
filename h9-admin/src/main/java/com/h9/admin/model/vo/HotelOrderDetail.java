package com.h9.admin.model.vo;

import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2018/1/18.
 */
@Data
@Accessors(chain = true)
public class HotelOrderDetail {
    private Long id;

    private String roomTypeName;

    private String comeAndOutDate;

    private String roomer;

    private String phone;

    private String roomPrice;

    private String orderStatus;

    private String hotelName;

    private String hotelAddress;

    private String hotelPhone;

    private String include;

    private String keepTime;

    private String remarks;

    private String PayMoney4JiuYuan = "0";

    private String PayMoney4Wechat = "0";

    private Integer roomCount;

    private Integer payMethod;

    private String totalMoney;

    private Long user_id;
    //待支付，【待确认，预订成功】，已退款，已取消

    private String roomStyle;

    private List<PayInfo> payInfoList = new ArrayList<>();

    public HotelOrderDetail(HotelOrder hotelOrder,List<PayInfo> payInfoList) {
        BeanUtils.copyProperties(hotelOrder, this);
        Date comeRoomTime = hotelOrder.getComeRoomTime();
        Date outRoomTime = hotelOrder.getOutRoomTime();
        String days = DateUtil.getEndDate(comeRoomTime, outRoomTime);
        Integer orderStatus = hotelOrder.getOrderStatus();
        HotelOrder.OrderStatusEnum findStatusEnum = HotelOrder.OrderStatusEnum.findByCode(orderStatus);

        this
                .setComeAndOutDate(days)
                .setOrderStatus(findStatusEnum == null ? "" : findStatusEnum.getDesc())
                .setHotelPhone(hotelOrder.getHotel().getHotelPhone())
                .setKeepTime(getKeepTime())
                .setRoomStyle(hotelOrder.getRoomStyle())
                .setPayInfoList(payInfoList)
                .setRemarks(hotelOrder.getRemarks());

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class PayInfo {
        private Long id;
        private String payMethod;
        private String money;
        private String createTime;
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
        }
    }
}
