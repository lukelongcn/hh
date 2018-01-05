package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserSign;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 李圆 on 2018/1/2
 */
@Repository
public interface UserSignRepository extends BaseRepository<UserSign> {

    @Query(value = "select s.* from user_sign s where s.user_id = ?1  order by s.create_time DESC limit 1 ",nativeQuery = true)
    UserSign findLastSign(long userId);

    @Query(value = "select s.* from user_sign s order by s.create_time DESC limit 10 ",nativeQuery = true)
    List<UserSign> findListNewSign();


    @Query("select s from UserSign s where s.user.id = ?1 order by s.createTime DESC")
    Page<UserSign> findUserSignList(long userId, Pageable pageRequest);

    default PageResult<UserSign> findUserSignList(long userId, Integer page, Integer limit){
        Page<UserSign> UserSign =  findUserSignList(userId,pageRequest(page,limit));
        return new PageResult(UserSign);
    }



}
