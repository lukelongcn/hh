package com.h9.admin.model.vo;

import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.order.Address;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAdvice;
import com.h9.common.utils.DateUtil;

import net.bytebuddy.asm.Advice;

import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.constraints.Size;

/**
 * Created by 李圆 on 2018/1/4
 */
public class UserAdviceVO {
    private long id;

    private long userId;

    private String advice;

    private List<String> adviceImgList;

    private String connect;

    private String adviceType;

    private String ip;

    private String createTime;


    public UserAdviceVO(UserAdvice userAdvice,Map<String,String> mapConfig) {
        BeanUtils.copyProperties(userAdvice,this);
        this.createTime = DateUtil.formatDate(userAdvice.getCreateTime(), DateUtil.FormatType.SECOND);
        this.adviceImgList = userAdvice.getAdviceImg();
        this.adviceType = mapConfig.get(String.valueOf(userAdvice.getAdviceType()));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public List<String> getAdviceImgList() {
        return adviceImgList;
    }

    public void setAdviceImgList(List<String> adviceImgList) {
        this.adviceImgList = adviceImgList;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
