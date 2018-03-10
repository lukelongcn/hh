package com.h9.admin.model.vo;

import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.GoodsType;
import com.h9.common.db.entity.order.OrderItems;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.utils.MoneyUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Gonyb on 2017/11/10.
 */
public class OrderItemVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String no;

    @ApiModelProperty(value ="收货人姓名")
    private String userName;

    @ApiModelProperty(value ="收货人号码")
    private String userPhone;

    @ApiModelProperty(value ="用户收货地址")
    private String userAddres;

    @ApiModelProperty(value ="订单状态")
    private Integer status;

    @ApiModelProperty(value ="订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value ="用户id")
    private Long userId;
    
    @ApiModelProperty(value ="商品")
    private String goods;
    

    @ApiModelProperty(value ="快递公司名")
    private String expressName;

    @ApiModelProperty(value = "商品数量")
    private Long count;

    @ApiModelProperty(value = "创建时间")
    private Date createTime ;

    @ApiModelProperty(value = "微信支付金额")
    private String payMoney4wx = "0.00";

    @ApiModelProperty(value = "酒元支付金额")
    private String payMoney4balance;

    @ApiModelProperty(value = "能否退款")
    private boolean canRefund = false;

    public static OrderItemVO toOrderItemVO(Orders orders){
        OrderItemVO orderItemVO = new OrderItemVO();
        BeanUtils.copyProperties(orders,orderItemVO);
        orderItemVO.setUserId(orders.getUser().getId());
        String collect = orders.getOrderItems().stream()
                .map(orderItem -> orderItem.getName() + " *" + orderItem.getCount())
                .collect(Collectors.joining(","));
        orderItemVO.setGoods(collect);
        /*String didi = orders.getOrderItems().stream()
                .map(OrderItems::getDidiCardNumber)
                .collect(Collectors.joining(","));
        Orders.PayMethodEnum byCode = Orders.PayMethodEnum.findByCode(orders.getPayMethond());*/
        //统计订单商品数量
        long sum = orders.getOrderItems().stream().parallel().mapToInt(OrderItems::getCount).summaryStatistics().getSum();
        orderItemVO.setCount(sum);
        Orders.statusEnum statusEnum = Orders.statusEnum.findByCode(orders.getStatus());
        orderItemVO.setStatusDesc(statusEnum==null?null:statusEnum.getDesc());
        BigDecimal payMoney = orders.getPayMoney();
        int payMethond = orders.getPayMethond();
        if(payMethond == Orders.PayMethodEnum.WX_PAY.getCode()){
            orderItemVO.setPayMoney4wx(MoneyUtils.formatMoney(payMoney));
        }else{
            orderItemVO.setPayMoney4balance(MoneyUtils.formatMoney(payMoney));
        }

        List<OrderItems> orderItems = orders.getOrderItems();
        boolean find = orderItems.stream().anyMatch(item -> {
            Goods goods = item.getGoods();
            GoodsType goodsType = goods.getGoodsType();
            if (goodsType.getCode().equals(GoodsType.GoodsTypeEnum.MOBILE_RECHARGE.getCode())) {
                return true;
            } else {
                return false;
            }
        });
        orderItemVO.setCanRefund(!find);
        return orderItemVO;
    }


    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPayMoney4wx() {
        return payMoney4wx;
    }

    public void setPayMoney4wx(String payMoney4wx) {
        this.payMoney4wx = payMoney4wx;
    }

    public String getPayMoney4balance() {
        return payMoney4balance;
    }

    public void setPayMoney4balance(String payMoney4balance) {
        this.payMoney4balance = payMoney4balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddres() {
        return userAddres;
    }

    public void setUserAddres(String userAddres) {
        this.userAddres = userAddres;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

}
