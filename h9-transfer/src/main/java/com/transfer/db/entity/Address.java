package com.transfer.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by itservice on 2017/11/15.
 */
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "Address")
@Entity
public class Address {
    @Id
    @Column(name = "ID")
    private Long ID;
    @Column(name = "Userid")

    private Long Userid;
    @Column(name = "Consignee")

    private String Consignee;
    @Column(name = "ConsigneePhone")

    private String ConsigneePhone;
    @Column(name = "Receivingaddress")

    private String Receivingaddress;
    @Column(name = "ADefault")

    private Integer ADefault;
    @Column(name = "Province")

    private String Province;
    @Column(name = "City")

    private String City;

    @Column(name = "District")
    private String District;

    @Column(name = "ProvincialCity")
    private String ProvincialCity;

}
