package com.h9.api.service;

import com.h9.api.model.vo.ArticleVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Article;
import com.h9.common.db.repo.ArticleRepository;
import com.h9.common.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.resources.cldr.mg.LocaleNames_mg;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
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
        articleVO.setStartTime(DateUtil.formatDate(article.getStartTime(), DateUtil.FormatType.MINUTE));
        articleVO.setEndTime(DateUtil.formatDate(article.getEndTime(), DateUtil.FormatType.MINUTE));
        return Result.success(articleVO);
    }

    @ApiOperation(value = "获取文章json数据")
    public @ResponseBody Result json(Long id){
        Article article =  articleReposiroty.findOne(id);
        ArticleVO articleVO = new ArticleVO(articleReposiroty.findOne(id));
        articleVO.setStartTime(DateUtil.formatDate(article.getStartTime(), DateUtil.FormatType.MINUTE));
        articleVO.setEndTime(DateUtil.formatDate(article.getEndTime(), DateUtil.FormatType.MINUTE));

        Map<Long, ArticleVO> map = new HashMap<Long, ArticleVO>();
        map.put(article.getId(), articleVO);
        return Result.success(map);
    }


}
