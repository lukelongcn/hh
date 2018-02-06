package com.h9.api.model.vo;

import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;
import com.h9.common.utils.DateUtil;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 李圆 on 2018/1/2
 */
@Data
public class SignVO {

    private BigDecimal cashBack = new BigDecimal(0);

    private Integer signCount = 0;

    private String nickName;

    private Integer signDays = 0;

    protected String createTime ;


    public SignVO(UserSign userSign) {
        this.cashBack = userSign.getCashBack();
        this.createTime = DateUtil.formatDate(userSign.getCreateTime(), DateUtil.FormatType.MINUTE);
        User user = userSign.getUser();
        if (user != null) {
            nickName = user.getNickName();
        }
        signDays = user.getSignDays();
        signCount = user.getSignCount();
    }


}
