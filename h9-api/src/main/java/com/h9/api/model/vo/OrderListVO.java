package com.h9.api.model.vo;

import com.h9.common.db.entity.OrderItems;
import com.h9.common.db.entity.Orders;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/11/1.
 */
public class OrderListVO {

    private Long orderId;
    private String company;
    private String status;

    private List<GoodsInfo> goodsInfoList;

    public static OrderListVO convert(Orders orders){
        OrderListVO vo = new OrderListVO();
        List<OrderItems> itemList = orders.getOrderItems();
        List<GoodsInfo> goodsInfoList = itemList.stream().map(item -> {
            GoodsInfo info = new GoodsInfo();
            info.setImgUrl(item.getImage());
            info.setGoodsName(item.getName());
            return info;
        }).collect(Collectors.toList());

        vo.setCompany(orders.getSupplierName());
        vo.setStatus("已完成");
        vo.setGoodsInfoList(goodsInfoList);
        vo.setOrderId(orders.getId());
        return vo;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
