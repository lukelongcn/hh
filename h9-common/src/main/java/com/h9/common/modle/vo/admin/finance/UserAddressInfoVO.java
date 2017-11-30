package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.Address;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * @author: George
 * @date: 2017/11/30 15:53
 */
public class UserAddressInfoVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "是否是默认地址")
    private String defaultAddress ;

    @ApiModelProperty(value = "状态")
    private String status;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        //this.status = ;
    }

    public UserAddressInfoVO() {
    }

    public UserAddressInfoVO(Address address) {
    }
}
