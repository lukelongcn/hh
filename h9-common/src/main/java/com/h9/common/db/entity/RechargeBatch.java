package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Created by itservice on 2018/1/11.
 */
@Entity
@Table(name = "recharge_batch")
@Data
@Accessors(chain = true)
public class RechargeBatch extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_no")
    private String batchNo;

    @Column(name = "remark")
    private String remark;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "opt_user")
    private Long optUserId;

}
