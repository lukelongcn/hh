package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.HtmlContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 * Created by Gonyb on 2017/11/9.
 */
public interface HtmlContentRepository extends BaseRepository<HtmlContent> {
    @Query("select a from HtmlContent a where a.status<>2 order by a.status desc , id desc")
    Page<HtmlContent> findAll(Pageable pageable);

    @Query("select a from HtmlContent a where a.status<>2 and a.id=?1")
    HtmlContent findOne(Long id);
}
