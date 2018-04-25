package com.h9.store.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.coupon.UserCoupon;
import com.h9.common.db.entity.custom.CustomModule;
import com.h9.common.db.entity.custom.CustomModuleGoods;
import com.h9.common.db.entity.custom.CustomModuleItems;
import com.h9.common.db.entity.custom.UserCustomItems;
import com.h9.common.db.repo.*;
import com.h9.store.modle.dto.AddUserCustomDTO;
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
import java.util.stream.Collectors;

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
    private UserCustomItemsRep userCustomItemsRep;
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
        if (customModule == null) {
            return Result.fail("模块不存在");
        }
        List<CustomModuleItems> customModuleItems = customModuleItmesRep.findByCustomModule(customModule);
        if (CollectionUtils.isNotEmpty(customModuleItems)) {

            List<CustomModuleDetailVO> customModuleDetailVOList = customModuleItems.stream().map(ci -> {
                Integer textCount = ci.getTextCount();
                Integer customImagesCount = ci.getCustomImagesCount();
                List<String> mainImages = ci.getMainImages();
                CustomModuleDetailVO vo = new CustomModuleDetailVO(mainImages, customImagesCount, textCount, ci.getId(), ci.getType());
                return vo;
            }).collect(Collectors.toList());
            return Result.success(customModuleDetailVOList);
        }
        return Result.fail();
    }

    public Result addUserCustom(List<AddUserCustomDTO> addUserCustomDTOs, Long userId) {


        if (CollectionUtils.isNotEmpty(addUserCustomDTOs)) {
            List listVO = new ArrayList();
            for (AddUserCustomDTO addUserCustomDTO : addUserCustomDTOs) {
                List<String> images = addUserCustomDTO.getImages();
                List<String> texts = addUserCustomDTO.getTexts();
                Long id = addUserCustomDTO.getId();
                CustomModuleItems customModuleItems = customModuleItmesRep.findOne(id);

                if(customModuleItems == null){
                    return Result.fail("id " + id + " 定制项不存在");
                }
                if (customModuleItems.getTextCount() < addUserCustomDTO.getTexts().size()) {
                    return Result.fail("定制的文本数量应为 " + customModuleItems.getTextCount());
                }

                if (customModuleItems.getCustomImagesCount() < addUserCustomDTO.getImages().size()) {
                    return Result.fail("定制的图片数量应为 " + customModuleItems.getTextCount());
                }
                if (customModuleItems != null) {
                    UserCustomItems userCustomItems = new UserCustomItems();
                    userCustomItems.setCustomModuleItemsId(customModuleItems.getId());
                    userCustomItems.setCustomImages(images);
                    userCustomItems.setTexts(texts);
                    userCustomItems.setUserId(userId);
                    userCustomItems.setType(addUserCustomDTO.getType());
                    userCustomItemsRep.saveAndFlush(userCustomItems);
                    listVO.add(userCustomItems.getId());
                }
            }
            return Result.success(listVO);
        }
        return Result.fail();
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
    public Result validateBalance(UserAccount userAccount, BigDecimal payMoney, Integer payMethod, Goods goods) {
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
                Orders orders = ordersRepository.saveAndFlush(order);
                // 订单关联用户模板
                orderLinkUserCustomItems(customModuleDTO,orders);
                return Result.success(showInfo);
            } else {
                Result  payResult = goodService.getPayInfo(order.getId(), payMoney, user, customModuleDTO.getPayPlatform(), count, goods);
                // 如微信支付成功
                if (payResult.getCode() == 0 ){
                    // 订单关联用户模板
                    orderLinkUserCustomItems(customModuleDTO,order);
                }
                return payResult;
            }
        } else {
            //余额支付
            Result result = goodService.changeStock(goods, count);
            if (result.getCode() == 1) {
                return result;
            }
            Result balancePayResult = goodService.balancePay(order, userId, goods, payMoney, count);
            // 如余额支付成功
            if (balancePayResult.getCode() == 0) {
                Map<Object, Object> showInfo = goodService.showJoinIn(order, user, goods, 2, count);
                order.setStatus(Orders.statusEnum.WAIT_SEND.getCode());
                order.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
                order.setPayMethond(Orders.PayMethodEnum.BALANCE_PAY.getCode());
                Orders orders = ordersRepository.saveAndFlush(order);
                // 订单关联用户模板
                orderLinkUserCustomItems(customModuleDTO,orders);
                return Result.success(showInfo);
            }
            return balancePayResult;
        }
    }

    @Transactional
    public void  orderLinkUserCustomItems(CustomModuleDTO customModuleDTO,Orders orders){
        List<Long> userModelMessage = customModuleDTO.getUserCustomItemsId();
        userModelMessage.forEach(u->{
            UserCustomItems userCustomItems = userCustomItemsRep.findOne(u);
            userCustomItems.setOrderId(orders.getId());
            userCustomItemsRep.save(userCustomItems);
        });
    }

    public Result modelGoods(long userId, Long id) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        List<CustomModuleGoods> customModuleGoods = customModuleGoodsRep.findByCustomModuleId(0, id);
        if (CollectionUtils.isEmpty(customModuleGoods)) {
            return Result.fail("暂无可选订制商品");
        }
        List<ModelGoodsVO> modelGoodsVOS = new ArrayList<>();
        customModuleGoods.forEach(c -> {
            Long goodsId = c.getGoodsId();
            Goods goods = goodsReposiroty.findOne(goodsId);
            if (goods == null) {
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
