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
import com.h9.common.db.entity.bigrich.OrdersLotteryActivity;
import com.h9.common.db.entity.bigrich.OrdersLotteryRelation;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.GoodsType;
import com.h9.common.db.entity.order.OrderItems;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.repo.*;
import com.h9.common.modle.dto.transaction.OrderDTO;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MoneyUtils;
import com.h9.common.utils.POIUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

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

    @Resource
    private FileService fileService;
    @Resource
    private UserCouponsRepository userCouponsRepository;
    @Resource
    private OrdersLotteryRelationRep ordersLotteryRelationRep;

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

    public Result<PageResult<WxOrderListInfo>> wxOrderList(Integer page, Integer limit, String wxOrderNo,
                                                           Integer orderType, Long createTime, Long endTime) {
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
            pageResult.setCount(1);
            pageResult.setData(Arrays.asList(wxOrderListInfo));
            return Result.success(pageResult);
        } else {
            //WxOrderListInfo
            Specification<PayInfo> specification = getWxOrderList(createTime, orderType, endTime);
            List<PayInfo> payInfoList = null;
            PageResult<PayInfo> pageResult = null;
            if (page == null && limit == null) {
                payInfoList = payInfoRepository.findAll(specification);
                pageResult = new PageResult<>();
                pageResult.setData(payInfoList);
            } else {
                PageRequest pageRequest = payInfoRepository.pageRequest(page, limit, new Sort(Sort.Direction.DESC, "id"));
                Page<PayInfo> pageInfoList = payInfoRepository.findAll(specification, pageRequest);
                pageResult = new PageResult(pageInfoList);
                payInfoList = pageInfoList.getContent();
            }

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
                int dayMi = 1000 * 60 * 60 * 24;
                Date endTime = new Date(endTimeL + dayMi);

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
            refundCoupond(order);
            return Result.success("退款成功");
        } else if (Orders.PayMethodEnum.WX_PAY.getCode() == payMethond) {
            Long payInfoId = order.getPayInfoId();

            Result getResult = payProvider.getPayOrderInfo(payInfoId);
            int payMethod = 3;
            if (getResult.isSuccess()) {
                Map<String, Integer> map = (Map<String, Integer>) getResult.getData();
                payMethod = map.get("payMethod");
                //WX(2, "wx"), WXJS(3, "wxjs"),
            }
            Result result = payProvider.refundOrder(payInfoId, order.getPayMoney(), payMethod);
            if (result.getCode() == 1) {
                return Result.fail(result.getMsg());
            } else {
                order = ordersRepository.findOne(orderId);
                order.setStatus(Orders.statusEnum.CANCEL.getCode());
                ordersRepository.save(order);
                refundCoupond(order);
                return Result.success("退款成功");
            }
        } else {
            logger.info("退款异常，没有匹配到支付方式");
            return Result.fail("退款异常");
        }

    }

    /**
     * 退优惠劵
     *
     * @param
     */
    public void refundCoupond(Orders order) {
        //退优惠劵
        UserCoupon userCoupon = userCouponsRepository.findByOrderId(order.getId());
        if (userCoupon != null) {
            userCoupon.setState(UserCoupon.statusEnum.UN_USE.getCode());
            userCouponsRepository.save(userCoupon);
        } else {
            logger.info("orderId :" + order.getId() + " 没有对应的优惠劵");
        }

        //删除对应参与用户记录
        OrdersLotteryRelation ordersLotteryRelation = ordersLotteryRelationRep.findByOrderId(order.getId());
        if (ordersLotteryRelation != null) {
            ordersLotteryRelation.setDelFlag(1);
            ordersLotteryRelationRep.save(ordersLotteryRelation);
        }
        //如果这个订单对应大富贵的中奖人 是此用户的话，清空中奖人。

        OrdersLotteryRelation or = ordersLotteryRelationRep.findByOrderId(order.getId());

        if(or != null){
            Long ordersLotteryActivityId = or.getOrdersLotteryActivityId();

            OrdersLotteryActivity ordersLotteryActivity = ordersLotteryActivityRep.findOne(ordersLotteryActivityId);

            Long winnerUserId = ordersLotteryActivity.getWinnerUserId();

            if (order.getUser().getId().equals(winnerUserId)) {
                if (ordersLotteryActivity.getStatus() != OrdersLotteryActivity.statusEnum.FINISH.getCode()) {
                    ordersLotteryActivity.setWinnerUserId(null);
                    ordersLotteryActivity.setMoney(null);
                    ordersLotteryActivityRep.save(ordersLotteryActivity);
                }
            }

        }
//        Long ordersLotteryId = order.getOrdersLotteryId();
//        if (ordersLotteryId != null) {
//            OrdersLotteryActivity ordersLotteryActivity = ordersLotteryActivityRep.findOne(ordersLotteryId);
//            Long winnerUserId = ordersLotteryActivity.getWinnerUserId();
//            if (order.getUser().getId().equals(winnerUserId)) {
//                if (ordersLotteryActivity.getStatus() != OrdersLotteryActivity.statusEnum.FINISH.getCode()) {
//                    ordersLotteryActivity.setWinnerUserId(null);
//                    ordersLotteryActivity.setMoney(null);
//                    ordersLotteryActivityRep.save(ordersLotteryActivity);
//                }
//            }
//        }
    }

    /**
     * 判断订单是否能退款
     *
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
        return !find;
    }


    public Result exportExcel(String wxOrderNo, Integer orderType, Long startTime, Long endTime) {

        Result<PageResult<WxOrderListInfo>> pageResultResult = wxOrderList(null, null, wxOrderNo, orderType, startTime, endTime);
        PageResult<WxOrderListInfo> pageResult = pageResultResult.getData();
        List<WxOrderListInfo> wxOrderListInfoList = pageResult.getData();
        if (wxOrderListInfoList.size() > 5000) {
            return Result.fail("数据量过大，请增加筛选条件再导出");
        }
        //定义表的列名
        String[] rowsName = new String[]{"微信订单编号", "订单类型", "商品订单", "金额", "创建时间", "支付时间"};
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < wxOrderListInfoList.size(); i++) {
            WxOrderListInfo wxOrderListInfo = wxOrderListInfoList.get(i);
            objs = new Object[rowsName.length];
            objs[0] = wxOrderListInfo.getWxOrderNo();
            objs[1] = wxOrderListInfo.getOrderType();
            objs[2] = wxOrderListInfo.getOrderId();
            objs[3] = wxOrderListInfo.getMoney();
            objs[4] = wxOrderListInfo.getCreateTime();
            objs[5] = wxOrderListInfo.getCreateTime();
            dataList.add(objs);
        }
//        ExportExcel ex = new ExportExcel("订单", rowsName, dataList);
        try {
            InputStream is = POIUtils.export("订单列表", rowsName, dataList);
            //上传文件
            String fileName = DateUtil.formatDate(new Date(), "yyyy-MM-dd:HH:mm:ss") + "订单列表.xls";
            Result upload = fileService.upload(is, fileName);
            return upload;
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return Result.fail();
        }

    }

    @Transactional(propagation = Propagation.NEVER)
    public void method1() {
        logger.info("method1");
    }

    @Resource
    private OrdersLotteryActivityRep ordersLotteryActivityRep;

}
