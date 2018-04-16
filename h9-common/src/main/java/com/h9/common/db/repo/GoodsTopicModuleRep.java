package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.order.GoodsTopicModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ln on 2018/4/8.
 */
public interface GoodsTopicModuleRep extends BaseRepository<GoodsTopicModule> {

    @Query("select o from GoodsTopicModule  o where o.delFlag = ?1 and o.goodsTopicTypeId = ?2 order by o.sort")
    Page<GoodsTopicModule> findByDelFlagAndGoodsTopicTypeId(Integer delFlag, Long topicTypeId, Pageable pageable);

    @Query("select o from GoodsTopicModule  o where o.delFlag = ?1 and o.goodsTopicTypeId = ?2 order by o.sort")
    List<GoodsTopicModule> findByDelFlagAndGoodsTopicTypeId(Integer delFlag, Long topicTypeId);
}
