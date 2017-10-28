package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 10:21
 */
@Entity
@Table(name = "reward")
public class Reward extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_batch_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '所属批次'")
    private RewardBatch rewardBatch;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "reward_type_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '奖励配置'")
    private RewardType rewardType;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励总金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "have_money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '已领取奖励总金额'")
    private BigDecimal haveMoney = new BigDecimal(0);

    @Column(name = "surplus_moeny",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '剩余金额'")
    private BigDecimal surplusMoeny = new BigDecimal(0);

    @Column(name = "reward_code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '奖励条码'")
    private String reward_code;

    @Column(name = "reward_url", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '奖励路径'")
    private String rewardUrl;

    @Column(name = "product_code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '关联商品条码'")
    private String productCode;

    @Column(name = "product_url", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '关联商品路径'")
    private String productUrl;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 未领取 2 部分领取 3 已领取完毕 4 已失效'")
    private Integer status = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RewardBatch getRewardBatch() {
        return rewardBatch;
    }

    public void setRewardBatch(RewardBatch rewardBatch) {
        this.rewardBatch = rewardBatch;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getHaveMoney() {
        return haveMoney;
    }

    public void setHaveMoney(BigDecimal haveMoney) {
        this.haveMoney = haveMoney;
    }

    public BigDecimal getSurplusMoeny() {
        return surplusMoeny;
    }

    public void setSurplusMoeny(BigDecimal surplusMoeny) {
        this.surplusMoeny = surplusMoeny;
    }

    public String getReward_code() {
        return reward_code;
    }

    public void setReward_code(String reward_code) {
        this.reward_code = reward_code;
    }

    public String getRewardUrl() {
        return rewardUrl;
    }

    public void setRewardUrl(String rewardUrl) {
        this.rewardUrl = rewardUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
