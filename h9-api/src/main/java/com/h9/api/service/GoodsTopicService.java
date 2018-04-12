package com.h9.api.service;

import com.h9.api.model.vo.topic.GoodsTopicDetailVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.GoodsTopicModule;
import com.h9.common.db.entity.order.GoodsTopicRelation;
import com.h9.common.db.entity.order.GoodsTopicType;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTopicModuleRep;
import com.h9.common.db.repo.GoodsTopicRelationRep;
import com.h9.common.db.repo.GoodsTopicTypeRep;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     *
     * @param id
     * @return
     */
    public Result topicDetail(Long id) {
        GoodsTopicType goodsTopicType = goodsTopicTypeRep.findOne(id);

        if (goodsTopicType == null) {
            return Result.fail("此id不存在");
        }
        GoodsTopicDetailVO vo = new GoodsTopicDetailVO();

        BeanUtils.copyProperties(goodsTopicType, vo);

        List<GoodsTopicModule> goodsTopicModuleList = goodsTopicModuleRep.
                findByDelFlagAndGoodsTopicTypeId(0, goodsTopicType.getId());

        vo.setModules(map2ModulesList(goodsTopicModuleList));

        return Result.success(vo);
    }

    private List<GoodsTopicDetailVO.Modules> map2ModulesList(List<GoodsTopicModule> goodsTopicModuleList) {
        List<GoodsTopicDetailVO.Modules> modulesList = goodsTopicModuleList.stream().map(module -> {
            List<GoodsTopicRelation> goodsTopicRelations = goodsTopicRelationRep.findByGoodsTopicModuleId(module.getId());

            List<Map<String, String>> mapList = goodsTopicRelations.stream().map(goodsTopicRelation -> {
                Map<String, String> goodsMap = new HashMap<>();
                Goods goods = goodsReposiroty.findOne(goodsTopicRelation.getGoodsId());
                if (goods != null) {
                    goodsMap.put("goodsId", goodsTopicRelation.getGoodsId() + "");
                    goodsMap.put("img", goods.getImg() + "");
                    goodsMap.put("name", goods.getName());
                    goodsMap.put("link", "goods:" + goodsTopicRelation.getGoodsId());
                    return goodsMap;
                }
                return null;
            }).filter(el -> el != null).collect(Collectors.toList());

            GoodsTopicDetailVO.Modules modules = new GoodsTopicDetailVO.Modules(module.getImg(),module.getId()+"", mapList);

            return modules;
        }).collect(Collectors.toList());

        return modulesList;
    }
}
