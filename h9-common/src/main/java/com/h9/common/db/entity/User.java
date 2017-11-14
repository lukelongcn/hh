package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.dialect.unique.DB2UniqueDelegate;

import javax.naming.Name;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/26
 * Time: 11:16
 */

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = AUTO, generator = "h9-apiSeq")
    private Long id;
    
    @Column(name = "phone", nullable = false, columnDefinition = "varchar(11) default '' COMMENT '手机号'")
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

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 启用 2禁用 3失效'")
    private Integer status = 1;

    //TODO 待定
    @Column(name = "type",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 正常用户'")
    private Integer type = 1;

    @Column(name = "is_admin",nullable = false,columnDefinition = "tinyint default 0 COMMENT '0:非后台用户 1：后台用户'")
    private Integer isAdmin = 0;

    @Column(name = "password", columnDefinition = "varchar(36) default '' COMMENT '加密后密码'")
    private String password;

    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public enum IsAdminEnum{
        NOTADMIN(0,"非后台用户"),
        ADMIN(1,"后台用户");

        IsAdminEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

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
        ENABLED(1,"启用"),
        DISABLED(2,"禁用"),
        INVALID(3,"失效");

        StatusEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

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
