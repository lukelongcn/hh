package com.h9.admin.service;

import com.h9.admin.model.dto.order.ExpressDTO;
import com.h9.admin.model.vo.OrderItemVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.beans.BeanUtils;
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

    public Result<OrderItemVO> editExpress(ExpressDTO expressDTO) {
        Orders one = ordersRepository.findOne(expressDTO.getId());
        if(one==null){
            return Result.fail("订单号不存在");
        }
        //只有实物订单才能修改
        BeanUtils.copyProperties(expressDTO,one);
        ordersRepository.save(one);
        return Result.success(OrderItemVO.toOrderItemVO(one));
    }
}
