package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:用户行为表
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 18:01
 */

@Entity
@Table(name = "user_record")
public class UserRecord extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户'")
    private Long userId;

    @Column(name = "type",nullable = false,columnDefinition = "tinyint default 1 COMMENT '类型 ：1.红包 ：2.防伪 3.登录 4 充值，6提现'")
    private Integer type = 1;
    
    
    @Column(name = "longitude", columnDefinition = "double default 0 COMMENT '经度'")
    private double longitude;

    @Column(name = "latitude", columnDefinition = "double default 0 COMMENT '维度'")
    private double latitude;

    @Column(name = "address", columnDefinition = "varchar(64) default '' COMMENT '地址'")
    private String address;

    @Column(name = "refer", columnDefinition = "varchar(500) default '' COMMENT '前置页面'")
    private String refer;

    @Column(name = "user_agent",  columnDefinition = "varchar(500) default '' COMMENT '用户代理'")
    private String userAgent;

    @Column(name = "ip", columnDefinition = "varchar(64) default '' COMMENT 'ip地址'")
    private String ip;
    
    @Column(name = "os",columnDefinition = "varchar(32) default '' COMMENT '系统'")
    private String os;
    
    @Column(name = "version",  columnDefinition = "varchar(32) default '' COMMENT '客户端版本'")
    private String version;
    
    @Column(name = "client",columnDefinition = "int default 0 COMMENT '客户端'")
    private Integer client;

    @Column(name = "imei",  columnDefinition = "varchar(64) default '' COMMENT '机器唯一码'")
    private String imei;

    @Column(name = "phone_type",  columnDefinition = "varchar(32) default '' COMMENT '手机品牌'")
    private String phoneType;

    @Column(name="province",columnDefinition = "varchar(50) COMMENT '省'")
    private String province;

    @Column(name="city",columnDefinition = "varchar(50) COMMENT '市'")
    private String city;

    @Column(name="district",columnDefinition = "varchar(50) COMMENT '区'")
    private String district;

    @Column(name="street",columnDefinition = "varchar(50) COMMENT '街道'")
    private String street;

    @Column(name="street_number",columnDefinition = "varchar(50) COMMENT '街道号'")
    private String streetNumber;


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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        if(StringUtils.isNotEmpty(refer)){
            if(refer.length()>499){
                refer = refer.substring(0, 499);
            }
            this.refer = refer;
        }

    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        if(StringUtils.isNotEmpty(userAgent)){
            if(userAgent.length()>499){
                userAgent = userAgent.substring(0, 499);
            }
            this.userAgent = userAgent;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public enum ClientEnum {
        ANDROID(1,"android"),
        IOS(2,"ios"),
        WEIXIN(3,"微信");

        ClientEnum(long id,String name){
            this.id = id;
            this.name = name;
        }

        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getValue(Integer id){
            if (id == null) {
                return null;
            }
            ClientEnum[] values = values();
            for(ClientEnum clientEnum: values){
                if(id == clientEnum.getId()){
                    return clientEnum.getName();
                }
            }
            return null;
        }
    }
}
