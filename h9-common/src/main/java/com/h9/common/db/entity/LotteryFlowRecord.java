package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;
import javax.validation.Constraint;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author: George
 * @date: 2017/11/10 14:41
 * name="活动参与记录的转账记录"
 */
@Entity()
@Table(name = "lottery_flow_record",uniqueConstraints = {@UniqueConstraint(columnNames={"lottery_flow_id"})})
public class LotteryFlowRecord  extends BaseEntity {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '操作人'")
    private User user;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_flow_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint(20) default 0 COMMENT '活动参与记录id'")
    private LotteryFlow lotteryFlow;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '转账状态，1：成功，2：失败'")
    private Integer status = 1;

    @Column(name = "outer_id", columnDefinition = "bigint(20) default null COMMENT '转出方id'")
    private Long outerId;

    @Column(name = "phone", columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;

    @Column(name = "nick_name", columnDefinition = "varchar(64) default '' COMMENT '昵称'")
    private String nickName;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '奖励条码'")
    private String code;

    @Column(name = "money", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励领取金额'")
    private BigDecimal money;

    @Column(name = "transfer_time",columnDefinition = "datetime COMMENT '转账时间'")
    @Temporal(TIMESTAMP)
    private Date transferTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LotteryFlow getLotteryFlow() {
        return lotteryFlow;
    }

    public void setLotteryFlow(LotteryFlow lotteryFlow) {
        this.lotteryFlow = lotteryFlow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Long getOuterId() {
        return outerId;
    }

    public void setOuterId(Long outerId) {
        this.outerId = outerId;
    }

    public enum LotteryFlowRecordStatusEnum {
        SUCCESS(1,"成功"),
        FAIL(2,"失败");

        LotteryFlowRecordStatusEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
