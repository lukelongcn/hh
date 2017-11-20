package com.h9.api.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.repo.GoodsReposiroty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/20.
 */
@Service
public class GoodService {

    @Resource
    private GoodsReposiroty goodsReposiroty;

    /**
     * description: 减少商品库 -1
     */
    public Result changeStock(Long goodsId){

        Goods goods = goodsReposiroty.findOne(goodsId);
        if(goods != null) return Result.fail("商品不存在");

        int stock = goods.getStock();
        if(stock <=0){
            return Result.fail("库存不足");
        }

        goods.setStock(--stock);
        if(stock <= 0){
            goods.setStatus(2);
        }
        goodsReposiroty.save(goods);
        return Result.success();
    }

    public Result changeStock(Goods goods){
        return changeStock(goods.getId());
    }
}
