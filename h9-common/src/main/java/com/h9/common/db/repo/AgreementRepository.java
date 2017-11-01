package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.HtmlContent;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by 李圆
 * on 2017/11/1
 */
public interface AgreementRepository extends BaseRepository<HtmlContent> {
    /**
     * 返回用户协议内容
     */
    @Query("select code from HtmlContent where name=?1 ")
    String agreement(String name);
}
