package com.h9.api.model.dto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
public class PayNotifyVO {

    private String notify_time; // 通知时间 Date 通知的发送时间。格式为 yyyy-MM-dd HH:mm:ss
    private String notify_type; // 通知类型 String 通知的类型
    private long notify_id;   // 通知校验 ID String 通知校验 ID。notify_id 子系统必须记录用于退款业务
    private String order_id;    //订单号
    private String total_fee;   //订单总金额
    private String cash_fee;   // 实际支付金额
    private String coupon_fee; // 代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额，详见支付金额
    private String coupon_rate; //折扣率用于计算多次退款的实际退款金额
    private Integer coupon_count;// 代金券或立减优惠使用数量
    private String pay_time;
    private int pay_way;  //支付方式
    private String sub_mchid;

    /** 业务数据 */
    private String app_id;
    private String pay_type;
    
    /*三方支付方交易号*/
    private String trade_no;

    private int pay_channel;
    
    private String pay_order_id;//支付订单号
    
    public PayNotifyVO() {
	}
    
    public PayNotifyVO(long notify_id, String order_id, BigDecimal total_fee, BigDecimal cash_fee, Date pay_time, int pay_way) {
		super();
		this.notify_id = notify_id;
		this.order_id = order_id;
		this.total_fee = total_fee.toString();
		this.cash_fee = cash_fee.toString();
		this.pay_time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(pay_time);
		this.pay_way = pay_way;
	}

	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
