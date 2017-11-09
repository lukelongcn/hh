package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/3 14:13
 */
public interface ArticleTypeRepository extends BaseRepository<ArticleType> {
    ArticleType findByCode(String code);
    @Query("select a from ArticleType a where a.enable<>2 order by a.sort asc ,a.id desc ")
    Page<ArticleType> findAll(Pageable pageable);
}
