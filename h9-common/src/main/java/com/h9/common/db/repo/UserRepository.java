package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserRepository
 * @Description: User 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByPhone(String phone);

    @Query("select u from User u where u.openId = ?1 and u.status<>2")
    User findByOpenId(String openId);


    @Query(value = "select o from User o where o.phone=?1 and o.password=?2 and o.isAdmin=?3")
    User findByPhoneAndPasswordAndIsAdmin(String phone, String password, Integer isAdmin);
}
