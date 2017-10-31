package com.h9.admin.repository;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author: George
 * @date: 2017/10/30 18:51
 */
@Repository
public interface UserRepository extends BaseRepository<User> {
    @Query(value = "select o from User o where o.name=?1 and o.password=?2 and o.isAdmin=?3")
    User findByNameAndPasswordAndIsAdmin(String name,String password,Integer isAdmin);

    @Query(value = "select o from User o where o.name=?1")
    User findByName(String name);
}
