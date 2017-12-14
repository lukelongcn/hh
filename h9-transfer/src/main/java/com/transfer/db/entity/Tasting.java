package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.dialect.unique.DB2UniqueDelegate;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 18:12
 */

@Entity
@Table(name = "tasting")
public class Tasting  {


    @Column(name = "OrderOID")
    private String orderOID;
    
    @Column(name = "PlanName")
    private String planName;

    @Column(name = "BZ")
    private String BZ;

    @Column(name = "JPMC")
    private String JPMC;

    @Id
    @Column(name = "TastingOID")
    private String tastingOId;

//    TODO 这个地方要调整
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "",nullable = false,referencedColumnName="codemd")
    private Bouns bouns;

    public Bouns getBouns() {
        return bouns;
    }

    public void setBouns(Bouns bouns) {
        this.bouns = bouns;
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

    public String getTastingOId() {
        return tastingOId;
    }

    public void setTastingOId(String tastingOId) {
        this.tastingOId = tastingOId;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }
}
