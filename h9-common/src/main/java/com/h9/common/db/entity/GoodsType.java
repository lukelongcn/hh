package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description: 商品类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 11:35
 */

@Entity
@Table(name = "goodsType")
public class GoodsType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '商品分类名称'")
    private String name;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 启用  2 禁用'")
    private Integer status = 1;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    private GoodsType parent;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public GoodsType getParent() {
        return parent;
    }

    public void setParent(GoodsType parent) {
        this.parent = parent;
    }
}
