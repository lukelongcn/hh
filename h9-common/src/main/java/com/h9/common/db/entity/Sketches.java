package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:小品会商品信息表
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 18:04
 */
@Entity
@Table(name = "sketches")
public class Sketches extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "activity_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '小品会活动名称'")
    private String activityName;

    @Column(name = "product_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '商品名'")
    private String productName;
    
    
    @Column(name = "unit",  columnDefinition = "varchar(64) default '' COMMENT '规格'")
    private String unit;

    @Column(name = "code", columnDefinition = "varchar(64) default '' COMMENT '条码'")
    private String code;

    @Column(name = "md5_code", columnDefinition = "varchar(64) default '' COMMENT '条码md5值'")
    private String md5Code;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }
}
