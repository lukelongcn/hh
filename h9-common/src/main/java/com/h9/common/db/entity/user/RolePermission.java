package com.h9.common.db.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: George
 * @date: 2017/11/27 17:33
 */
@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {

    @Id
    @Column(name = "permission_id",nullable = false,  columnDefinition = "bigint(20)  COMMENT '权限id'")
    private Long permissionId;

    @Id
    @Column(name = "role_id",nullable = false,  columnDefinition = "bigint(20)  COMMENT '角色id'")
    private Long roleId;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
