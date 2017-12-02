package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static java.util.Arrays.stream;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by itservice on 2017/11/29.
 */
@Entity
@Table(name = "white_user_list")
public class WhiteUserList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '白明单用户'")
    private Long userId;

    @Column(name = "phone", columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;

    @Column(name = "status",nullable = false,columnDefinition = "int default 1 COMMENT '白名单状态,1:正常,2:已取消'")
    private Integer status = 1;

    @Column(name = "cause", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '加入白名单原因'")
    private String cause;

    @Temporal(TIMESTAMP)
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '白名单生效时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '白名单结束时间'")
    private Date endTime;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public enum StatusEnum {
        DISABLED(2,"已取消"),
        ENABLED(1,"正常");

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

}
