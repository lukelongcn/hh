package com.h9.common.db.entity.bigrich;

import com.h9.common.base.BaseEntity;
import com.h9.common.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/15.
 */
@Table(name = "orders_lottery_relation")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersLotteryRelation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint COMMENT 'uid'")
    private Long userId;

    @Column(name = "order_id", columnDefinition = "bigint COMMENT 'order id '")
    private Long orderId;

    @Column(name = "orders_lottery_activity_id", columnDefinition = "bigint COMMENT ''")
    private Long ordersLotteryActivityId;

    @Column(name = "del_flag", columnDefinition = "int COMMENT '删除标记 1 删除 0 未删除'")
    private Integer delFlag=0;

    @Column(name = "money")
    private BigDecimal money;


}
