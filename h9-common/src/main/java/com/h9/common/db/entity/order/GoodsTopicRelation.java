package com.h9.common.db.entity.order;

import com.h9.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/8.
 * 商品专题-商品 关系表
 */
@Table(name = "goods_topic_relation")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTopicRelation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "goods_id", columnDefinition = "bigint(20) comment '商品Id'")
    private Long goodsId;

    @Column(name = "name", columnDefinition = "varchar(200) comment '商品名'")
    private String name;

    @Column(name = "goods_topic_module_id", columnDefinition = "bigint(20) comment '商品专题模块Id'")
    private Long goodsTopicModuleId;

    @Column(name = "sort", columnDefinition = "int comment '排序'")
    private Integer sort;

    @Column(name = "delFlag", columnDefinition = "int comment '状态'")
    private Integer del_flag;

    @Column(name = "goods_topic_type_id", columnDefinition = "bigint(20) comment '商品专题类型id'")
    private Long goodsTopicTypeId;
}
