package com.h9.admin.service;

import com.h9.admin.model.dto.topic.EditGoodsTopicDTO;
import com.h9.admin.model.dto.topic.EditGoodsTopicTypeDTO;
import com.h9.admin.model.vo.GoodsTopicVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.GoodsTopic;
import com.h9.common.db.entity.order.GoodsTopicRelation;
import com.h9.common.db.entity.order.GoodsTopicType;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTopicRelationRep;
import com.h9.common.db.repo.GoodsTopicRep;
import com.h9.common.db.repo.GoodsTopicTypeRep;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.CollatorUtilities;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Ln on 2018/4/8.
 */
@Service
public class GoodsTopicService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private GoodsTopicTypeRep goodsTopicTypeRep;
    @Resource
    private GoodsTopicRelationRep goodsTopicRelationRep;
    @Resource
    private GoodsTopicRep goodsTopicRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;

    /**
     * 专题列表
     *
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public Result goodsTopicTypeList(Integer pageSize, Integer pageNumber) {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = goodsTopicTypeRep.pageRequest(pageNumber, pageSize, sort);
        Page<GoodsTopicType> page = goodsTopicTypeRep.findAll(pageRequest);
        PageResult<GoodsTopicType> result = new PageResult<>(page);

        return Result.success(result);
    }

    public Result editGoodsTopicType(EditGoodsTopicTypeDTO editGoodsTopicTypeDTO) {

        GoodsTopicType goodsTopicType = new GoodsTopicType();
        BeanUtils.copyProperties(editGoodsTopicTypeDTO, goodsTopicType);
        goodsTopicTypeRep.save(goodsTopicType);
        return Result.success();

    }

    public Result topicDetail(Long topicId) {

        GoodsTopic goodsTopic = goodsTopicRep.findOne(topicId);
        if (goodsTopic == null) {
            return Result.fail("此Id不存在数据");
        }
        List<GoodsTopicRelation> goodsTopicRelations = goodsTopicRelationRep.findByGoodsTopicId(goodsTopic.getId());
        GoodsTopicVO goodsTopicVO = new GoodsTopicVO(goodsTopic, goodsTopicRelations);
        return Result.success(goodsTopicVO);
    }

    @Transactional
    public Result editGoodsTopic(EditGoodsTopicDTO editGoodsTopicDTO) {
        Map<Long, Integer> ids = editGoodsTopicDTO.getIds();
        Long topicId = editGoodsTopicDTO.getTopicId();

        //把此topic 的商品全部禁用
        Integer ints = goodsTopicRelationRep.updateStatus(topicId);

        logger.info("更新 " + ints + " 条记录");

        ids.forEach((goodsId, sort) -> {

            List<GoodsTopicRelation> goodsTopicRelations = goodsTopicRelationRep.findByGoodsIdAndTopicId(goodsId, 1, topicId);

            if (CollectionUtils.isNotEmpty(goodsTopicRelations)) {

                GoodsTopicRelation goodsTopicRelation = goodsTopicRelations.get(0);
                goodsTopicRelation.setDel_flag(0);
                goodsTopicRelationRep.save(goodsTopicRelation);

            } else {
                Goods goods = goodsReposiroty.findOne(goodsId);
                if (goods != null) {
                    //新添加的数据
                    GoodsTopicRelation goodsTopicRelation = new GoodsTopicRelation(null
                            , goodsId, goods.getName(), topicId, sort, 0);
                    goodsTopicRelationRep.save(goodsTopicRelation);
                }

            }

        });

        return Result.success();
    }
}
