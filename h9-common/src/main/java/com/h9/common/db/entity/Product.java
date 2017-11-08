package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 17:43
 */

@Entity
@Table(name = "product")
public class Product extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '产品名'")
    private String name;

    @Column(name = "product_code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '关联商品条码'")
    private String productCode;



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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
