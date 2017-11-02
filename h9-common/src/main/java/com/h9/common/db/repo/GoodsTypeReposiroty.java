package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GoodsType;

/**
 * Created by itservice on 2017/11/2.
 */
public interface GoodsTypeReposiroty extends BaseRepository<GoodsType>{

    GoodsType findByCode(Integer code);

}
