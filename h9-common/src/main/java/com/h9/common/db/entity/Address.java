package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/20
 * Time: 17:27
 */

@Entity
@Table(name = "address")
public class Address extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户id'")
    private Long userId;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(20) default '' COMMENT '用户名'")
    private String name;

    @Column(name = "phone", nullable = false, columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;
    
    @Column(name = "address", nullable = false, columnDefinition = "varchar(200) default '' COMMENT '地址'")
    private String address;
    
    @Column(name = "default_addresss",nullable = false,columnDefinition = "tinyint default 1 COMMENT '默认地址'")
    private Integer defaultAddresss = 1;

    @Column(name = "province", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '省'")
    private String province;

    @Column(name = "city", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '城市'")
    private String city;

    @Column(name = "distict", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '区'")
    private String distict;

    @Column(name = "provincial_cyty", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '城市编号'")
    private String provincialCyty;


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDefaultAddresss() {
        return defaultAddresss;
    }

    public void setDefaultAddresss(Integer defaultAddresss) {
        this.defaultAddresss = defaultAddresss;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistict() {
        return distict;
    }

    public void setDistict(String distict) {
        this.distict = distict;
    }

    public String getProvincialCyty() {
        return provincialCyty;
    }

    public void setProvincialCyty(String provincialCyty) {
        this.provincialCyty = provincialCyty;
    }
}
