package com.h9.common.db.entity;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2017/11/30
 */
@Table(name = "china")
@Entity
public class China {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '编号'")
    private String code;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '名称'")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_code",referencedColumnName="code",columnDefinition = "varchar(20) default 0 COMMENT '上级编号'")
    private China parentCode;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '状态， 1：启用，0：禁用'")
    private Integer status;

    @Column(name = "level",nullable = false,columnDefinition = "tinyint  COMMENT '级别， 1：省，2：市  3：县/区'")
    private Integer level;

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

    public China getParentCode() {
        return parentCode;
    }

    public void setParentCode(China parentCode) {
        this.parentCode = parentCode;
    }

    /*public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }*/

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
