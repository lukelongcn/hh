package com.h9.common.modle.vo.admin.finance;

import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
@Data
public class UserAccountVO {
    @ApiModelProperty(value = "用户id ")
    private Long userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value ="昵称")
    private String nickName;

    @ApiModelProperty(value ="余额")
    private BigDecimal balance = new BigDecimal(0);

    @ApiModelProperty(value ="v币")
    private BigDecimal vCoins = new BigDecimal(0);
    @ApiModelProperty(value ="注册时间")
    private Date createTime;

    public UserAccountVO() {
    }

    public UserAccountVO(User user, UserAccount userAccount){
        BeanUtils.copyProperties(userAccount,this);
        BeanUtils.copyProperties(user,this);
    }

    public static UserAccountVO toUserAccountVO(UserAccount userAccount){
        UserAccountVO userAccountVO = new UserAccountVO();
        //BeanUtils.copyProperties(user,userAccountVO);
        BeanUtils.copyProperties(userAccount,userAccountVO);
        return userAccountVO;
    }




}
