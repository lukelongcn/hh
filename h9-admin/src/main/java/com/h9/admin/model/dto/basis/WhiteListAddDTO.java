package com.h9.admin.model.dto.basis;

import com.h9.common.db.entity.config.WhiteUserList;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author: George
 * @date: 2017/12/1 11:05
 */
public class WhiteListAddDTO {

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "备注",required = true)
    private String cause;

    @ApiModelProperty(value = "生效时间",required = true)
    @NotNull(message = "生效时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "失效时间",required = true)
    @NotNull(message = "失效时间不能为空")
    private Date endTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

     public WhiteUserList toWhiteUserList() {
        WhiteUserList whiteUserList = new WhiteUserList();
        BeanUtils.copyProperties(this,whiteUserList);
        return whiteUserList;
    }

}
