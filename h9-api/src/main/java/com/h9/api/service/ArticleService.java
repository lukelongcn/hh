package com.h9.api.service;

import com.h9.api.model.vo.ArticleVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Article;
import com.h9.common.db.repo.ArticleRepository;
import com.h9.common.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

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
        Article article =  articleReposiroty.findOne(id);
        ArticleVO articleVO = new ArticleVO(articleReposiroty.findOne(id));
        articleVO.setStartTime(DateUtil.formatDate(article.getStartTime(), DateUtil.FormatType.GBK_MINUTE));
        articleVO.setEndTime(DateUtil.formatDate(article.getEndTime(), DateUtil.FormatType.GBK_MINUTE));
        return Result.success(articleVO);
    }


    @ApiOperation(value = "获取文章json数据")
    @ResponseBody
    public Map<String,ArticleVO> json(Long id){
        Article article =  articleReposiroty.findOne(id);
        ArticleVO articleVO = new ArticleVO(articleReposiroty.findOne(id));
        articleVO.setStartTime(DateUtil.formatDate(article.getStartTime(), DateUtil.FormatType.MINUTE));
        articleVO.setEndTime(DateUtil.formatDate(article.getEndTime(), DateUtil.FormatType.MINUTE));

        Map<String, ArticleVO> map = new HashMap<String, ArticleVO>();
        map.put("key", articleVO);
        return map;
    }
}
