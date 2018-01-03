package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.user.UserBank;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: George
 * @date: 2017/11/30 15:54
 */
public class UserBankInfoVO {
    @Id
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "持卡人名")
    private String name;

    @ApiModelProperty(value = "银行卡号")
    private String no;

    @ApiModelProperty(value = "开户省")
    private String province;

    @ApiModelProperty(value = "开户城市")
    private String city;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawMoney;

    @ApiModelProperty(value = "提现次数")
    private Long withdrawCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = UserBank.StatusEnum.getNameById(status);
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public Long getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(Long withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    public UserBankInfoVO() {
    }

    public UserBankInfoVO(UserBank userBank) {
        BeanUtils.copyProperties(userBank,this);
    }

    public static UserBankInfoVO toUserBankVO(UserBank userBank) {
        return new UserBankInfoVO(userBank);
    }

    public static List<UserBankInfoVO> toUserBankVO(List<UserBank> userBankList) {
        return userBankList==null?null:userBankList.stream().map(item -> toUserBankVO(item)).collect(Collectors.toList());
    }
}
