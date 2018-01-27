package com.h9.api.provider.model;

import com.h9.common.db.entity.UserBank;
import com.h9.common.utils.DateUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * WithdrawDTO:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/25
 * Time: 15:21
 */
@Data
public class WithdrawDTO {
    //提现金额
    private BigDecimal money;
    private long moneyPercent;
    private Date date;
    private String bankName;
    private String name;
    private String no;
    private String bankTypeCode="";
    private String province;
    private String city;
    private String orderNo;
    private Long orderId;

    public WithdrawDTO() {
    }

    public WithdrawDTO(UserBank userBank, BigDecimal money, long orderId, String orderNo) {
        BeanUtils.copyProperties(userBank,this);
        this.money = money;
        moneyPercent = money.multiply(new BigDecimal(100)).longValue();

        this.orderId = orderId;
        this.orderNo = orderNo;
        bankTypeCode = userBank.getBankType().getCode();
        bankName = userBank.getBankType().getBankName();
        date = new Date();
        bankTypeCode = "ICBC";
    }

    public String getDate(){
        return DateUtil.toFormatDateString(date, "yyyyMMdd");
    }



}
