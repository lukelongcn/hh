package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.user.UserCount;

import java.util.List;

/**
 * Created by itservice on 2018/1/29.
 */
public interface UserCountRepository extends BaseRepository<UserCount> {
    List<UserCount> findByDayKey(String dateKey);
}
