package com.transfer.db.model;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * Description:界面显示
 * TastingVo:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 18:22
 */
public class TastingVo {

    private String md5Code;

    private String orderOID;

    private String planName;

    private String JPMC;

    private String BZ;

    private String codeId;

    public TastingVo(String md5Code, String orderOID, String planName, String JPMC, String BZ, String codeId) {
        this.md5Code = md5Code;
        this.orderOID = orderOID;
        this.planName = planName;
        this.JPMC = JPMC;
        this.BZ = BZ;
        this.codeId = codeId;
    }

    public TastingVo() {
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getOrderOID() {
        return orderOID;
    }

    public void setOrderOID(String orderOID) {
        this.orderOID = orderOID;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getJPMC() {
        return JPMC;
    }

    public void setJPMC(String JPMC) {
        this.JPMC = JPMC;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }
}
