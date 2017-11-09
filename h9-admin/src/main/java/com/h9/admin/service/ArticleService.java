package com.h9.admin.service;

import com.h9.admin.model.dto.article.ArticleTypeDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Article;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.repo.ArticleRepository;
import com.h9.common.db.repo.ArticleTypeRepository;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.beans.BeanUtils;
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

    public Result<PageResult<ArticleType>> categoryList(PageDTO pageDTO) {
        Page<ArticleType> all = articleTypeRepository.findAll(pageDTO.toPageRequest());
        all.forEach(articleType -> articleType.setArticleCount(articleRepository.findCountByArticleType(articleType.getId())));
        return Result.success(new PageResult<>(all));
    }


    public Result<ArticleType> getCategory(Long id) {
        ArticleType one = articleTypeRepository.findOne(id);
        one.setArticleCount(articleRepository.findCountByArticleType(one.getId()));
        return Result.success(one);
    }

    public Result<ArticleType> addCategory(ArticleTypeDTO articleTypeDTO) {
        ArticleType articleType = new ArticleType();
        BeanUtils.copyProperties(articleTypeDTO,articleType);
        articleType.setId(null);
        articleTypeRepository.save(articleType);
        return Result.success(articleType);
    }

    public Result<ArticleType> editCategory(ArticleTypeDTO articleTypeDTO) {
        Long id = articleTypeDTO.getId();
        ArticleType one = articleTypeRepository.findOne(id);
        if(one==null){
            return Result.fail("分类不存在");
        }
        BeanUtils.copyProperties(articleTypeDTO,one);
        articleTypeRepository.save(one);
        return Result.success(one);
    }

    public Result deleteCategory(Long id) {
        ArticleType one = articleTypeRepository.findOne(id);
        if(one==null){
            return Result.fail("分类不存在");
        }
        one.setEnable(2);
        articleTypeRepository.save(one);
        return Result.success();
    }

    public Result<PageResult<Article>> articleList(PageDTO pageDTO) {
        Page<Article> all = articleRepository.findAll(pageDTO.toPageRequest());
        return Result.success(new PageResult<>(all));
    }
}
