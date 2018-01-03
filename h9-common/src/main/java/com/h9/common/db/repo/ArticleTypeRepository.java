package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/3 14:13
 */
public interface ArticleTypeRepository extends BaseRepository<ArticleType> {
    ArticleType findByCode(String code);
    @Query("select a from ArticleType a where a.enable<>2 order by a.enable desc , a.sort desc,a.id desc ")
    Page<ArticleType> findAll(Pageable pageable);
    
    @Query("select a from ArticleType a where a.enable<>2 and a.id=?1")
    ArticleType findOne(Long id);

    @Query("select o from ArticleType o where o.enable<>2 and o.code = ?1")
    ArticleType findOnByCode(String code);
}
