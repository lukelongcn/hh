package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

/**
 * Created with IntelliJ IDEA.
 * RechargeOrder:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 17:45
 */
@Data
@Entity
@Table(name = "recharge_order")
public class RechargeOrder extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;
    
    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT ''")
    private Long user_id;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '充值金额'")
    private BigDecimal money = new BigDecimal(0);
    
    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '订单状态 0 待充值 1 充值失败， 2 充值成功'")
    private Integer status = 1;

    @Temporal(DATE)
    @Column(name = "call_back_time", columnDefinition = "DATE COMMENT '充值回调时间'")
    private Date callBackTime;




}
