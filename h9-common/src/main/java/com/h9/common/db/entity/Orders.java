package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
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

    @Column(name = "supplier_name", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '商家名称'")
    private String supplierName;

    @Column(name = "addressId")
    private Long addressId;

    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(36) default '' COMMENT '收货人姓名'")
    private String userName;
    
    @Column(name = "user_phone", nullable = false, columnDefinition = "varchar(11) default '' COMMENT '收货人号码'")
    private String userPhone;
    
    @Column(name = "user_addres", columnDefinition = "varchar(128) default '' COMMENT '用户收货地址'")
    private String userAddres;
    
    @Column(name = "pay_methond",nullable = false,columnDefinition = "int default 0 COMMENT '支付方式'")
    private Integer payMethond;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '订单金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "pay_money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '需要支付的金额'")
    private BigDecimal payMoney = new BigDecimal(0);
    
    @Column(name = "pay_status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '支付状态 1待支付 2 已支付'")
    private Integer payStatus = 1;
    
    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '订单状态 '")
    private Integer status = 1;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) COMMENT ''")
    private User user;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @OrderBy(" id desc")
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderItems> orderItems = new ArrayList<>();

    /**
     * description: 标识订单类别
     * @see
     */
    @Column(name="order_type",columnDefinition = "int default 1 COMMENT'订单类别'")
    private Integer orderType ;
    public enum orderTypeEnum{

        MOBILE_RECHARGE(1, "话费充值"),
        DIDI_COUPON(2,"滴滴兑换"),
        OTHER(3, "其他");

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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
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
}
