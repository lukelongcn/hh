package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 11:31
 */

@Entity
@Table(name = "t_rmpr")
public class T_rmpr  {


    @Id
    @Column(name="T_rmprOID")
    private String id;

    @Column(name = "regional")
    private String regional;

    @Column(name = "name")
    private String name;

    @Column(name = "mobileone")
    private String mobileone;

    private Integer type;
    @Column(name = "SYS_Created")
    private Date createTime;
    @Column(name = "SYS_LAST_UPD")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegional() {

        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileone() {
        return mobileone;
    }

    public void setMobileone(String mobileone) {
        this.mobileone = mobileone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
