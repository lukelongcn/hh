package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.dialect.unique.DB2UniqueDelegate;

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
 * Time: 14:32
 */

@Entity
@Table(name = "withdrawals_record")
public class WithdrawalsRecord extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;
    
    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户id'")
    private Long userId;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现金额'")
    private BigDecimal money = new BigDecimal(0);
    
    @Column(name = "surplus_balance",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现后剩余余额'")
    private BigDecimal surplusBalance = new BigDecimal(0);

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_bank_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '用户银行卡'")
    private UserBank userBank;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '提现状态 ： 1提现中  2银行转账中 3银行转账完成 ，4 提现异常'")
    private Integer status = 1;

    @Column(name = "remarks", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '备注'")
    private String remarks;

    @Column(name = "order_id", columnDefinition = "bigint(20) default null COMMENT '相关第三方订单id'")
    private Long orderId;

    @Column(name = "order_no", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '第三方相关订单编号'")
    private String orderNo;


    @Temporal(TIMESTAMP)
    @Column(name = "finish_time", columnDefinition = "datetime COMMENT '转账成功时间'")
    private Date finishTime;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_record_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    private UserRecord userRecord;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public UserBank getUserBank() {
        return userBank;
    }

    public void setUserBank(UserBank userBank) {
        this.userBank = userBank;
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

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }
}
