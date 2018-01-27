package com.h9.api.provider.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * SuNingOrders:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/26
 * Time: 17:41
 */
@Data
public class SuNingOrders {

    private String serialNo;
    private String receiverName;
    private String receiverCardNo ;
    private String receiverType ;
    private String bankName ;
    private String bankCode ;
    private String payeeBankLinesNo ;
    private String bankProvince ;
    private String bankCity ;
    private long id;
    private long amount;
    private long poundage;
    private String success;
    private String payTime;
    private String errMessag;
    private String  refundTicket ;


}
