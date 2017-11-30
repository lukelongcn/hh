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

    public SimpleAddressVO(){}

    public SimpleAddressVO(Address address, User user){
        this.name = user.getNickName();
        this.tel = user.getPhone();
        this.address = address.getAddress();
    }
}
