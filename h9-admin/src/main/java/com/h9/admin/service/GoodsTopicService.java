package com.h9.admin.service;

import com.h9.admin.model.dto.topic.EditGoodsTopicModuleDTO;
import com.h9.admin.model.dto.topic.EditGoodsTopicTypeDTO;
import com.h9.admin.model.vo.GoodsTopicModuleVO;
import com.h9.admin.model.vo.GoodsTopicVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.GoodsTopicModule;
import com.h9.common.db.entity.order.GoodsTopicRelation;
import com.h9.common.db.entity.order.GoodsTopicType;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTopicRelationRep;
import com.h9.common.db.repo.GoodsTopicModuleRep;
import com.h9.common.db.repo.GoodsTopicTypeRep;
import com.h9.common.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    private GoodsTopicModuleRep goodsTopicModuleRep;
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

        Long id = editGoodsTopicTypeDTO.getId();

        GoodsTopicType goodsTopicType = goodsTopicTypeRep.findOne(id);
        if(goodsTopicType == null){
            return Result.fail("专题类型不存在");
        }
        BeanUtils.copyProperties(editGoodsTopicTypeDTO, goodsTopicType);
        goodsTopicTypeRep.save(goodsTopicType);
        return Result.success();

    }

    public Result topicDetail(Long topicId) {

        GoodsTopicModule goodsTopicModule = goodsTopicModuleRep.findOne(topicId);
        if (goodsTopicModule == null) {
            return Result.fail("此Id不存在数据");
        }
        List<GoodsTopicRelation> goodsTopicRelations = goodsTopicRelationRep.findByGoodsTopicModuleId(goodsTopicModule.getId());
        GoodsTopicVO goodsTopicVO = new GoodsTopicVO(goodsTopicModule, goodsTopicRelations);
        return Result.success(goodsTopicVO);
    }

    @Transactional
    public Result editGoodsTopicModule(EditGoodsTopicModuleDTO editGoodsTopicModuleDTO) {
        Map<Long, Integer> ids = editGoodsTopicModuleDTO.getIds();
        Long topicModuleId = editGoodsTopicModuleDTO.getTopicModuleId();

        GoodsTopicModule goodsTopicModule = goodsTopicModuleRep.findOne(topicModuleId);
        if(goodsTopicModule == null) return Result.fail("模块不存在");

        //把此topic 的商品全部禁用
        Integer ints = goodsTopicRelationRep.updateStatus(topicModuleId);
        logger.info("更新 " + ints + " 条记录");
        Long topicTypeId = editGoodsTopicModuleDTO.getTopicTypeId();
        GoodsTopicType goodsTopicType = goodsTopicTypeRep.findOne(topicTypeId);
        if (goodsTopicType == null) return Result.fail("专题不存在");

        ids.forEach((goodsId, sort) -> {
            Goods goods = goodsReposiroty.findOne(goodsId);
            if (goods != null) {
                //新添加的数据
                GoodsTopicRelation goodsTopicRelation = new GoodsTopicRelation(null, goodsId, goods.getName(),
                        topicModuleId, sort, 0, goodsTopicType.getId());
                goodsTopicRelationRep.save(goodsTopicRelation);
            }

        });
        goodsTopicModule.setImg(editGoodsTopicModuleDTO.getImg());
        goodsTopicModule.setSort(editGoodsTopicModuleDTO.getSort());
        goodsTopicModuleRep.save(goodsTopicModule);
        return Result.success();
    }

    /**
     * 专题模块列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Result goodsTopicModule(Integer pageNumber, Integer pageSize, Long topicId) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");

        PageRequest pageRequest = goodsTopicModuleRep.pageRequest(pageNumber, pageSize, sort);
        Page<GoodsTopicModule> goodsTopicModulePage = goodsTopicModuleRep.
                findByDelFlagAndGoodsTopicTypeId(0, topicId, pageRequest);
        PageResult<GoodsTopicModule> pageResult = new PageResult<>(goodsTopicModulePage);

        PageResult<GoodsTopicModuleVO> map = pageResult.map(goodsTopicModule -> {
            Long id = goodsTopicModule.getId();
            List<GoodsTopicRelation> byGoodsTopicIdList = goodsTopicRelationRep.findByGoodsTopicModuleId(id);
            int goodsCount = 0;
            if (CollectionUtils.isNotEmpty(byGoodsTopicIdList)) {
                goodsCount = byGoodsTopicIdList.size();
            }
            GoodsTopicModuleVO vo = new GoodsTopicModuleVO(goodsTopicModule.getSort(),
                    goodsTopicModule.getImg(), goodsCount + "",
                    DateUtil.formatDate(goodsTopicModule.getCreateTime(), DateUtil.FormatType.MINUTE),
                    goodsTopicModule.getId());
            return vo;
        });

        return Result.success(map);
    }

    /**
     * 增加 专题模块
     *
     * @param editGoodsTopicModuleDTO 参数
     * @return result
     */
    public Result addGoodsTopicModule(EditGoodsTopicModuleDTO editGoodsTopicModuleDTO) {
        GoodsTopicModule module = new GoodsTopicModule(null,
                editGoodsTopicModuleDTO.getSort(), editGoodsTopicModuleDTO.getImg(), 0,
                editGoodsTopicModuleDTO.getTopicTypeId());
        Map<Long, Integer> ids = editGoodsTopicModuleDTO.getIds();
        Long topicTypeId = editGoodsTopicModuleDTO.getTopicTypeId();
        GoodsTopicType goodsTopicType = goodsTopicTypeRep.findOne(topicTypeId);
        if (goodsTopicType == null) {
            return Result.fail("专题不存在");
        }
        ids.forEach((goodsId, sort) -> {
            Goods goods = goodsReposiroty.findOne(goodsId);

            if (goods != null) {
                GoodsTopicRelation relation = new GoodsTopicRelation(null, goodsId, goods.getName(),
                        editGoodsTopicModuleDTO.getTopicModuleId(), sort, 0, goodsTopicType.getId());
                goodsTopicRelationRep.save(relation);
            }

        });
        goodsTopicModuleRep.save(module);
        return Result.success();
    }

    /**
     * 删除专题模块
     *
     * @param id
     * @return
     */
    public Result<GoodsTopicModuleVO> delGoodsTopicModule(Long id) {
        GoodsTopicModule goodsTopicModule = goodsTopicModuleRep.findOne(id);
        if (goodsTopicModule == null) {
            return Result.fail("模块不存在");
        }
        goodsTopicModule.setDelFlag(1);
        goodsTopicModuleRep.save(goodsTopicModule);
        return Result.success();
    }

}
