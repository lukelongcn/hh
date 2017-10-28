package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 14:52
 */

@Entity
@Table(name = "rechargeRecord")
public class RechargeRecord extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "surplus_balance",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现后剩余余额'")
    private BigDecimal surplusBalance = new BigDecimal(0);

    @Column(name = "order_id", columnDefinition = "bigint(20) default null COMMENT '相关第三方订单id'")
    private Long orderId;

    @Column(name = "order_no", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '第三方相关订单编号'")
    private String orderNo;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '提现状态 ： 1充值中 2充值完成  3充值异常'")
    private Integer status = 1;

    @Column(name = "remarks", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '备注'")
    private String remarks;

    @Temporal(TIMESTAMP)
    @Column(name = "finish_time", columnDefinition = "datetime COMMENT '转账成功时间'")
    private Date finishTime;

    @Column(name = "longitude", columnDefinition = "double default null COMMENT ''")
    private double longitude;

    @Column(name = "latitude", columnDefinition = "double default null COMMENT ''")
    private double latitude;

    @Column(name = "address", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '地址'")
    private String address;

    @Column(name = "refer", nullable = false, columnDefinition = "varchar(500) default '' COMMENT '前置页面'")
    private String refer;

    @Column(name = "user_agent", nullable = false, columnDefinition = "varchar(500) default '' COMMENT '用户代理'")
    private String userAgent;

    @Column(name = "ip", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'ip地址'")
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getSurplusBalance() {
        return surplusBalance;
    }

    public void setSurplusBalance(BigDecimal surplusBalance) {
        this.surplusBalance = surplusBalance;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
