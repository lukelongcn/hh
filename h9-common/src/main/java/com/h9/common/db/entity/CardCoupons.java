package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by itservice on 2017/11/6.
 */
@Entity
@Table(name = "card_coupons")
public class CardCoupons extends BaseEntity{

    @Id
    @SequenceGenerator(name = "h9-apiSeq")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "no", columnDefinition = "varchar(16) default '' COMMENT '卡券号'")
    private String no;
    @Column(name="goods_id")
    private Long goodsId;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "status",columnDefinition = "int COMMENT '1为正常，2为已兑换,3 禁用'")
    private Integer status;

    @Column(name = "old_id", columnDefinition = "bigint(20) default null COMMENT '迁移数据id'")
    private Long oldId;

    @Column(name = "batch_no", columnDefinition = "varchar(16) default '' COMMENT '批次'")
    private String batchNo;

    @Column(name = "user_id", columnDefinition = "bigint(20) default 0 COMMENT '导入卡券用户的id'")
    private Long userId;

    @Column(name = "grant_time",columnDefinition = "datetime COMMENT '发放卡券时间'")
    @Temporal(TIMESTAMP)
    protected Date grantTime ;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }
}
