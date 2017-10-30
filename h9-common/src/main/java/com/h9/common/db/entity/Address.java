package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description: 收货地址
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 15:04
 */

@Entity
@Table(name = "address")
public class Address extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(36) default '' COMMENT '用户名'")
    private String name;

    @Column(name = "sex",nullable = false,columnDefinition = "int default 0 COMMENT '性别'")
    private Integer sex;

    @Column(name = "phone", nullable = false, columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;
    
    @Column(name = "province", nullable = false, columnDefinition = "varchar(36) default '' COMMENT '省'")
    private String province;
    
    @Column(name = "city", nullable = false, columnDefinition = "varchar(36) default '' COMMENT '城市'")
    private String city;
    
    @Column(name = "district", nullable = false, columnDefinition = "varchar(36) default '' COMMENT '区'")
    private String district;

    @Column(name = "address", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '收货地址'")
    private String address;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 整常，2已删除'")
    private Integer status = 1;

    @Column(name = "city_path", nullable = false, columnDefinition = "varchar(25) default '' COMMENT '城市路径'")
    private String cityPath;

    @Column(name = "an_int",nullable = false,columnDefinition = "tinyint default 1 COMMENT '默认地址'")
    private Integer defaultAddress = 1;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户id'")
    private Long userId;


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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddressDetail() {
        return province+city+district+address;
    }

    public String getCityPath() {
        return cityPath;
    }

    public void setCityPath(String cityPath) {
        this.cityPath = cityPath;
    }

    public Integer getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
