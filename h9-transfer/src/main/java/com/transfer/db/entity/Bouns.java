package com.transfer.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/24
 * Time: 18:50
 */

@Getter
@Setter
@Entity
@Table(name = "bouns")
public class Bouns implements Serializable {

    @Id
    @Column(name="BounsOID")
    private String BounsOID;

    @Column(name="CodeId")
    private String CodeId;

    @Column(name="BounsTime")
    private Date BounsTime;

    @Column(name="CodeOID")
    private String CodeOID;

    @Column(name="Bouns")
    private BigDecimal Bouns;

    @Column(name="codemd")
    private String codemd;

    @Column(name="IntegralPoints")
    private String IntegralPoints;



}
