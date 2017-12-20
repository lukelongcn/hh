package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by itservice on 2017/11/7.
 */
@Table(name = "withdrawals_request")
@Entity
public class WithdrawalsRequest extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "bank_return_data", columnDefinition = "varchar(1000) default '' COMMENT '银行返回的数据' ")
    private String bankReturnData;
    @Column(name = "mer_seq_id", columnDefinition = "varchar(255) default '' COMMENT '商户订单' ")
    private String merSeqId;
    @Column(name = "card_no", columnDefinition = "varchar(255) default '' COMMENT '提现卡号' ")
    private String cardNo;
    @Column(name = "ser_name", columnDefinition = "varchar(255) default '' COMMENT '提现人名' ")
    private String usrName;
    @Column(name = "open_bank", columnDefinition = "varchar(255) default '' COMMENT '开户银行' ")
    private String openBank;
    @Column(name = "prov", columnDefinition = "varchar(255) default '' COMMENT '开户省份' ")
    private String prov;
    @Column(name = "city", columnDefinition = "varchar(255) default '' COMMENT '城市' ")
    private String city;
    @Column(name = "trans_amt", columnDefinition = "varchar(255) default '' COMMENT '金额' ")
    private String transAmt;
    @Column(name = "purpose", columnDefinition = "varchar(255) default '' COMMENT '目的' ")
    private String purpose;
    @Column(name = "version", columnDefinition = "varchar(255) default '' COMMENT '' ")
    private String version = "20151207";
    @Column(name = "signFlag", columnDefinition = "varchar(255) default '' COMMENT '' ")
    private String signFlag = "1";
    @Column(name = "term_type", columnDefinition = "varchar(255) default '' COMMENT '' ")
    private String termType = "7";
    /**
     * description:  s	成功
     * 2	处理中
     * 3	处理中
     * 4	处理中
     * 5	处理中
     * 6	失败
     * 7	处理中
     * 8	处理中
     */
//    @Column(name = "stat", columnDefinition = "varchar(10) COMMENT '交易状态'")
//    private String stat;

    @Column(name = "withdraw_id")
    private Long withdrawCashId;

    @Column(name="mer_date")
    private String merDate;

    public String getMerDate() {
        return merDate;
    }

    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }

    public Long getWithdrawCashId() {
        return withdrawCashId;
    }

    public void setWithdrawCashId(Long withdrawCashId) {
        this.withdrawCashId = withdrawCashId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
