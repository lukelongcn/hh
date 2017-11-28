package com.h9.api.service;

import com.h9.api.model.vo.GoodsListVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/11/20.
 */
@Service
public class GoodService {

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;

    /**
     * description: 减少商品库 -1
     */
    public Result changeStock(Long goodsId){

        Goods goods = goodsReposiroty.findOne(goodsId);
        if(goods == null) return Result.fail("商品不存在");

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

    public Result goodsList(Integer type,int page,int size) {
        GoodsType goodsType = goodsTypeReposiroty.findOne(Long.valueOf(type));

        if(goodsType == null) return Result.fail("此类别不存在");

        Page<Goods> pageObj = goodsReposiroty.findByGoodsType(goodsType,new PageRequest(page, size));
        PageResult<Goods> pageResult = new PageResult(pageObj);
        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }


    public Result goodsDetail(Long id) {

        return null;
    }
}
