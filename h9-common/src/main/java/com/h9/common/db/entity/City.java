package com.h9.common.db.entity;

import javax.persistence.*;

/**
 * description: 城市
 */
@Table(name = "city")
@Entity
public class City {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "pid")
    private Province province;

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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
