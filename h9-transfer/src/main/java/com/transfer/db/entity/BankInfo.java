package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/21
 * Time: 17:20
 */

@Getter
@Setter
@Entity
@Table(name = "BankInfo")
public class BankInfo {


    @Id
    @Column(name = "Oid")
    private String id;

    @Column(name = "BankBin")
    private String BankBin;

    @Column(name = "newb")
    private String newb;
}
