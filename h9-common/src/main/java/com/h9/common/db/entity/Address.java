package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static java.util.Arrays.stream;
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

    @Column(name = "default_address",nullable = false,columnDefinition = "tinyint default 1 COMMENT '默认地址 1默认 0不默认'")
    private Integer defaultAddress = 1;

    @Column(name = "province", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '省'")
    private String province;

    @Column(name = "city", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '城市'")
    private String city;

    @Column(name = "distict", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '区'")
    private String distict;

    @Column(name = "provincial_city", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '城市编号'")
    private String provincialCity;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '状态， 1：启用，0：禁用'")
    private Integer status;

    @Column(name = "pid", columnDefinition = "bigint default 1 COMMENT '省id'")
    private Long pid;

    @Column(name = "cid", columnDefinition = "bigint default 1 COMMENT '市id'")
    private Long cid;

    @Column(name = "aid", columnDefinition = "bigint default 1 COMMENT '区id'")
    private Long aid;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getProvincialCity() {
        return provincialCity;
    }

    public void setProvincialCity(String provincialCity) {
        this.provincialCity = provincialCity;
    }

    public enum StatusEnum {
        DISABLED(0,"禁用"),
        ENABLED(1,"启用");

        StatusEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public static String getNameById(int id){
           StatusEnum statusEnum = stream(values()).filter(o -> o.getId()==id).limit(1).findAny().orElse(null);
            return statusEnum==null?null:statusEnum.getName();
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }

    public enum DefaultAddressEnum {
        NOT_DEFAULT(0,"否"),
        DEFAULT(1,"是");

        DefaultAddressEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public static String getNameById(int id){
            DefaultAddressEnum defaultAddressEnum = stream(values()).filter(o -> o.getId()==id).limit(1).findAny().orElse(null);
            return defaultAddressEnum==null?null:defaultAddressEnum.getName();
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }
}
