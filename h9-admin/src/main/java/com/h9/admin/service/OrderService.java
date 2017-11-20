package com.h9.admin.service;

import com.h9.admin.model.dto.order.ExpressDTO;
import com.h9.admin.model.vo.OrderItemVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * Created by Gonyb on 2017/11/10.
 */
@Service
public class OrderService {
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private ConfigService configService;
    
    public Result<PageResult<OrderItemVO>> orderList(PageDTO pageDTO) {
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Page<Orders> all = ordersRepository.findAll(pageDTO.toPageRequest(sort));
        return Result.success(new PageResult<>(all.map(OrderItemVO::toOrderItemVO)));
    }

    public Result<OrderItemVO> editExpress(ExpressDTO expressDTO) {
        Orders one = ordersRepository.findOne(expressDTO.getId());
        if(one==null){
            return Result.fail("订单号不存在");
        }
        //只有实物订单才能修改
        if (one.getOrderType() == GoodsType.GoodsTypeEnum.MATERIAL.getCode()) {
            BeanUtils.copyProperties(expressDTO, one);
            ordersRepository.save(one);
            return Result.success(OrderItemVO.toOrderItemVO(one));
        } else {
            GoodsType.GoodsTypeEnum byCode = GoodsType.GoodsTypeEnum.findByCode(one.getOrderType());
            return Result.fail("此订单为"+(byCode==null?"未知":byCode.getDesc())+",无法添加物流信息");
        } 
    }

    public Result<List<String>> getSupportExpress() {
        return Result.success(configService.getStringListConfig("supportExpress"));
    }
}
