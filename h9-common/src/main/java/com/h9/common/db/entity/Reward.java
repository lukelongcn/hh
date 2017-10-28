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
 * Time: 10:21
 */
@Entity
@Table(name = "reward")
public class Reward extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '活动id'")
    private Activity activity;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '奖励条码'")
    private String code;

    @Column(name = "md5_code", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '条码加密'")
    private String md5Code;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励总金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "have_money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '已领取奖励总金额'")
    private BigDecimal haveMoney = new BigDecimal(0);

    @Column(name = "surplus_moeny",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '剩余金额'")
    private BigDecimal surplusMoeny = new BigDecimal(0);

    @Column(name = "reward_url", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '奖励路径'")
    private String rewardUrl;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '管连商品信息'")
    private Product product;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 未领取 2 部分领取 3 已领取完毕 4 已失效'")
    private Integer status = 1;

    @Column(name = "user_id",nullable = false, columnDefinition = "bigint(20) default 0 COMMENT '奖励所属人'")
    private Long userId;

    @Temporal(TIMESTAMP)
    @Column(name = "finish_time", columnDefinition = "datetime COMMENT '结束时间'")
    private Date finishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getRewardUrl() {
        return rewardUrl;
    }

    public void setRewardUrl(String rewardUrl) {
        this.rewardUrl = rewardUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }
}
