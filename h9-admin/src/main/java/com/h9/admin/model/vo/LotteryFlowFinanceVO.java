package com.h9.admin.model.vo;

import com.h9.common.db.entity.LotteryFlow;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/8 13:52
 */
public class LotteryFlowFinanceVO {
    @ApiModelProperty(value = "id" )
    private Long id;

    @ApiModelProperty(value = "昵称" )
    private String nickName;

    @ApiModelProperty(value = "兑奖码" )
    private String code;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "金额" )
    private BigDecimal money;

    @ApiModelProperty(value = "发奖时间" )
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public LotteryFlowFinanceVO(LotteryFlow lotteryFlow){
        BeanUtils.copyProperties(lotteryFlow, this);
        this.setCode(lotteryFlow.getReward().getCode());
        this.setPhone(lotteryFlow.getUser().getPhone());
        this.setNickName(lotteryFlow.getUser().getNickName());
    }

}
