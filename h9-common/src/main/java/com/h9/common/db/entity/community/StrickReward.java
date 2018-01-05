package com.h9.common.db.entity.community;

import com.h9.common.db.entity.user.User;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2018/1/5
 */
@Data
@Entity
@Table(name = "stick_reward")
public class StrickReward {
    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "stick_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '对应贴子'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Stick stick;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '打赏用户'")
    private User user;

    @Column(name = "reward",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '个人打赏金额'")
    private BigDecimal reward = new BigDecimal(0);

}
