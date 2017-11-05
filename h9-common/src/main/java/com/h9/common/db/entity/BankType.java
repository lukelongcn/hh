package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.Table;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:bank类型
 * Created by 李圆
 * on 2017/11/2
 */
@Entity
@Table(name = "bank_type")
public class BankType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "bank_name", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '银行名称'")
    private String bankName;

    @Column(name = "color",nullable = false,columnDefinition = "varchar(32) default '' COMMENT '颜色'")
    private String color;


    @Column(name = "back_img",columnDefinition = "varchar(200) default '' COMMENT '银行图标'")
    private String bankImg;

    public String getBankImg() {
        return bankImg;
    }

    public void setBankImg(String bankImg) {
        this.bankImg = bankImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
