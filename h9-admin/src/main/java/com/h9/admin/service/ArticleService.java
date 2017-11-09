package com.h9.admin.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.repo.ArticleRepository;
import com.h9.common.db.repo.ArticleTypeRepository;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章服务
 * Created by Gonyb on 2017/11/9.
 */
@Service
public class ArticleService {
    @Resource
    private ArticleTypeRepository articleTypeRepository;
    @Resource
    private ArticleRepository articleRepository;

    public Result categoryList(PageDTO pageDTO) {
        Page<ArticleType> all = articleTypeRepository.findAll(pageDTO.toPageRequest());
        all.forEach(articleType -> articleType.setArticleCount(articleRepository.findCountByArticleType(articleType.getId())));
        return Result.success(all);
    }
}
