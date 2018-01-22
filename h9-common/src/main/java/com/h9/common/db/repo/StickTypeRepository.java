package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.community.StickType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: StickTypeRepository
 * @Description: StickType 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface StickTypeRepository extends BaseRepository<StickType> {

    StickType findByName(String name);

    @Query("select s from StickType  s where s.id = ?1 and s.state =1")
    StickType findById(long stickTypeId);
}
