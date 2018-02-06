package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import com.h9.common.common.ConstantConfig;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static java.util.Arrays.stream;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/26
 * Time: 11:16
 */
//,uniqueConstraints = @UniqueConstraint(columnNames="userId")
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;
    
    @Column(name = "phone", columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;

    @Column(name = "open_id",  columnDefinition = "varchar(64) default null COMMENT '微信openId'")
    private String openId;

    private String unionId;

    @Column(name = "avatar", columnDefinition = "varchar(500) default '' COMMENT '用户头像'")
    private String avatar ;

    @Column(name = "nick_name", columnDefinition = "varchar(64) default '' COMMENT '昵称'")
    private String nickName;


    @Temporal(TIMESTAMP)
    @Column(name = "last_login_time", columnDefinition = "datetime COMMENT '最后登陆时间'")
    private Date lastLoginTime;

    @Column(name = "login_count",nullable = false,columnDefinition = "int default 0 COMMENT '登录次数'")
    private Integer loginCount;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 正常 2禁用 3失效'")
    private Integer status = 1;

    @Column(name = "type",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 正常用户'")
    private Integer type = 1;

    @Column(name = "is_admin",nullable = false,columnDefinition = "tinyint default 0 COMMENT '0:普通用户 1：管理员'")
    private Integer isAdmin = 0;

    @Column(name = "password", columnDefinition = "varchar(36) default '' COMMENT '加密后密码'")
    private String password;


    @Column(name = "uuid", columnDefinition = "varchar(64) default '' COMMENT 'uuid'")
    private String uuid;

    @Column(name = "h9_user_id", columnDefinition = "bigint(20) default null COMMENT '徽9原有用户id'")
    private Long h9UserId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_role",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<Role> roles ;

    @Column(name = "sign_count", nullable = false, columnDefinition = "tinyint default 0 COMMENT '总共签到天数'")
    private Integer signCount = 0;

    @Column(name = "sign_days", nullable = false, columnDefinition = "tinyint default 0 COMMENT '连续签到天数'")
    private Integer signDays = 0;

    @Column(name = "client",columnDefinition = "int default 0 COMMENT '客户端'")
    private Integer client;

    @Column(name = "city",columnDefinition = "varchar(200) comment '城市'")
    private String city;

    @Column(name = "longitude", columnDefinition = "double default 0 COMMENT '经度'")
    private double longitude;

    @Column(name = "latitude", columnDefinition = "double default 0 COMMENT '维度'")
    private double latitude;

    @Column(name="province",columnDefinition = "varchar(50) COMMENT '省'")
    private String province;


    @Column(name="street",columnDefinition = "varchar(50) COMMENT '街道'")
    private String street;

    @Column(name="street_number",columnDefinition = "varchar(50) COMMENT '街道号'")
    private String streetNumber;

    public User() {
    }


    public String getAvatar() {
        if(StringUtils.isNotEmpty(avatar)){
            return avatar;
        }
        return ConstantConfig.DEFAULT_HEAD;
    }



    public enum IsAdminEnum{
        NOTADMIN(0,"普通用户"),
        ADMIN(1,"管理员");

        IsAdminEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public static String getNameById(int id){
            IsAdminEnum isAdminEnum = stream(values()).filter(o -> o.getId()==id).limit(1).findAny().orElse(null);
            return isAdminEnum==null?null:isAdminEnum.getName();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum StatusEnum {
        ENABLED(1,"正常"),
        DISABLED(2,"禁用"),
        INVALID(3,"失效");

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

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
