package com.h9.common.db.entity;

import com.alibaba.fastjson.JSON;
import com.h9.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author: George
 * @date: 2017/10/30 13:41
 */
@Entity
@Table(name = "global_property",uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class GlobalProperty extends BaseEntity  {
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(50) default '' COMMENT '键'")
    private String code;

    @Column(name = "val", nullable = false, columnDefinition = "varchar(512) default '' COMMENT '值'")
    private String val;

    @Column(name = "description", nullable = false, columnDefinition = "varchar(512) default '' COMMENT '说明'")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVal() {

        return val;
    }

    public void setVal(Object val) {
        if(val instanceof String){
            this.val = val.toString();
        }else{
            this.val = JSON.toJSONString(val);
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
