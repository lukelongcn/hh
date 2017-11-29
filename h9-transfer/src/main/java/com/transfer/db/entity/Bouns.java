package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

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
public class Bouns {

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
