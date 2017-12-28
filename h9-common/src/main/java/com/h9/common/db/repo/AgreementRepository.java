package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.HtmlContent;

/**
 * Created by liyuan on 2017/11/1.
 */
public interface AgreementRepository extends BaseRepository<HtmlContent> {

    /**
     * 返回单页内容
     * @param code
     * @return
     */
    HtmlContent findByCode(String code);
}
