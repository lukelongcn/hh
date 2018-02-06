package com.h9.admin.model.vo;

import com.h9.common.db.entity.user.User;
import com.h9.common.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by 李圆 on 2018/1/15
 */
@Data
public class UserSignVO {
    private Long userId;
    private String avatar ;
    private String nickName;
    private Integer signCount = 0;
    private Integer signDays = 0;
    private BigDecimal cashBackMoney = new BigDecimal(0);
    private String newCreateTime;

    public UserSignVO(User user, BigDecimal cashBackMoney, Date createTime){
        this.nickName = user.getNickName();
        this.userId = user.getId();
        this.avatar = user.getAvatar();
        this.cashBackMoney = cashBackMoney;
        this.signCount = user.getSignCount();
        this.signDays = user.getSignDays();
        this.newCreateTime = DateUtil.formatDate(createTime, DateUtil.FormatType.SECOND);
    }
}
