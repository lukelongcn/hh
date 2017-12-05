package com.transfer.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/2
 * Time: 15:44
 */

@Entity
@Table(name = "t_BlackList")
public class T_BlackList extends BaseEntity {


    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "Userid")
    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
