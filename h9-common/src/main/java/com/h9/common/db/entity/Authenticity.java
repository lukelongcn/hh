package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 19:41
 */

@Entity
@Table(name = "authenticity")
public class Authenticity extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;


    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '条码'")
    private String code;

    @Temporal(TIMESTAMP)
    @Column(name = "first_date", columnDefinition = "datetime COMMENT '第一次扫描时间'")
    private Date firstDate;

    @Column(name = "product_name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '产品名称'")
    private String productName;


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

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
