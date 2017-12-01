package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.WhiteUserList;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/11/29.
 */
public interface WhiteUserListRepository extends BaseRepository<WhiteUserList> {

    @Query(value = "select o from WhiteUserList o where o.startTime < ?2 and o.endTime > ?2 and o.status = 1 and o.userId = ?1")
    List<WhiteUserList> findByUserId(Long userId, Date date);
}
