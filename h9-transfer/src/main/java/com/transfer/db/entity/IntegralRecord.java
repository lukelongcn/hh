package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/27
 * Time: 14:20
 */

@Getter
@Setter
@Entity
@Table(name = "integralRecord")
public class IntegralRecord {

    @Id
    @Column(name="RecordID")
    private Long RecordID;

    @Column(name="UserId")
    private Long UserId;

    @Column(name="ActivityId")
    private Long ActivityId;

    @Column(name="GoodsInfoId")
    private Long GoodsInfoId;

    @Column(name="GoodsType")
    private Long GoodsType;

    @Column(name="Recordtime")
    private Date Recordtime;

    @Column(name="GoodsIntegral")
    private Long GoodsIntegral;

    @Column(name="RecordState")
    private Long RecordState;

    @Column(name="GoodsName")
    private String GoodsName;

    @Column(name="RecordIntegral")
    private Long RecordIntegral;

    @Column(name="RccordGuid")
    private String RccordGuid;

    @Column(name="OrderID")
    private Long OrderID;

    @Column(name="CodeID")
    private String CodeID;

}
