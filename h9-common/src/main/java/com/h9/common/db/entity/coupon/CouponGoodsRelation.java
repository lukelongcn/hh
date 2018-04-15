package com.h9.common.db.entity.coupon;

import com.h9.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/9.
 */
@Table(name = "coupon_goods_relation")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponGoodsRelation extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "coupon_id",columnDefinition = "bigint(20) comment '劵id'")
    private Long couponId;

    @Column(name = "goods_id",columnDefinition = "bigint(20) comment '商品iD'")
    private Long goodsId;

    @Column(name = "del_flag",columnDefinition = "int comment '删除标记 1 删除 0 未删除'")
    private Integer delFlag = 0;


}
