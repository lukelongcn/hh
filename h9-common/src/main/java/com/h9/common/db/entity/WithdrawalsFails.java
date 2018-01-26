package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;


/**
 * description: 提现失败表
 */
@Entity
@Table(name = "withdrawals_fails")
public class WithdrawalsFails extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name="status",columnDefinition = "int default 0 COMMENT'处理状态,1为未处理 2为处理完成'")
    private Integer status;

    @Column(name="bank_return_data",columnDefinition = "varchar(2000) default '' COMMENT '银行返回的数据' ")
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

    public WithdrawalsFails(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
