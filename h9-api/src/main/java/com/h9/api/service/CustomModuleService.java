package com.h9.api.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleItems;
import com.h9.common.db.repo.CustomModuleGoodsRep;
import com.h9.common.db.repo.CustomModuleItmesRep;
import com.h9.common.db.repo.CustomModuleRep;
import com.h9.common.db.repo.GoodsReposiroty;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
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
}
