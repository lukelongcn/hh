package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.HtmlContent;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

/**
 * Created by liyuan on 2017/11/1.
 */
public interface AgreementRepository extends BaseRepository<HtmlContent> {
    /**
     * 返回页面内容
     * @param code
     * @return
     */
    @Query("select content from HtmlContent where code=?1 ")
    String agreement(String code);


    /**
     * 返回页面标题
     * @param code
     * @return
     */
    @Query("select title from HtmlContent where code=?1 ")
    String getTitle(String code);
}
