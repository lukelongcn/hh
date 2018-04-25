package com.h9.store.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleGoods;
import com.h9.common.db.entity.custom.CustomModuleItems;
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
        if(customModule == null){
            return Result.fail("模块不存在");
        }
        List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(customModule);

        CustomModuleDetailVO VO = new CustomModuleDetailVO();
        return null;
    }

    public Result modelPay(CustomModuleDTO customModuleDTO) {
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
