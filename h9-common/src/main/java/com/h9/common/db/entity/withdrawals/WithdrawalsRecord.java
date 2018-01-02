package com.h9.common.db.entity.withdrawals;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.account.BankType;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserBank;
import com.h9.common.db.entity.user.UserRecord;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description: 提现订单表
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 14:32
 */

@Entity
@Table(name = "withdrawals_record",indexes = {@Index(columnList = "user_id")} )
public class WithdrawalsRecord extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;
    
    @Column(name = "user_id", columnDefinition = "bigint(20) default null COMMENT '用户id'")
    private Long userId;
    
    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现金额'")
    private BigDecimal money = new BigDecimal(0);
    
//    @Column(name = "surplus_balance",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '提现后剩余余额'")
//    private BigDecimal surplusBalance = new BigDecimal(0);

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_bank_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '用户银行卡'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    private UserBank userBank;

    @Column(name = "name", columnDefinition = "varchar(32) default '' COMMENT '持卡人名'")
    private String name;

    @Column(name = "bank_no", columnDefinition = "varchar(64) default '' COMMENT '银行卡号'")
    private String bankNo;

    @Column(name = "bank_name", columnDefinition = "varchar(64) default '' COMMENT '所属银行'")
    private String bankName;

    @Column(name = "provice",  columnDefinition = "varchar(64) default '' COMMENT '开户省'")
    private String province;

    @Column(name = "city", columnDefinition = "varchar(64) default '' COMMENT '开户城市'")
    private String city;

    @Column(name = "phone", columnDefinition = "varchar(64) default '' COMMENT '用户手机号'")
    private String phone;

    /**
     * description:
     * @see statusEnum
     */
    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT ''")
    private Integer status = 1;

    @Column(name = "remarks", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '备注'")
    private String remarks;

    @Column(name = "order_id", columnDefinition = "bigint(20) default null COMMENT '相关第三方订单id'")
    private Long orderId;

    @Column(name = "order_no",  columnDefinition = "varchar(64) default '' COMMENT '第三方相关订单编号'")
    private String orderNo;


    @Temporal(TIMESTAMP)
    @Column(name = "finish_time", columnDefinition = "datetime COMMENT '转账成功时间'")
    private Date finishTime;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_record_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    private UserRecord userRecord;


    public enum statusEnum{
        BANK_HANDLER(2,"银行转账中"),
//        BANK_TANSLATE(2,"银行转账完成"),
        FINISH(3, "银行转账完成"),
        FAIL(4, "银行转账失败"),
        WITHDRA_EXPCETION(6, "提现异常"),
        CANCEL(5,"退回");

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
    }



    public WithdrawalsRecord(User user, BigDecimal money , UserBank userBank , String remarks) {
        this.userId = user.getId();
        this.money = money;
        this.userBank = userBank;
        this.remarks = remarks;
        BankType bankType = userBank.getBankType();
        if(bankType!=null){
            this.bankName = bankType.getBankName();
        }
        this.name = userBank.getName();
        this.bankNo = userBank.getNo();
        this.province = userBank.getProvince();
        this.city = userBank.getCity();
        this.phone = user.getPhone();

    }

    public WithdrawalsRecord(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public UserBank getUserBank() {
        return userBank;
    }

    public void setUserBank(UserBank userBank) {
        this.userBank = userBank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord)
    {
        this.userRecord = userRecord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
