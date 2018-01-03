package com.h9.common.db.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: George
 * @date: 2017/11/27 17:30
 */
@Entity
@Table(name = "user_role")
public class UserRole  implements Serializable {

    @Id
    @Column(name = "user_id",nullable = false,  columnDefinition = "bigint(20)  COMMENT '用户id'")
    private Long userId;

    @Id
    @Column(name = "role_id", nullable = false, columnDefinition = "bigint(20)  COMMENT '角色id'")
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
