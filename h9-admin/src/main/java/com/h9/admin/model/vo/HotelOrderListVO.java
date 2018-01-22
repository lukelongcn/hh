package com.h9.admin.model.vo;

import com.h9.common.db.entity.hotel.HotelOrder;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HotelOrderListVO")
public class HotelOrderListVO {

    @ApiModelProperty("订单Id")
    private Long id;
    @ApiModelProperty("酒店名")
    private String hotelName;
    @ApiModelProperty("酒店房间名")
    private String roomTypeName;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("入住人")
    private String roomer;
    @ApiModelProperty("总金额")
    private String totalMoney;
    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("入住时间")
    private String comeRoomTime;
    @ApiModelProperty("离开时间")
    private String outRoomTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("能否确认")
    private Boolean canAffirm = false;
    @ApiModelProperty("能否退款")
    private Boolean canRefund = false;

    public HotelOrderListVO(){}

    public HotelOrderListVO(HotelOrder hotelOrder){
        BeanUtils.copyProperties(hotelOrder, this);
        this.setCreateTime(DateUtil.formatDate(hotelOrder.getCreateTime(), DateUtil.FormatType.MINUTE))
                .setComeRoomTime(DateUtil.formatDate(hotelOrder.getComeRoomTime(), DateUtil.FormatType.MINUTE))
                .setOutRoomTime(DateUtil.formatDate(hotelOrder.getOutRoomTime(), DateUtil.FormatType.MINUTE))
                .setTotalMoney(MoneyUtils.formatMoney(hotelOrder.getTotalMoney()));

        Integer orderStatus = hotelOrder.getOrderStatus();
        if(orderStatus != null){
            this.setStatus(orderStatus+"");
            if(orderStatus == HotelOrder.OrderStatusEnum.WAIT_ENSURE.getCode()){
                this.setCanAffirm(true);
                this.setCanRefund(true);
            }
            if(orderStatus == HotelOrder.OrderStatusEnum.SUCCESS.getCode()){
                this.setCanRefund(true);
            }

        }
    }

}
