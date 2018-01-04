package com.h9.common.db.entity.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.h9.common.db.entity.order.China;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 李圆 on 2018/1/3
 */
@Entity
@Table(name = "user_advice")
public class UserAdvice {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint(20) default 0 COMMENT '用户id'")
    private Long userId;

    @Column(name = "advice", columnDefinition = "varchar(200) default '' COMMENT '反馈内容'")
    private String advice;

    @Column(name = "advice_img",columnDefinition = "varchar(200) default '' COMMENT '意见反馈图片'")
    private String adviceImg;

    @Column(name = "connect", columnDefinition = "varchar(30) default '' COMMENT '联系方式'")
    private String connect;

    @Column(name = "anonymous",columnDefinition = "tinyint default 0 COMMENT '状态， 1：匿名，0：不匿名'")
    private Integer anonymous;

    @Column(name = "advice_type",columnDefinition = "varchar(32) default '' COMMENT '意见反馈类别'")
    private String adviceType;

    public List<String> getAdviceImg() {
        try {
            return JSONArray.parseArray(adviceImg, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void setAdviceImg(List<String> adviceImg) {
       try {
           this.adviceImg = JSONArray.toJSONString(adviceImg);
       }catch (Exception e){
           e.printStackTrace();
       }
       this.adviceImg = JSONArray.toJSONString("");
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
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

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }
}
