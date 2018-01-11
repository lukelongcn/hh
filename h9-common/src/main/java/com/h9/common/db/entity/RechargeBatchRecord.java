package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Created by itservice on 2018/1/11.
 */
@Table(name = "recharge_batch_record")
@Entity
@Data
@Accessors(chain = true)
public class RechargeBatchRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recharge_batch_id")
    private Long rechargeBatchId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "money")
    private String money;

    /**
     * description: 1 为充值 0为未充值 3 为 查无此人
     */
    @Column(name = "status")
    private Integer status = 0;


    @Column(name = "remarks")
    private String remarks;

}
