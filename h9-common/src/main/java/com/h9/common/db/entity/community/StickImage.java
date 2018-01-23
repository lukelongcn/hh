package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.order.OrderItems;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2018/1/23
 */
@Data
@Entity
@Table(name = "stick_image")
public class StickImage  extends BaseEntity {
    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "image",columnDefinition = "varchar(255) default '' COMMENT '贴子内容图片'")
    private String image;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "stick_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '对应贴子'")
    private Stick stick;

   /* @OneToMany(mappedBy = "stick_image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("stickId desc")
    @Fetch(FetchMode.SUBSELECT)
    private List<StickImage> images = new ArrayList<>();*/
}
