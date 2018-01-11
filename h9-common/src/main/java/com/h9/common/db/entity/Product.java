package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;

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

@Data
@Entity
@Table(name = "product",uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Product extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '关联商品条码'")
    private String code;

    @Column(name = "supplier_name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '供应商名称（喷码）'")
    private String supplierName;
    
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_type_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '商品类别'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductType productType;

    @Column(name = "supplier_district", nullable = false, columnDefinition = "varchar(128) default '' COMMENT ''")
    private String supplierDistrict;

    @Column(name = "count",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '查询次数'")
    private BigDecimal count = new BigDecimal(0);

    @Temporal(TIMESTAMP)
    @Column(name = "fisrt_time", columnDefinition = "datetime COMMENT '首次扫描时间'")
    private Date fisrtTime;

    @Column(name = "address",  columnDefinition = "varchar(256) default '' COMMENT '上次查询位置'")
    private String address;

    @Temporal(TIMESTAMP)
    @Column(name = "last_time", columnDefinition = "datetime COMMENT '上次扫描时间'")
    private Date lastTime;

    @Column(name = "fisrt_address",  columnDefinition = "varchar(256) default '' COMMENT '第一次查询位置'")
    private String fisrtAddress;

    @Column(name = "plan_id", columnDefinition = "varchar(84) default '' COMMENT '生产计划id'")
    private String planId;


}
