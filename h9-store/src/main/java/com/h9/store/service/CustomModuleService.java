package com.h9.store.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleGoods;
import com.h9.common.db.entity.custom.CustomModuleItems;
import com.h9.common.db.entity.order.Address;
import com.h9.common.db.entity.order.Goods;
import com.h9.common.db.entity.order.OrderItems;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.repo.*;
import com.h9.common.utils.MoneyUtils;
import com.h9.store.modle.dto.ConvertGoodsDTO;
import com.h9.store.modle.dto.CustomModuleDTO;
import com.h9.store.modle.vo.CustomModuleDetailVO;
import com.h9.store.modle.vo.GoodsDetailVO;
import com.h9.store.modle.vo.ModelGoodsVO;
import com.h9.store.modle.vo.PersonaLModelOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.h9.common.db.entity.coupon.UserCoupon.statusEnum.UN_USE;
import static com.h9.common.db.entity.coupon.UserCoupon.statusEnum.USED;
import static com.h9.common.db.entity.order.Orders.PayMethodEnum.BALANCE_PAY;

/**
 * Created by Ln on 2018/4/24.
 */
@Service
public class CustomModuleService {
    @Resource
    private ConfigService configService;
    @Resource
    private CustomModuleRep customModuleRep;
    @Resource
    private CustomModuleGoodsRep customModuleGoodsRep;
    @Resource
    private CustomModuleItmesRep customModuleItmesRep;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private AddressRepository addressRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private OrderService orderService;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;
    @Resource
    private GoodService goodService;

    public Result types() {
        List<String> list = configService.getStringListConfig("customType");
        return Result.success(list);
    }

    public Result modules(Long type, Integer page, Integer limit) {

        PageRequest pageRequest = customModuleRep.pageRequest(page, limit, new Sort(Sort.Direction.DESC, "id"));
        Page<Map> mapVO = customModuleRep
                .findByModuleTypeId(type, 0, pageRequest).map(cm -> {
                    Map map = new HashMap<>();
                    map.put("id", cm.getId());
                    map.put("name", cm.getName());
                    List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(cm);
                    String imgUrl = "";
                    if (CollectionUtils.isNotEmpty(customModuleItems)) {
                        CustomModuleItems citem = customModuleItems.get(0);
                        List<String> mainImages = citem.getMainImages();
                        if (CollectionUtils.isNotEmpty(mainImages)) {
                            imgUrl = mainImages.get(0);
                        }
                    }
                    map.put("imgUrl", imgUrl);
                    return map;
                });

        return Result.success(new PageResult<>(mapVO));
    }

    public Result modulesDetail(Long id) {
        CustomModule customModule = customModuleRep.findOne(id);
        if(customModule == null){
            return Result.fail("模块不存在");
        }
        List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(customModule);

        CustomModuleDetailVO VO = new CustomModuleDetailVO();
        return null;
    }

    public Result modelPay(CustomModuleDTO customModuleDTO, Long userId) {
        Long addressId = customModuleDTO.getAddressId();
        Address address = addressRepository.findOne(addressId);
        if (address == null) {
            return Result.fail("地址不存在");
        }

        Integer count = customModuleDTO.getCount();
        Goods goods = goodsReposiroty.findOne(customModuleDTO.getGoodsId());
        if (goods == null) {
            return Result.fail("商品不存在");
        }
        if (goods.getStatus() == 2) {
            return Result.fail("商品已下架");
        }
        BigDecimal payMoney = goods.getRealPrice().multiply(new BigDecimal(customModuleDTO.getCount()));

        User user = userRepository.findOne(userId);
        Long addressUserId = address.getUserId();

        if (!addressUserId.equals(userId)) {
            return Result.fail("无效的地址");
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        Result result = validateBalance(userAccount, payMoney, customModuleDTO.getPayMethod(), goods);
        if (!result.isSuccess()) {
            return result;
        }

        String code = goods.getGoodsType().getCode();

        Orders order = orderService.initOrder(payMoney, address.getPhone()
                , Orders.orderTypeEnum.MATERIAL_GOODS.getCode() + ""
                , "徽酒"
                , user
                , code
                , address.getName());

        order.setAddressId(addressId);
        order.setUserAddres(address.getProvince() + address.getCity() + address.getDistict() + address.getAddress());
        order.setOrderFrom(1);
        order.setStatus(Orders.statusEnum.CANCEL.getCode());
        ordersRepository.saveAndFlush(order);
        int payMethod = customModuleDTO.getPayMethod();
        //订单项
        OrderItems orderItems = new OrderItems(goods, count, order);
        order = ordersRepository.saveAndFlush(order);
        orderItems.setOrders(order);
        orderItemReposiroty.saveAndFlush(orderItems);

        return handlerPay(payMethod, order, payMoney, userId, customModuleDTO, goods);
    }

    @Transactional
    private Result validateBalance(UserAccount userAccount, BigDecimal payMoney, Integer payMethod, Goods goods) {
        BigDecimal balance = userAccount.getBalance();
        if (payMethod == BALANCE_PAY.getCode() && balance.compareTo(payMoney) < 0) {
                return Result.fail("余额不足");
        } else {
                payMoney = payMoney.subtract(goods.getRealPrice());
                if (payMethod == BALANCE_PAY.getCode() && balance.compareTo(payMoney) < 0) {
                    return Result.fail("余额不足");
                }
        }
        return Result.success();
    }

    @Transactional
    public Result handlerPay(int payMethod, Orders order, BigDecimal payMoney, Long userId, CustomModuleDTO customModuleDTO, Goods goods) {
        int count = customModuleDTO.getCount();
        User user = userRepository.findOne(userId);

        if (payMethod == Orders.PayMethodEnum.WX_PAY.getCode()) {
            // 微信支付
            if (payMoney.compareTo(BigDecimal.ZERO) == 0) {
                Map<Object, Object> showInfo = goodService.showJoinIn(order, user, goods, 1, count);
                order.setStatus(Orders.statusEnum.WAIT_SEND.getCode());
                order.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
                order.setPayMethond(Orders.PayMethodEnum.BALANCE_PAY.getCode());
                ordersRepository.save(order);
                return Result.success(showInfo);
            } else {
                return goodService.getPayInfo(order.getId(), payMoney, user, customModuleDTO.getPayPlatform(), count, goods);
            }
        } else {
            //余额支付
            Result result = goodService.changeStock(goods, count);
            if (result.getCode() == 1) {
                return result;
            }
            Result balancePayResult = goodService.balancePay(order, userId, goods, payMoney, count);
            if (balancePayResult.getCode() == 0) {
                Map<Object, Object> showInfo = goodService.showJoinIn(order, user, goods, 2, count);
                order.setStatus(Orders.statusEnum.WAIT_SEND.getCode());
                order.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
                order.setPayMethond(Orders.PayMethodEnum.BALANCE_PAY.getCode());
                ordersRepository.save(order);
                return Result.success(showInfo);
            }
            return balancePayResult;
        }
    }


    public Result modelGoods(long userId, Long id) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        List<CustomModuleGoods> customModuleGoods = customModuleGoodsRep.findByCustomModuleId(0,id);
        if (CollectionUtils.isEmpty(customModuleGoods)){
            return Result.fail("暂无可选订制商品");
        }
        List<ModelGoodsVO> modelGoodsVOS = new ArrayList<>();
        customModuleGoods.forEach(c->{
            Long goodsId = c.getGoodsId();
            Goods goods = goodsReposiroty.findOne(goodsId);
            if (goods == null){
                return;
            }
            ModelGoodsVO vo = ModelGoodsVO.builder()
                    .id(goods.getId())
                    .img(goods.getImg())
                    .desc(goods.getDescription())
                    .price(MoneyUtils.formatMoney(goods.getRealPrice()))
                    .name(goods.getName())
                    .unit(goods.getUnit())
                    .build();
            vo.setOrderlimitNumberl(c.getNumbers());
            modelGoodsVOS.add(vo);
        });
        PersonaLModelOrderVO personaLModelOrderVO = new PersonaLModelOrderVO();
        personaLModelOrderVO.setBalance(MoneyUtils.formatMoney(userAccount.getBalance()));
        personaLModelOrderVO.setModelGoodsVOS(modelGoodsVOS);
        return Result.success(personaLModelOrderVO);
    }
}
