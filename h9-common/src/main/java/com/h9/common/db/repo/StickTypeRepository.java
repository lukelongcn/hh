package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.community.StickType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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


   default PageResult<StickType> findAllType(int page, int limit){
       Page<StickType> stickList = findAllType(pageRequest(page, limit));
       return new PageResult(stickList);
   }
    @Query("select s from StickType s where s.state = 1 order by s.createTime desc ")
    Page<StickType> findAllType(Pageable pageRequest);

    @Query("select s from StickType s where s.state = 1 order by s.createTime desc ")
    List<StickType> findAllTypeList();
}
