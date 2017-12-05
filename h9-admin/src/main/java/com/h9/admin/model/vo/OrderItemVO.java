package com.h9.admin.model.vo;

import com.h9.common.db.entity.OrderItems;
import com.h9.common.db.entity.Orders;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
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

    @ApiModelProperty(value ="支付方式描述")
    private Integer payMethond;

    @ApiModelProperty(value ="支付方式描述")
    private String payMethodDesc;

    @ApiModelProperty(value ="订单金额")
    private BigDecimal money = new BigDecimal(0);

    @ApiModelProperty(value ="需要支付的金额")
    private BigDecimal payMoney = new BigDecimal(0);

    @ApiModelProperty(value ="支付状态描述")
    private String payStatusDesc ;

    @ApiModelProperty(value ="支付状态 1待支付 2 已支付")
    private Integer payStatus = 1;

    @ApiModelProperty(value ="订单状态")
    private Integer status = 1;

    @ApiModelProperty(value ="订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value ="用户id")
    private Long userId;
    
    @ApiModelProperty(value ="商品")
    private String goods;
    
    @ApiModelProperty(value ="订单类别 1手机卡 2滴滴卡 3实物")
    private Integer orderType ;
    
    @ApiModelProperty(value ="物流单号")
    private String logisticsNumber;

    @ApiModelProperty(value ="滴滴券号")
    private String didiCardNumber;

    @ApiModelProperty(value ="快递公司名")
    private String expressName;
    @ApiModelProperty(value = "商品数量")
    private long count;
    @ApiModelProperty(value = "创建时间")
    private Date createTime ;//创建时间
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;//更新时间

    public static OrderItemVO toOrderItemVO(Orders orders){
        OrderItemVO orderItemVO = new OrderItemVO();
        BeanUtils.copyProperties(orders,orderItemVO);
        orderItemVO.setPayStatusDesc(orders.getPayStatus());
        orderItemVO.setUserId(orders.getUser().getId());
        String collect = orders.getOrderItems().stream()
                .map(orderItem -> orderItem.getName() + " *" + orderItem.getCount())
                .collect(Collectors.joining(","));
        orderItemVO.setGoods(collect);
        String didi = orders.getOrderItems().stream()
                .map(OrderItems::getDidiCardNumber)
                .collect(Collectors.joining(","));
        orderItemVO.setDidiCardNumber(didi);
        Orders.PayMethodEnum byCode = Orders.PayMethodEnum.findByCode(orders.getPayMethond());
        orderItemVO.setPayMethodDesc(byCode==null?"未知的支付方式":byCode.getDesc());
        //统计订单商品数量
        long sum = orders.getOrderItems().stream().parallel().mapToInt(OrderItems::getCount).summaryStatistics().getSum();
        orderItemVO.setCount(sum);
        Orders.statusEnum statusEnum = Orders.statusEnum.findByCode(orders.getStatus());
        orderItemVO.setStatusDesc(statusEnum==null?null:statusEnum.getDesc());
        return orderItemVO;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getCount() {
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

    public String getPayMethodDesc() {
        return payMethodDesc;
    }

    public void setPayMethodDesc(String payMethodDesc) {
        this.payMethodDesc = payMethodDesc;
    }

    public String getDidiCardNumber() {
        return didiCardNumber;
    }

    public void setDidiCardNumber(String didiCardNumber) {
        this.didiCardNumber = didiCardNumber;
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

    public Integer getPayMethond() {
        return payMethond;
    }

    public void setPayMethond(Integer payMethond) {
        this.payMethond = payMethond;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getPayStatusDesc() {
        return payStatusDesc;
    }

    public void setPayStatusDesc(int payStatus) {
        this.payStatusDesc = Orders.PayStatusEnum.findByCode(payStatus).getDesc();
    }
}
