package com.h9.common.db.entity.custom;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.order.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 订制模板表
 */
@Entity
@Table(name = "custom_module_goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomModuleGoods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "custom_module_id")
    private Long customModuleId;

    @Column(name = "del_flag")
    private Integer delFlag = 0;

    @Column(name = "numbers")
    private Long numbers;
}
