package com.transfer.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by itservice on 2017/11/24.
 */
@Entity
@Table(name = "CardInfo")
@Getter
@Setter
@Accessors(chain = true)
public class CardInfo {


    @Id
    @Column(name = "CardId")
    private Long CardId;

    @Column(name = "BankName")
    private String BankName;

    @Column(name = "Userid")
    private Integer Userid;

    @Column(name = "Account")
    private String Account;

    @Column(name = "SignDate")
    private Date SignDate;

    @Column(name = "isdelete")
    private Integer isdelete;

    @Column(name = "cardName")
    private String cardName;

    @Column(name = "BankImg")
    private String BankImg;

    @Column(name = "Province")
    private Long Province;

    @Column(name = "City")
    private Long City;
}
