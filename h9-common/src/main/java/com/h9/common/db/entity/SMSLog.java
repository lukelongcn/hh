package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 短信发送记录表
 */
@Entity
@Table(name = "sms_log")
public class SMSLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(255) default '' COMMENT '短信内容'")
    private String content;

    @Column(name = "code",  columnDefinition = "varchar(32) default '' COMMENT '短信验证码code'")
    private String code;

    @Column(name = "phone", nullable = false, columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;

    public SMSLog(String content, String phone,String code, boolean status) {
        this.content = content;
        this.phone = phone;
        this.status = status;
        this.code = code;
    }


    /**
     * description: 发送状态 成功/不成功
     */
    @Column(name = "status")
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SMSLog( ) {
    }
}
