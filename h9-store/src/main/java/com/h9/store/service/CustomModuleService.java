package com.h9.store.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleGoods;
import com.h9.common.db.entity.custom.CustomModuleItems;
import com.h9.common.db.entity.custom.UserCustomItems;
import com.h9.common.db.repo.*;
import com.h9.store.modle.dto.AddUserCustomDTO;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.*;
import com.h9.common.utils.MoneyUtils;
import com.h9.store.modle.dto.CustomModuleDTO;
import com.h9.store.modle.vo.CustomModuleDetailVO;
import com.h9.store.modle.vo.GoodsDetailVO;
import com.h9.store.modle.vo.ModelGoodsVO;
import com.h9.store.modle.vo.PersonaLModelOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ln on 2018/4/24.
 */
@Service
public class CustomModuleService {
    @Resource
    private ConfigService configService;
    @Resource
    private CustomModuleRep customModuleRep;
    @Resource
    private CustomModuleGoodsRep customModuleGoodsRep;
    @Resource
    private CustomModuleItmesRep customModuleItmesRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private UserCustomItemsRep userCustomItemsRep;
    @Resource
    private UserAccountRepository userAccountRepository;

    public Result types() {
        List<String> list = configService.getStringListConfig("customType");
        return Result.success(list);
    }

    public Result modules(Long type, Integer page, Integer limit) {

        PageRequest pageRequest = customModuleRep.pageRequest(page, limit, new Sort(Sort.Direction.DESC, "id"));
        Page<Map> mapVO = customModuleRep
                .findByModuleTypeId(type, 0, pageRequest).map(cm -> {
                    Map map = new HashMap<>();
                    map.put("id", cm.getId());
                    map.put("name", cm.getName());
                    List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(cm);
                    String imgUrl = "";
                    if (CollectionUtils.isNotEmpty(customModuleItems)) {
                        CustomModuleItems citem = customModuleItems.get(0);
                        List<String> mainImages = citem.getMainImages();
                        if (CollectionUtils.isNotEmpty(mainImages)) {
                            imgUrl = mainImages.get(0);
                        }
                    }
                    map.put("imgUrl", imgUrl);
                    return map;
                });

        return Result.success(new PageResult<>(mapVO));
    }

    public Result modulesDetail(Long id) {
        CustomModule customModule = customModuleRep.findOne(id);
        if (customModule == null) {
            return Result.fail("模块不存在");
        }
        List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(customModule);
        if (CollectionUtils.isNotEmpty(customModuleItems)) {

            List<CustomModuleDetailVO> customModuleDetailVOList = customModuleItems.stream().map(ci -> {
                Integer textCount = ci.getTextCount();
                Integer customImagesCount = ci.getCustomImagesCount();
                List<String> mainImages = ci.getMainImages();
                CustomModuleDetailVO vo = new CustomModuleDetailVO(mainImages, customImagesCount, textCount, ci.getId(), ci.getType());
                return vo;
            }).collect(Collectors.toList());
//            Map map = new HashMap();
//            map.put("infos", customModuleDetailVOList);
//            map.put("id", customModule.getId());
            return Result.success(customModuleDetailVOList);
        }
        return Result.fail();
    }

    public Result addUserCustom(List<AddUserCustomDTO> addUserCustomDTOs, Long userId) {

        if (CollectionUtils.isNotEmpty(addUserCustomDTOs)) {

            List<Long> collect = addUserCustomDTOs.stream().map(addUserCustomDTO -> {
                List<String> images = addUserCustomDTO.getImages();
                List<String> texts = addUserCustomDTO.getTexts();
                Long id = addUserCustomDTO.getId();
                CustomModuleItems customModuleItems = customModuleItmesRep.findOne(id);
                if (customModuleItems != null) {
                    UserCustomItems userCustomItems = new UserCustomItems();
                    userCustomItems.setCustomModuleItemsId(customModuleItems.getId());
                    userCustomItems.setCustomImages(images);
                    userCustomItems.setTexts(texts);
                    userCustomItems.setUserId(userId);
                    userCustomItems.setType(addUserCustomDTO.getType());
                    userCustomItemsRep.saveAndFlush(userCustomItems);
                    return userCustomItems.getId();
                }
                return null;
            }).filter(el -> el != null).collect(Collectors.toList());
            return Result.success(collect);
        }
        return Result.fail();
    }

    public Result modelPay(CustomModuleDTO customModuleDTO, Long userId) {
        return null;
    }

    public Result modelGoods(long userId, Long id) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        List<CustomModuleGoods> customModuleGoods = customModuleGoodsRep.findByCustomModuleId(0,id);
        if (CollectionUtils.isEmpty(customModuleGoods)){
            return Result.fail("暂无可选订制商品");
        }
        List<ModelGoodsVO> modelGoodsVOS = new ArrayList<>();
        customModuleGoods.forEach(c->{
            Long goodsId = c.getGoodsId();
            Goods goods = goodsReposiroty.findOne(goodsId);
            if (goods == null){
                return;
            }
            ModelGoodsVO vo = ModelGoodsVO.builder()
                    .id(goods.getId())
                    .img(goods.getImg())
                    .desc(goods.getDescription())
                    .price(MoneyUtils.formatMoney(goods.getRealPrice()))
                    .name(goods.getName())
                    .unit(goods.getUnit())
                    .build();
            vo.setOrderlimitNumberl(c.getNumbers());
            modelGoodsVOS.add(vo);
        });
        PersonaLModelOrderVO personaLModelOrderVO = new PersonaLModelOrderVO();
        personaLModelOrderVO.setBalance(MoneyUtils.formatMoney(userAccount.getBalance()));
        personaLModelOrderVO.setModelGoodsVOS(modelGoodsVOS);
        return Result.success(personaLModelOrderVO);
    }
}
