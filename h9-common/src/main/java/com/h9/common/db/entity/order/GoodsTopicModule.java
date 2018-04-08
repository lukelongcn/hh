package com.h9.common.db.entity.order;

import com.h9.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/8.
 * 商品专题表
 */
@Table(name = "goods_topic_model")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTopicModule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "sort", columnDefinition = "int comment '排序'")
    private Integer sort;

    @Column(name = "img", columnDefinition = "varchar(300) comment '图片'")
    private String img;

    @Column(name = "del_flag", columnDefinition = "int comment '1为删除 0为未删除'")
    private Integer delFlag;

    @Column(name = "goods_topic_type_id", columnDefinition = "bigint(20) comment '类型Id'")
    private Long GoodsTopicTypeId;
}
