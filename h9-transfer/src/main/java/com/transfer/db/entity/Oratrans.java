package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/1
 * Time: 18:36
 */

@Entity
@Table(name = "oratrans")
public class Oratrans {


    @Id
    @Column(name = "transDate")
    private String transDate;


}
