package com.transfer.service;

import com.h9.common.base.PageResult;
import com.transfer.db.entity.GoodsOrder;
import com.transfer.db.repo.GoodsOrderRepository;
import com.transfer.service.base.BaseService;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by itservice on 2017/12/6.
 */
public class OrderService extends BaseService<GoodsOrder> {

    private GoodsOrderRepository goodsOrderRepository;

    @Override
    public String getPath() {
        return "./goodsOrder.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
        return goodsOrderRepository.findAll(page, limit);
    }

    @Override
    public void getSql(GoodsOrder goodsOrder, BufferedWriter writer) throws IOException {
        StringBuilder sql = new StringBuilder("");

    }

    @Override
    public String getTitle() {
        return "--------------------->>>>>>>>>>>>>>>>>>>>>>>>";
    }
}
