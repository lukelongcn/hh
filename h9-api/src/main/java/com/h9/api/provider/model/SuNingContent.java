package com.h9.api.provider.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
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


    public List<SuNingOrders> getTransferOrders(){
        return JSONArray.parseArray(transferOrders, SuNingOrders.class);
    }

    public SuNingOrders getTransferOrder(){
        return getTransferOrders().get(0);
    }
}
