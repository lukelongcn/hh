package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by 李圆 on 2018/1/2
 */
@Repository
public interface UserSignRepository extends BaseRepository<UserSign> {
    UserSign findOne(long userId);

    @Query(value = "select s.* from user_sign s where s.user_id = ?1  order by s.create_time DESC limit 1 ",nativeQuery = true)
    UserSign findLastSign(long userId);
}
