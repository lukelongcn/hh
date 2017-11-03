package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.ArticleType;

/**
 * @author: George
 * @date: 2017/11/3 14:13
 */
public interface ArticleTypeRepository extends BaseRepository<ArticleType> {
    ArticleType findByCode(String code);
}
