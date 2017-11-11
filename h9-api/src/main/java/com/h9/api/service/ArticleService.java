package com.h9.api.service;

import com.h9.api.model.vo.ArticleVO;
import com.h9.common.base.Result;
import com.h9.common.db.repo.ArticleRepository;
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
    ArticleRepository articleReposiroty;

    @ApiOperation(value = "获取文章内容")
    public Result findOne(Long id){
        return Result.success(new ArticleVO(articleReposiroty.findOne(id)));
    }
}
