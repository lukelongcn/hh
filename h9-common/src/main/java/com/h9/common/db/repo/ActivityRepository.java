package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/4 18:13
 */
public interface ActivityRepository extends BaseRepository<Activity> {
    Activity findByCode(String code);

    Activity findByIdNotAndCode(long id,String code);

    @Query("select o from Activity o")
    Page<Activity> findAllByPage(Pageable page);
}
