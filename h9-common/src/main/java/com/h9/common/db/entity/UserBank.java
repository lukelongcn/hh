package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 9:54
 */

@Entity
@Table(name = "user_bank")
public class UserBank extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id",nullable = false,columnDefinition = "bigint(20) default 0 COMMENT '用户id'")
    private Long userId;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '持卡人名'")
    private String name;

    @Column(name = "no", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '银行卡号'")
    private String no;

    @Column(name = "provice", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '开户省'")
    private String provice;

    @Column(name = "city", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '开户城市'")
    private String city;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1:正常 2禁用 3已删除'")
    private Integer status = 1;

    @Column(name = "back_img",nullable = false,columnDefinition = "varchar(200) default '' COMMENT '银行图标'")
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
