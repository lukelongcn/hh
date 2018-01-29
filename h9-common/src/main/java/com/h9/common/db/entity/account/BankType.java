package com.h9.common.db.entity.account;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:bank类型
 * Created by 李圆
 * on 2017/11/2
 */
@Data
@Entity
@Table(name = "bank_type")
public class BankType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "bank_name", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '银行名称'")
    private String bankName;

    @Column(name = "color",nullable = false,columnDefinition = "varchar(32) default '' COMMENT '颜色'")
    private String color;

    @Column(name = "back_img",columnDefinition = "varchar(200) default '' COMMENT '银行图标'")
    private String bankImg;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '状态， 1：启用，0：禁用'")
    private Integer status;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '编码'")
    private String code;

    public enum StatusEnum {
        DISABLED(0,"禁用"),
        ENABLED(1,"启用");

        StatusEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
