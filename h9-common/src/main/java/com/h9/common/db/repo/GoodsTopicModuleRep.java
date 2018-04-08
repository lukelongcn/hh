package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.order.GoodsTopicModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Ln on 2018/4/8.
 */
public interface GoodsTopicModuleRep extends BaseRepository<GoodsTopicModule> {

    Page<GoodsTopicModule> findByDelFlag(Integer delFlag, Pageable pageable);
}
