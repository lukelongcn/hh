package com.h9.admin.repository;

import com.h9.admin.model.po.SystemUser;
import com.h9.common.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author: George
 * @date: 2017/10/30 18:51
 */
@Repository
public interface SystemUserRepository extends BaseRepository<SystemUser> {
    @Query(value = "select o from SystemUser o where o.name=?1 and o.password=?2")
    SystemUser findByNameAndPassword(String name,String password);

    @Query(value = "select o from SystemUser o where o.name=?1")
    SystemUser findByName(String name);
}
