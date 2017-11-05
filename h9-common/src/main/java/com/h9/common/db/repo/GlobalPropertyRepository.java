package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GlobalProperty;

/**
 * @author: George
 * @date: 2017/11/3 10:57
 */
public interface GlobalPropertyRepository extends BaseRepository<GlobalProperty> {

    GlobalProperty findByCode(String code);

    GlobalProperty findByIdNotAndCode(long id,String code);

}
