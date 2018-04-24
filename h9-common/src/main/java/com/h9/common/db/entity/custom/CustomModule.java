package com.h9.common.db.entity.custom;

import com.h9.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/23.
 */
@Entity
@Table(name = "custom_module")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomModule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "module_type_id")
    private Long moduleTypeId;

    /**
     * 1 为删除 ，0 为未删
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

    @Column(name = "name")
    private String name;


}
