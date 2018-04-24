package com.h9.admin.service;

import com.h9.admin.model.dto.customModule.AddCustomModuleDTO;
import com.h9.admin.model.vo.CustomModuleDetailVO;
import com.h9.admin.model.vo.CustomModuleListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleGoods;
import com.h9.common.db.entity.custom.CustomModuleItems;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.repo.CustomModuleGoodsRep;
import com.h9.common.db.repo.CustomModuleItmesRep;
import com.h9.common.db.repo.CustomModuleRep;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ln on 2018/4/23.
 */
@Service
public class CustomModuleService {

    @Resource
    private CustomModuleRep customModuleRep;
    @Resource
    private CustomModuleGoodsRep customModuleGoodsRep;
    @Resource
    private CustomModuleItmesRep customModuleItmesRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * @param addCustomModuleDTO
     * @param type               1 为新增 2 为修改
     * @return
     */
    @Transactional
    public Result addAndEditModule(AddCustomModuleDTO addCustomModuleDTO, int type, Long userId) {
        CustomModule customModule = null;
        CustomModuleItems customModuleItmes = null;
        List<AddCustomModuleDTO.Info> infos = addCustomModuleDTO.getInfos();
        if (CollectionUtils.isEmpty(infos)) {
            return Result.fail("请传组件信息");
        } else {
            if (infos.size() != 2) {
                return Result.fail("请传组件信息");
            }
        }

        if (type == 1) {
            //新增
            customModule = new CustomModule(null, addCustomModuleDTO.getType(), 0, addCustomModuleDTO.getName());
            customModuleRep.saveAndFlush(customModule);
            customModuleItmesRep.deleteByCustomModule(customModule);
            for (AddCustomModuleDTO.Info info : infos) {

                customModuleItmes = new CustomModuleItems(info.getMainImages(),
                        info.getTextCount(), info.getCustomImagesCount(), customModule);
                customModuleItmesRep.saveAndFlush(customModuleItmes);
            }

        } else {
            //修改
            Long customModuleId = addCustomModuleDTO.getCustomModuleId();
            if (customModuleId == null) {
                return Result.fail("请提供要编辑的Id");
            }
            customModule = customModuleRep.findOne(customModuleId);
            if (customModule == null) {
                return Result.fail("模块不存在");
            }

            customModule.setModuleTypeId(addCustomModuleDTO.getType());
            customModule.setName(addCustomModuleDTO.getName());
            customModuleRep.saveAndFlush(customModule);


            Result reSetResult = reSetCustomItems(customModule, infos);
            if (reSetResult.isFail()) {
                return reSetResult;
            }
        }
        Map<Long, Long> goodsMap = addCustomModuleDTO.getGoodsInfo();
        Result setResult = setCustomModuleGoods(customModule, goodsMap);
        if (setResult.isFail()) {
            return setResult;
        }
        return Result.success();
    }

    /**
     * 重置 CustomItems
     *
     * @param customModule
     * @param infos
     * @return
     */
    public Result reSetCustomItems(CustomModule customModule, List<AddCustomModuleDTO.Info> infos) {
        List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(customModule);
        CustomModuleItems customModuleItmes = null;
        if (CollectionUtils.isEmpty(customModuleItems)) {

            for (AddCustomModuleDTO.Info info : infos) {

                customModuleItmes = new CustomModuleItems(info.getMainImages(),
                        info.getTextCount(), info.getCustomImagesCount(), customModule);
                customModuleItmesRep.save(customModuleItmes);
            }
        } else {

            for (int i = 0; i < customModuleItems.size(); i++) {
                CustomModuleItems ci = customModuleItems.get(i);
                AddCustomModuleDTO.Info info1 = infos.get(0);
                AddCustomModuleDTO.Info info2 = infos.get(1);
                if (i == 0) {
                    ci.setMainImages(info1.getMainImages());
                    ci.setTextCount(info1.getTextCount());
                    ci.setCustomImagesCount(info1.getCustomImagesCount());
                } else {
                    ci.setMainImages(info2.getMainImages());
                    ci.setTextCount(info2.getTextCount());
                    ci.setCustomImagesCount(info2.getCustomImagesCount());
                }
                customModuleItmesRep.save(ci);
            }

        }

        return Result.success();
    }


    /**
     * 设置定制模块的关联的商品信息
     *
     * @param customModule
     * @param goodsInfo
     * @return
     */
    public Result setCustomModuleGoods(CustomModule customModule, Map<Long, Long> goodsInfo) {
        Integer ints = customModuleGoodsRep.deleteByCustomModuleId(customModule.getId());
        logger.info("删除 " + ints + " 条记录");
        if (CollectionUtils.isNotEmpty(goodsInfo.keySet())) {
            if (CollectionUtils.isNotEmpty(goodsInfo.keySet())) {
                Set<Long> idsSet = goodsInfo.keySet();
                Iterator<Long> iterator = idsSet.iterator();
                while (iterator.hasNext()) {
                    Long id = iterator.next();
                    Goods goods = goodsReposiroty.findOne(id);
                    if (goods == null) {
                        return Result.fail("商品不存在");
                    }
                    Long numbers = goodsInfo.get(id);
                    CustomModuleGoods customModuleGoods = new CustomModuleGoods(null, goods.getId(), customModule.getId(), 0, numbers);
                    customModuleGoodsRep.saveAndFlush(customModuleGoods);
                }
            }
        }
        return Result.success();
    }

    public Result<PageResult<CustomModuleListVO>> modules(Integer pageNumber, Integer pageSize, Long userId) {
        PageRequest pageRequest = customModuleRep.pageRequest(pageNumber, pageSize, new Sort(Sort.Direction.DESC, "id"));

        Page<CustomModuleListVO> map = customModuleRep
                .findByDelFlag(0, pageRequest)
                .map(cm -> {

                    String createTime = DateUtil.formatDate(cm.getCreateTime(), DateUtil.FormatType.MINUTE);
                    String updateTime = DateUtil.formatDate(cm.getUpdateTime(), DateUtil.FormatType.MINUTE);
                    List<CustomModuleGoods> customModuleGoodsList = customModuleGoodsRep.findByCustomModuleId(0, cm.getId());
                    int goodsNumber = 0;
                    if (CollectionUtils.isNotEmpty(customModuleGoodsList)) {
                        goodsNumber = customModuleGoodsList.size();
                    }
                    CustomModuleListVO customModuleListVO = new CustomModuleListVO(cm.getId(), cm.getName()
                            , cm.getModuleTypeId() + "", goodsNumber, createTime, updateTime);
                    return customModuleListVO;

                });
        PageResult pageResult = new PageResult(map);
        return Result.success(pageResult);
    }

    public Result deleteModule(Long id) {
        CustomModule customModule = customModuleRep.findOne(id);
        if (customModule == null) {
            return Result.fail("定制模块不存在");
        }
        customModule.setDelFlag(1);
        customModuleRep.saveAndFlush(customModule);
        return Result.success();
    }

    public Result detail(Long id) {
        CustomModule customModule = customModuleRep.findOne(id);
        if (customModule == null) {
            return Result.fail("模块不存在");
        }
        List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(customModule);
        List<CustomModuleGoods> customModuleGoodsList = customModuleGoodsRep.findByCustomModuleId(0, customModule.getId());

        CustomModuleDetailVO vo = new CustomModuleDetailVO();
        vo.setCustomModuleId(customModule.getId());
        vo.setName(customModule.getName());
        vo.setType(customModule.getModuleTypeId());

        List<Map<String, String>> goodsList = customModuleGoodsList.stream().map(ci -> {
            Map<String,String> map = new HashMap();
            Long goodsId = ci.getGoodsId();
            Goods goods = goodsReposiroty.findOne(goodsId);
            map.put("id", goodsId+"");
            map.put("goodsName", goods.getName());
            map.put("numbers", ci.getNumbers()+"");
            return map;
        }).collect(Collectors.toList());

        vo.setGoodsList(goodsList);

        List<CustomModuleDetailVO.Info> infos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(customModuleItems)) {

            for (CustomModuleItems citem : customModuleItems) {
                List<String> mainImages = citem.getMainImages();
                Integer customImagesCount = citem.getCustomImagesCount();
                Integer textCount = citem.getTextCount();
                CustomModuleDetailVO.Info info = new CustomModuleDetailVO.Info(mainImages, customImagesCount, textCount);
                infos.add(info);
            }
        }
        vo.setInfos(infos);
        return Result.success(vo);
    }
}
