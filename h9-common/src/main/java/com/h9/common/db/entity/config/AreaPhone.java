package com.h9.common.db.entity.config;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:区域手机号
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/4
 * Time: 14:51
 */

@Entity
@Table(name = "areaPhone")
public class AreaPhone extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "area", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '所属区域'")
    private String area;
    
    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '用户名'")
    private String name;
    
    @Column(name = "phone", nullable = false, columnDefinition = "varchar(11) default '' COMMENT '手机号'")
    private String phone;
    
    @Column(name = "type",columnDefinition = "int default 0 COMMENT '类型 1  2'")
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
