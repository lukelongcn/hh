package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2017/12/29
 */
@Entity
@Table(name = "user_sign")
@Data
public class UserSign  extends BaseEntity {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "cash_back",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '签到奖励金额'")
    private BigDecimal cashBack = new BigDecimal(0);


}
