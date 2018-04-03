package com.h9.common.db.entity.order;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:专题表</p>
 *
 * @author LiYuan
 * @Date 2018/4/3
 */
@Data
@Entity
@Table(name = "special_topic")
public class SpecialTopic extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "sort", columnDefinition = "int default 0 COMMENT '排序'")
    private int sort;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '专题图片'")
    private String image;

    @Column(name = "good_id",columnDefinition = "bigint COMMENT '商品Id'")
    private Long goodId;

    @Column(name = "good_sort", columnDefinition = "int default 0 COMMENT '商品排序'")
    private int goodSort;
}
