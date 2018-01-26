package com.h9.api.provider.model;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * SuNingContent:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/26
 * Time: 15:17
 */
@Data
public class SuNingContent {
    private String batchNo;
    private String merchantNo;
    private String dataSource;
    private int totalNum;
    private long totalAmount;
    private int successNum;
    private long sucessAmount;
    private int failNum;
    private long failAmount;
    private long poundage;
    private String status;
    private String errorCode;
    private String errorMsg;
    private String transferOrders;

}
