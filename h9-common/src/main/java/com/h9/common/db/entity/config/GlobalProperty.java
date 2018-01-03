package com.h9.common.db.entity.config;

import com.alibaba.fastjson.JSON;
import com.h9.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author: George
 * @date: 2017/10/30 13:41
 */
@Entity
@Table(name = "global_property",uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class GlobalProperty extends BaseEntity  {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '名称'")
    private String name;

    @Column(name = "type",nullable = false,columnDefinition = "tinyint default 0 COMMENT '参数类型 0：文本 1：对象 '")
    private Integer type;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '键'")
    private String code;

    @Column(name = "val", nullable = false, columnDefinition = "text COMMENT '值'")
    private String val;

    @Column(name = "description", nullable = false, columnDefinition = "varchar(512) default '' COMMENT '说明'")
    private String description;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
       this.val = val;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
