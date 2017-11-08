package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

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

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '关联商品条码'")
    private String code;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '商品名称'")
    private String name;

    @Column(name = "supplier_name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '供应商名称（喷码）'")
    private String supplierName;

    @Column(name = "supplier_district", nullable = false, columnDefinition = "varchar(128) default '' COMMENT ''")
    private String supplierDistrict;

    @Column(name = "count",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '查询次数'")
    private BigDecimal count = new BigDecimal(0);

    @Temporal(TIMESTAMP)
    @Column(name = "fisrt_time", columnDefinition = "datetime COMMENT '首次扫描时间'")
    private Date fisrtTime;

    @Column(name = "address", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '上次查询位置'")
    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierDistrict() {
        return supplierDistrict;
    }

    public void setSupplierDistrict(String supplierDistrict) {
        this.supplierDistrict = supplierDistrict;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public Date getFisrtTime() {
        return fisrtTime;
    }

    public void setFisrtTime(Date fisrtTime) {
        this.fisrtTime = fisrtTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
