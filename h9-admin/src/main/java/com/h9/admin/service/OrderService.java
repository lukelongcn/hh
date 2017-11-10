package com.h9.admin.service;

import com.h9.admin.model.vo.OrderItemVO;
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
    private OrdersRepository ordersRepository;
    
    public Result<PageResult<OrderItemVO>> orderList(PageDTO pageDTO) {
        Page<Orders> all = ordersRepository.findAll(pageDTO.toPageRequest());
        return Result.success(new PageResult<>(all.map(OrderItemVO::toOrderItemVO)));
    }
}
