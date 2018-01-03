package com.h9.api.model.vo;

import com.h9.common.db.entity.config.HtmlContent;

import org.springframework.beans.BeanUtils;

/**
 * Created by 李圆 on 2017/12/8
 * 单页配置json接口使用
 */
public class AgreementVO {
    private String content;

    private String title;

    private String code;

    public AgreementVO(HtmlContent htmlContent){
            BeanUtils.copyProperties(htmlContent,this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
