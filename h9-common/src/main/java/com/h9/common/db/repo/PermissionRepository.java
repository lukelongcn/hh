package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.Permission;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/27 17:53
 */
public interface PermissionRepository extends BaseRepository<Permission> {
    @Query("select distinct p from User u,UserRole ur,Role r,RolePermission rp,Permission p" +
            " where u.id = ur.userId and ur.roleId = r.id and r.id = rp.roleId and rp.permissionId = p.id" +
            " and u.id = ?1 and p.accessCode = ?2")
    Permission findByUserIdAndAccessCode(long userId,String accessCode);

    Permission findByAccessCode(String accessCode);
}
