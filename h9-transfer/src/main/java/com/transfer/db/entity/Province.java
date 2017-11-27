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
@Entity
@Table(name = "province")
@Getter
@Setter
@Accessors(chain = true)
public class Province {

    @Id
    @Column(name = "pid")
    private Long pid;

    @Column(name = "Pname")
    private String name;

}
