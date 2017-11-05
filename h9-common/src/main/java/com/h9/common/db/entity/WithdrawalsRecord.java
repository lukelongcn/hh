package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.dialect.unique.DB2UniqueDelegate;

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
@Table(name = "withdrawals_record")
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
    @JoinColumn(name = "user_bank_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '用户银行卡'")
    private UserBank userBank;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '提现状态 ： 1提现中  2银行转账中 3银行转账完成 ，4 提现异常'")
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
    @JoinColumn(name = "user_record_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    private UserRecord userRecord;

    @Column(name="bank_return_data",columnDefinition = "varchar(1000) default '' COMMENT '银行返回的数据' ")
    private String bankReturnData;
    @Column(name = "mer_seq_id",columnDefinition = "varchar(255) default '' COMMENT '商户订单' ")
    private String merSeqId;
    @Column(name = "card_no",columnDefinition = "varchar(255) default '' COMMENT '提现卡号' ")
    private String cardNo;
    @Column(name = "ser_name",columnDefinition = "varchar(255) default '' COMMENT '提现人名' ")
    private String usrName;
    @Column(name = "open_bank",columnDefinition = "varchar(255) default '' COMMENT '开户银行' ")
    private String openBank;
    @Column(name = "prov",columnDefinition = "varchar(255) default '' COMMENT '开户省份' ")
    private String prov;
    @Column(name = "city",columnDefinition = "varchar(255) default '' COMMENT '城市' ")
    private String city;
    @Column(name = "trans_amt",columnDefinition = "varchar(255) default '' COMMENT '金额' ")
    private String transAmt;
    @Column(name = "purpose",columnDefinition = "varchar(255) default '' COMMENT '目的' ")
    private String purpose;
    @Column(name = "version",columnDefinition = "varchar(255) default '' COMMENT '' ")
    private String version = "20151207";
    @Column(name = "signFlag",columnDefinition = "varchar(255) default '' COMMENT '' ")
    private String signFlag = "1";
    @Column(name = "term_type",columnDefinition = "varchar(255) default '' COMMENT '' ")
    private String termType = "7";

    public WithdrawalsRecord(Long userId, BigDecimal money , UserBank userBank ,String remarks) {
        this.userId = userId;
        this.money = money;
        this.userBank = userBank;
        this.remarks = remarks;
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

//    public BigDecimal getSurplusBalance() {
//        return surplusBalance;
//    }

//    public void setSurplusBalance(BigDecimal surplusBalance) {
//        this.surplusBalance = surplusBalance;
//    }

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

    public String getBankReturnData() {
        return bankReturnData;
    }

    public void setBankReturnData(String bankReturnData) {
        this.bankReturnData = bankReturnData;
    }

    public String getMerSeqId() {
        return merSeqId;
    }

    public void setMerSeqId(String merSeqId) {
        this.merSeqId = merSeqId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }
}
