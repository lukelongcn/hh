package com.h9.api.model.vo;

import com.h9.common.db.entity.OrderItems;
import com.h9.common.db.entity.Orders;
import com.h9.common.utils.DateUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/11/1.
 */
public class OrderDetailVO {
    private String company = "";
    private String orderStatus = "";
    private Integer orderType;
    private String accepterName = "";
    private String tel= "";
    private String address= "";
    private String orderId= "";
    private String payMethod= "";
    private String payMoney= "";
    private String createOrderDate= "";
    private List<GoodsInfo> goodsInfoList;
    private String couponsNumber = "";


    public static OrderDetailVO convert(Orders order){
        OrderDetailVO vo = new OrderDetailVO();
        vo.setCompany(order.getSupplierName());
        vo.setOrderStatus("已完成");
        vo.setOrderType(order.getOrderType());
        if(order.getOrderType() == Orders.orderTypeEnum.OTHER.getCode()){
            vo.setAccepterName("");
            vo.setTel("");
            vo.setAddress("");
        }
        List<OrderItems> orderItems = order.getOrderItems();
        if (!CollectionUtils.isEmpty(orderItems)) {
            vo.setCouponsNumber(orderItems.get(0).getDidiCardNumber());
        }
        vo.setOrderId(order.getId() + "");
        vo.setPayMethod("余额支付");
        vo.setPayMoney(order.getPayMoney() + "");
        vo.setCreateOrderDate(DateUtil.formatDate(order.getCreateTime(), DateUtil.FormatType.GBK_MINUTE));

        List<OrderItems> itemList = order.getOrderItems();
        List<GoodsInfo> goodsInfos = itemList.stream().map(item -> {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsName(item.getName());
            goodsInfo.setImgUrl(item.getImage());
            return goodsInfo;
        }).collect(Collectors.toList());

        vo.setGoodsInfoList(goodsInfos);
        return vo;
    }


    public String getCouponsNumber() {
        return couponsNumber;
    }

    public void setCouponsNumber(String couponsNumber) {
        this.couponsNumber = couponsNumber;
    }

    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    private static class GoodsInfo{
        private String imgUrl;
        private String GoodsName;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String goodsName) {
            GoodsName = goodsName;
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }



    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getCreateOrderDate() {
        return createOrderDate;
    }

    public void setCreateOrderDate(String createOrderDate) {
        this.createOrderDate = createOrderDate;
    }
}
