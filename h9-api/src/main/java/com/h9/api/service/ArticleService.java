package com.h9.api.service;

import com.h9.common.db.entity.Article;
import com.h9.common.db.repo.ArticleReposiroty;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by 李圆
 * on 2017/11/5
 */
@Service
@Transactional
public class ArticleService {
    @Resource
    ArticleReposiroty articleReposiroty;

    @ApiOperation(value = "获取文章内容")
    public Article findOne(Long id){
        return articleReposiroty.findOne(id);
    }
}
