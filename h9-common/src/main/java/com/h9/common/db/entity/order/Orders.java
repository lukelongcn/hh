package com.h9.common.db.entity.order;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 14:57
 */

@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "no", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '订单编号'")
    private String no;

    @Column(name = "supplier_id", columnDefinition = "bigint(20) default null COMMENT '供应商id'")
    private Long supplierId;

    @Column(name = "delivery_id", columnDefinition = "bigint(20) default null COMMENT '配送'")
    private Long deliveryId;

    @Column(name = "supplier_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '商家名称'")
    private String supplierName;

    @Column(name = "addressId")
    private Long addressId;

    @Column(name = "user_name", columnDefinition = "varchar(36) default '' COMMENT '收货人姓名'")
    private String userName;

    @Column(name = "user_phone", columnDefinition = "varchar(11) default '' COMMENT '收货人号码'")
    private String userPhone;

    @Column(name = "user_addres", columnDefinition = "varchar(128) default '' COMMENT '用户收货地址'")
    private String userAddres;

    /**
     * description: 支付方式
     *
     * @see PayMethodEnum
     */
    @Column(name = "pay_methond", nullable = false, columnDefinition = "int default 0 COMMENT '支付方式'")
    private Integer payMethond;

    @Column(name = "money", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '订单金额'")
    private BigDecimal money = new BigDecimal(0);


    @Column(name = "pay_money", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '需要支付的金额'")
    private BigDecimal payMoney = new BigDecimal(0);

    /**
     * description:
     * @see  PayStatusEnum
     */
    @Column(name = "pay_status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '支付状态 1待支付 2 已支付'")
    private Integer payStatus = 1;

    /**
     * description:
     * @see statusEnum
     */
    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '订单状态,1:待发货,2:已发货,3:已完成'")
    private Integer status = 1;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint(20) COMMENT ''")
    private User user;

    @Column(name = "goods_type",columnDefinition = "varchar(50) COMMENT'商品类型'")
    private String goodsType;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(" id desc")
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderItems> orderItems = new ArrayList<>();

    /**
     * description: 标识订单类别
     *
     * @see orderTypeEnum 订单类型
     */
    @Column(name = "order_type", columnDefinition = "varchar(50) default '' COMMENT'订单类别'")
    private String orderType;

//    @Column(name = "loginstics_number")
//    private String logisticsNumber;

    @Column(name = "express_name", columnDefinition = "varchar(128) default '' COMMENT '快递名称'")
    private String expressName;

    @Column(name = "order_from",columnDefinition = "int default 2 COMMENT '订单来源 1为酒元商场 2为其他'")
    private Integer orderFrom;

    @Column(name = "express_num",columnDefinition = "varchar(200) COMMENT '物流单号'")
    private String expressNum  = "";

    @Column(name = "province",columnDefinition = "varchar(50) default '' COMMENT ''")
    private String province;

    @Column(name = "district",columnDefinition = "varchar(50) default '' COMMENT ''")
    private String district;

    @Column(name = "city",columnDefinition = "varchar(50) default '' COMMENT ''")
    private String city;

    @Column(name="pay_info_id")
    private Long payInfoId;

    @Column(name = "orders_lottery_id",columnDefinition = "bigint COMMENT '大富贵Id'")
    private Long ordersLotteryId;

    public Long getOrdersLotteryId() {
        return ordersLotteryId;
    }

    public void setOrdersLotteryId(Long ordersLotteryId) {
        this.ordersLotteryId = ordersLotteryId;
    }

    public Long getPayInfoId() {
        return payInfoId;
    }

    public void setPayInfoId(Long payInfoId) {
        this.payInfoId = payInfoId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public enum PayMethodEnum {

        BALANCE_PAY(1, "余额支付"),
        VBPAY(2, "vb支付"),
        WX_PAY(3,"微信支付"),
        COUPON_PAY(4,"优惠券支付");

        private int code;
        private String desc;

        PayMethodEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }


        public String getDesc() {
            return desc;
        }

        public static PayMethodEnum findByCode(int code) {
            PayMethodEnum[] values = values();
            for (PayMethodEnum smsTypeEnum : values) {
                if (code == smsTypeEnum.getCode()) {
                    return smsTypeEnum;
                }
            }
            return null;
        }
    }


    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public Integer getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(Integer orderFrom) {
        this.orderFrom = orderFrom;
    }

//    public String getLogisticsNumber() {
//        return logisticsNumber;
//    }
//
//    public void setLogisticsNumber(String logisticsNumber) {
//        this.logisticsNumber = logisticsNumber;
//    }

    public enum orderTypeEnum {

        MATERIAL_GOODS(1, "实体商品"),
        VIRTUAL_GOODS(2, "虚拟商品");

        private int code;
        private String desc;

        orderTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum statusEnum{


        UNCONFIRMED(0,"未确认"),
        WAIT_SEND(1,"等待发货"),
        DELIVER(2,"等待收货"),
        CANCEL(3,"已取消"),
        FINISH(4, "交易成功"),
        FAIL(5,"交易失败"),
        REFUND_DEALING(6, "退货受理中"),
        REFUNDING(7,"退货中"),
        REJECT_REFUND(8,"不予退货"),
        REFUND_FINISH(9,"退货完成");

        private int code;
        private String desc;

        statusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static statusEnum findByCode(int code){
            statusEnum[] values = values();
            for(statusEnum enumEl : values){
                if(enumEl.code == code){

                    return enumEl;
                }
            }
            return null;
        }
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddres() {
        return userAddres;
    }

    public void setUserAddres(String userAddres) {
        this.userAddres = userAddres;
    }

    public Integer getPayMethond() {
        return payMethond;
    }

    public void setPayMethond(Integer payMethond) {
        this.payMethond = payMethond;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }



    public enum PayStatusEnum {

        UNPAID(1, "待支付"),
        PAID(2, "已支付");

        private int code;
        private String desc;

        PayStatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }


        public String getDesc() {
            return desc;
        }

        public static PayStatusEnum findByCode(int code) {
            PayStatusEnum[] values = values();
            for (PayStatusEnum smsTypeEnum : values) {
                if (code == smsTypeEnum.getCode()) {
                    return smsTypeEnum;
                }
            }
            return null;
        }
    }
}
