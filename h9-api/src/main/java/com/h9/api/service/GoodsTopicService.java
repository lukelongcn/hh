package com.h9.api.service;

import com.h9.common.base.Result;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTopicModuleRep;
import com.h9.common.db.repo.GoodsTopicRelationRep;
import com.h9.common.db.repo.GoodsTopicTypeRep;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Ln on 2018/4/9.
 */
@Service
public class GoodsTopicService {


    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private GoodsTopicTypeRep goodsTopicTypeRep;
    @Resource
    private GoodsTopicRelationRep goodsTopicRelationRep;
    @Resource
    private GoodsTopicModuleRep goodsTopicModuleRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;

    /**
     * 专题详情
     * @param id
     * @return
     */
    public Result topicDetail(Long id) {
        return null;
    }
}
