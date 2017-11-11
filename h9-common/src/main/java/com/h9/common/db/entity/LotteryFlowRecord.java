package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;
import javax.validation.Constraint;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

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
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    private User user;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_flow_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint(20) default 0 COMMENT '活动参与记录id'")
    private LotteryFlow lotteryFlow;


    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '转账状态，1：成功，2：失败'")
    private Integer status = 1;

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
