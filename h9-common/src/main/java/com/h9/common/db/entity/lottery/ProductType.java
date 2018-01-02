package com.h9.common.db.entity.lottery;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/11
 * Time: 14:30
 */

@Entity
@Table(name = "product_type",uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class ProductType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '商品类型名称'")
    private String name;

    @Column(name = "unit", columnDefinition = "varchar(64) default '' COMMENT '规格'")
    private String unit;

    @Column(name = "degrees",  columnDefinition = "varchar(32) default '' COMMENT '度数'")
    private String degrees;

    @Column(name = "product_name", columnDefinition = "varchar(256) default '' COMMENT '商品名称'")
    private String productName;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
