package com.h9.api.model.vo;

import com.h9.common.db.entity.Address;
import com.h9.common.db.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2017/11/30.
 */
@Data
@Accessors(chain = true)
public class SimpleAddressVO {
    private String name = "";
    private String tel ="";
    private String address="";
    private Long id;


    private Long userId;

    // 默认地址
    private Integer defaultAddress;

    // 所在省
    private String province;

    // 所在市
    private String city;

    // 所在区
    private String distict;


    public SimpleAddressVO(){}

    public SimpleAddressVO(Address address, User user){
        this.name = user.getNickName();
        this.tel = user.getPhone();
        this.address = address.getAddress();
        this.id = address.getId();
        this.city = address.getCity();
        this.defaultAddress = address.getDefaultAddress();
        this.province = address.getProvince();
        this.distict = address.getDistict();
        this.userId =address.getUserId();
    }
}
