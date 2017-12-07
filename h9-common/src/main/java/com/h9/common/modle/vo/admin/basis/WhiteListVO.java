package com.h9.common.modle.vo.admin.basis;

import com.h9.common.db.entity.User;
import com.h9.common.db.entity.WhiteUserList;
import com.h9.common.modle.vo.admin.BasisVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;


/**
 * @author: George
 * @date: 2017/12/1 16:37
 */
public class WhiteListVO extends BasisVO{
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "状态,1:正常,2:已取消")
    private Integer status = 1;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc ;

    @ApiModelProperty(value = "备注")
    private String cause;

    @ApiModelProperty(value = "生效时间")
    private Date startTime;

    @ApiModelProperty(value = "失效时间")
    private Date endTime;

    @ApiModelProperty(value = "昵称")
    private String nickName;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public WhiteListVO() {
    }

    public WhiteListVO(WhiteUserList whiteUserList, User user) {
        this.nickName = user.getNickName();
        BeanUtils.copyProperties(whiteUserList,this);
        this.setStatusDesc(WhiteUserList.StatusEnum.getNameById(user.getStatus()));
    }
}
