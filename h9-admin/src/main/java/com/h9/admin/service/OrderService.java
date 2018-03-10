package com.h9.admin.service;

import com.h9.admin.model.dto.WxOrderListInfo;
import com.h9.admin.model.dto.order.ExpressDTO;
import com.h9.admin.model.vo.OrderDetailVO;
import com.h9.admin.model.vo.OrderItemVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.PayInfo;
import com.h9.common.db.entity.RechargeOrder;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.account.RechargeRecord;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.GoodsType;
import com.h9.common.db.entity.order.OrderItems;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.transaction.OrderDTO;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.PayInfo.OrderTypeEnum.STORE_ORDER;
import static com.h9.common.db.entity.account.BalanceFlow.BalanceFlowTypeEnum.REFUND;

/**
 * Created by Gonyb on 2017/11/10.
 */
@Service
public class OrderService {

    Logger logger = Logger.getLogger(OrderService.class);

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

    @Resource
    private RechargeOrderRepository rechargeOrderRepository;

    public Result<PageResult<OrderItemVO>> orderList(OrderDTO orderDTO) {
        long startTime = System.currentTimeMillis();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        long sortEndTime = System.currentTimeMillis();
        logger.debugv("排序时间" + (sortEndTime - startTime));
        Specification<Orders> ordersSpecification = this.ordersRepository.buildSpecification(orderDTO);
        long 查询参数构建时间 = System.currentTimeMillis();
        logger.debugv("查询参数构建时间" + (查询参数构建时间 - sortEndTime));
        PageRequest pageRequest = orderDTO.toPageRequest(sort);
        Page<Orders> all = ordersRepository.findAll(ordersSpecification
                , pageRequest);
        long 查询真正使用时间 = System.currentTimeMillis();
        logger.debugv("查询时间" + (查询真正使用时间 - 查询参数构建时间));
        return Result.success(new PageResult<>(all.map(OrderItemVO::toOrderItemVO)));
    }

    public Result<OrderDetailVO> getOrder(long id) {

//        if (type == -1) {
        return getOrderDetail(id);
//        } else {
//            return getOrder4wx(id);
//        }

    }

    public Result<OrderDetailVO> getOrderDetail(long id) {
        Orders orders = this.ordersRepository.findOne(id);
        if (orders == null) {
            return Result.fail("订单不存在");
        }
        RechargeRecord rechargeRecord = this.rechargeRecordRepository.findByOrderId(id);
        Long payInfoId = orders.getPayInfoId();
        String wxOrderId = "";
        if (payInfoId != null) {
            PayInfo payInfo = payInfoRepository.findOne(payInfoId);
            if (payInfo == null) {
                logger.info("payInfo 为 null");
            } else {
                wxOrderId = getWxOrderId(payInfo.getId());
            }
        }

        return Result.success(OrderDetailVO.toOrderDetailVO(orders, rechargeRecord, wxOrderId));
    }

    /**
     * 处理微信支付的订单
     *
     * @param payInfoId
     * @return
     */
    public Result<OrderDetailVO> getOrder4wx(Long payInfoId) {
        PayInfo payInfo = payInfoRepository.findOne(payInfoId);
        if (payInfo == null) {
            return Result.fail("没有找到此订单");
        }
        Long orderId = payInfo.getOrderId();
        int orderType = payInfo.getOrderType();

        if (orderType == PayInfo.OrderTypeEnum.Recharge.getId()) {
            RechargeOrder rechargeOrder = rechargeOrderRepository.findOne(orderId);
            String wxOrderId = getWxOrderId(payInfoId);
            OrderDetailVO orderDetailVO = OrderDetailVO.toOrderDetailVO(rechargeOrder, wxOrderId);
            return Result.success(orderDetailVO);
        } else {
            return getOrderDetail(payInfo.getOrderId());
        }
    }

    public Result<OrderItemVO> editExpress(ExpressDTO expressDTO) {
        Orders one = ordersRepository.findOne(expressDTO.getId());
        if (one == null) {
            return Result.fail("订单号不存在");
        }
        //只有实物订单才能修改
        if (one.getOrderType().equals(Orders.orderTypeEnum.MATERIAL_GOODS.getCode() + "")) {
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
    public Result updateOrderStatus(Long id, Integer status) {
        Orders orders = this.ordersRepository.findOne(id);
        if (orders == null) {
            return Result.fail("订单不存在");
        }
        if (status == Orders.statusEnum.WAIT_SEND.getCode()) {
            if (orders.getStatus() != Orders.statusEnum.UNCONFIRMED.getCode()) {
                return Result.fail("不是未确认的订单不能发货");
            }
            orders.setStatus(status);
        } else if (status == Orders.statusEnum.CANCEL.getCode()) {
            if (orders.getStatus() != Orders.statusEnum.UNCONFIRMED.getCode()) {
                return Result.fail("不是未确认的订单不能取消");
            }
            orders.setStatus(status);
        } else {
            return Result.fail("不支持修改订单为当前状态");
        }
        this.ordersRepository.save(orders);
        if (status == Orders.statusEnum.CANCEL.getCode()) {
            this.commonService.setBalance(orders.getUser().getId(), orders.getPayMoney().abs(), BalanceFlow.BalanceFlowTypeEnum.REFUND.getId(),
                    orders.getId(), orders.getNo(), "订单取消退回");
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

    @Value("${pay.host}")
    private String payHost;
    @Value("${pay.businessAppId}")
    private String bid;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private PayInfoRepository payInfoRepository;


    public String getWxOrderId(Long payInfoId) {
        String url = payHost + "/h9/pay/order/info/batch?ids=" + payInfoId + "&bid=" + bid;
        Result result = restTemplate.getForObject(url, Result.class);
        if (result.getCode() != 0) {
            return null;
        }
        Map<String, String> map = (Map<String, String>) result.getData();
        String wxOrderId = map.get(payInfoId + "");
        return wxOrderId;

    }

    public Result<PageResult<WxOrderListInfo>> wxOrderList(Integer page, Integer limit, String wxOrderNo, Integer orderType, Long createTime, Long endTime) {
        if (StringUtils.isNotBlank(wxOrderNo)) {
            String url = payHost + "/h9/pay/order/info?no=" + wxOrderNo;
            Result result = restTemplate.getForObject(url, Result.class);
            if (result.getCode() == 1) {
                return result;
            }
            Map<String, Object> map = (Map<String, Object>) result.getData();
            String payInfoIdStr = map.get("payInfoId").toString();
            Long payInfId = Long.valueOf(payInfoIdStr);
            Object wxNo = map.get("wxId");
            PayInfo payInfo = payInfoRepository.findOne(payInfId);

            String date = DateUtil.formatDate(payInfo.getCreateTime(), DateUtil.FormatType.SECOND);
            int type = payInfo.getOrderType();

            WxOrderListInfo wxOrderListInfo = new WxOrderListInfo(wxNo.toString(), type == 0 ? "微信充值" : "购买商品"
                    , payInfo.getOrderId() + "", MoneyUtils.formatMoney(payInfo.getMoney()), date, date, "", payInfo.getId() + "");

            PageResult<WxOrderListInfo> pageResult = new PageResult();
            pageResult.setHasNext(false);
            pageResult.setCurrPage(1);
            pageResult.setTotal(1);
            pageResult.setTotalPage(1);
            pageResult.setCount(limit);
            pageResult.setData(Arrays.asList(wxOrderListInfo));
            return Result.success(pageResult);
        } else {
            //WxOrderListInfo
            Specification<PayInfo> specification = getWxOrderList(createTime, orderType, endTime);
            PageRequest pageRequest = payInfoRepository.pageRequest(page, limit, new Sort(Sort.Direction.DESC, "id"));
            Page<PayInfo> pageInfoList = payInfoRepository.findAll(specification, pageRequest);
            PageResult<PayInfo> pageResult = new PageResult(pageInfoList);
            List<PayInfo> payInfoList = pageInfoList.getContent();
            String ids = payInfoList.stream()
                    .map(payInfo -> payInfo.getId() + "")
                    .reduce("", (e1, e2) -> e1 + "-" + e2);

            if (ids.startsWith("-")) {
                ids = ids.substring(1, ids.length());
            }
            String url = payHost + "/h9/pay/order/info/batch?ids=" + ids + "&bid=" + bid;
            Result result = restTemplate.getForObject(url, Result.class);
            if (result.getCode() != 0) {
                return result;
            }
            Map<String, String> map = (Map<String, String>) result.getData();

            PageResult<WxOrderListInfo> pageVo = pageResult.map(payInfo -> new WxOrderListInfo(payInfo, map));

            return Result.success(pageVo);
        }

    }

    /**
     * description: 微信 支付订单
     *
     * @param orderType -1 全部 ，1 充值 ，2购买
     */
    private Specification<PayInfo> getWxOrderList(Long startTimeL, int orderType, Long endTimeL) {

        return (root, query, builder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            if (orderType == 2) {
                predicateList.add(builder.equal(root.get("orderType"), STORE_ORDER.getId()));
            } else if (orderType == 1) {
                predicateList.add(builder.equal(root.get("orderType"), PayInfo.OrderTypeEnum.Recharge.getId()));
            }
//            else{
//                Predicate predicate = builder.equal(root.get("orderType"), 2);
//                predicateList.add(builder.or(root.get("orderType"),predicate));
//            }

            predicateList.add(builder.equal(root.get("status"), 2));
            if (startTimeL != null && endTimeL != null) {
                Date startTime = new Date(startTimeL);
                Date endTime = new Date(endTimeL);

                predicateList.add(builder.between(root.get("createTime"), startTime, endTime));
            }

            return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

    @Resource
    private PayProvider payProvider;

    public Result refund(Long orderId) {
        Orders order = ordersRepository.findOne(orderId);
        if (order == null) {
            return Result.fail("订单不存在");
        }
        if (!order.getPayStatus().equals(Orders.PayStatusEnum.PAID.getCode())) {
            return Result.fail("退款失败，此订单未支付");
        }

        if (!canRefund(order)) {
            return Result.fail("此订单不能退款");
        }
        int payMethond = order.getPayMethond();

        if (Orders.PayMethodEnum.BALANCE_PAY.getCode() == payMethond) {
            BigDecimal payMoney = order.getPayMoney();
            commonService.setBalance(order.getUser().getId(), payMoney, REFUND.getId(), order.getId(), "", REFUND.getName());
            order = ordersRepository.findOne(orderId);
            order.setStatus(Orders.statusEnum.CANCEL.getCode());
            ordersRepository.save(order);
        } else if (Orders.PayMethodEnum.WX_PAY.getCode() == payMethond) {
            Long payInfoId = order.getPayInfoId();
            Result result = payProvider.refundOrder(payInfoId, order.getPayMoney());
            if (result.getCode() == 1) {
                return Result.fail(result.getMsg());
            } else {
                order = ordersRepository.findOne(orderId);
                order.setStatus(Orders.statusEnum.CANCEL.getCode());
                ordersRepository.save(order);
                return Result.success("退款成功");
            }
        } else {
            logger.info("退款异常，没有匹配到支付方式");
            return Result.fail("退款异常");
        }
        return Result.success("退款成功");

    }

    /**
     * 判断订单是否能退款
     * @param orders
     * @return
     */
    @SuppressWarnings("Duplicates")
    public boolean canRefund(Orders orders) {
        List<OrderItems> orderItems = orders.getOrderItems();
        boolean find = orderItems.stream().anyMatch(item -> {
            Goods goods = item.getGoods();
            GoodsType goodsType = goods.getGoodsType();
            if (goodsType.getCode().equals(GoodsType.GoodsTypeEnum.MOBILE_RECHARGE.getCode())) {
                return true;
            } else {
                return false;
            }
        });
        return false;
    }
}
