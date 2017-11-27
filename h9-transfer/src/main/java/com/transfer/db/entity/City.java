package com.transfer.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by itservice on 2017/11/24.
 */
@Table(name = "City")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class City {

    @Id
    @Column(name ="cid")
    private Long cid;

    @Column(name = "cname")
    private String cname;

    @Column(name = "pid")
    private Long pid;


}
