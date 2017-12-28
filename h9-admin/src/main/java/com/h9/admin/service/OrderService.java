package com.h9.admin.service;

import com.h9.admin.model.dto.order.ExpressDTO;
import com.h9.admin.model.vo.OrderDetailVO;
import com.h9.admin.model.vo.OrderItemVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.account.RechargeRecord;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.OrderItems;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.OrdersRepository;
import com.h9.common.db.repo.RechargeRecordRepository;
import com.h9.common.modle.dto.transaction.OrderDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    @Resource
    private RechargeRecordRepository rechargeRecordRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    
    public Result<PageResult<OrderItemVO>> orderList(OrderDTO orderDTO) {
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Page<Orders> all = ordersRepository.findAll(this.ordersRepository.buildSpecification(orderDTO)
                ,orderDTO.toPageRequest(sort));
        return Result.success(new PageResult<>(all.map(OrderItemVO::toOrderItemVO)));
    }

    public Result<OrderDetailVO> getOrder(long id) {
        Orders orders = this.ordersRepository.findOne(id);
        if (orders == null) {
            return Result.fail("订单不存在");
        }
        RechargeRecord rechargeRecord = this.rechargeRecordRepository.findByOrderId(id);
        return Result.success(OrderDetailVO.toOrderDetailVO(orders,rechargeRecord));
    }

    public Result<OrderItemVO> editExpress(ExpressDTO expressDTO) {
        Orders one = ordersRepository.findOne(expressDTO.getId());
        if(one==null){
            return Result.fail("订单号不存在");
        }
        //只有实物订单才能修改
        if (one.getOrderType().equals(Orders.orderTypeEnum.MATERIAL_GOODS.getCode()+"")) {
            BeanUtils.copyProperties(expressDTO, one);
            if (one.getStatus() == Orders.statusEnum.WAIT_SEND.getCode()) {
                if (StringUtils.isNotBlank(expressDTO.getExpressNum())) {
                    one.setStatus(Orders.statusEnum.DELIVER.getCode());
                }
            }
            ordersRepository.save(one);
            return Result.success(OrderItemVO.toOrderItemVO(one));
        } else {
            //GoodsType.GoodsTypeEnum byCode = GoodsType.GoodsTypeEnum.findByCode(one.getOrderType());
            return Result.fail("此订单不为实体商品,无法添加物流信息");
        } 
    }

    public Result<List<String>> getSupportExpress() {
        return Result.success(configService.getStringListConfig(ParamConstant.SUPPORT_EXPRESS));
    }

    @Transactional
    public Result updateOrderStatus(Long id,Integer status) {
        Orders orders = this.ordersRepository.findOne(id);
        if (orders == null) {
            return Result.fail("订单不存在");
        }
        if (status == Orders.statusEnum.WAIT_SEND.getCode()) {
            if (orders.getStatus() != Orders.statusEnum.UNCONFIRMED.getCode()) {
                return Result.fail("不是未确认的订单不能发货");
            }
            orders.setStatus(status);
        }else if (status == Orders.statusEnum.CANCEL.getCode()) {
            if (orders.getStatus() != Orders.statusEnum.UNCONFIRMED.getCode()) {
                return Result.fail("不是未确认的订单不能取消");
            }
            orders.setStatus(status);
        }else {
            return Result.fail("不支持修改订单为当前状态");
        }
        this.ordersRepository.save(orders);
        if (status == Orders.statusEnum.CANCEL.getCode()) {
           this.commonService.setBalance(orders.getUser().getId(),orders.getPayMoney().abs(), BalanceFlow.BalanceFlowTypeEnum.REFUND.getId(),
                   orders.getId(),orders.getNo(),"订单取消退回");
           List<Goods> goodsList = new ArrayList<>();
           for (OrderItems orderItems : orders.getOrderItems()) {
               Goods goods = this.goodsReposiroty.findByLockId(orderItems.getGoods().getId());
               goods.setStock(goods.getStock() + orderItems.getCount());
               goodsList.add(goods);
           }
            this.goodsReposiroty.save(goodsList);
        }
        return Result.success();
    }
}
