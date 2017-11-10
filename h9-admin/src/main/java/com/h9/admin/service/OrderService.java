package com.h9.admin.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
@Service
public class OrderService {
    @Resource
    private OrdersRepository ordersReposiroty;
    
    public Result<PageResult<Orders>> orderList(PageDTO pageDTO) {
        Page<Orders> all = ordersReposiroty.findAll(pageDTO.toPageRequest());

        return null;
    }
}