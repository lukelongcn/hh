package com.h9.store.service;

import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.MoneyUtils;
import com.h9.store.modle.dto.ConvertGoodsDTO;
import com.h9.store.modle.vo.GoodsDetailVO;
import com.h9.store.modle.vo.GoodsListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itservice on 2017/11/20.
 */

@Service
public class GoodService {

    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;
    @Resource
    private OrderService orderService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private ConfigService configService;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private AddressRepository addressRepository;
    @Resource
    private OrderItemReposiroty orderItemReposiroty;


    /**
     * description: 减少商品库 -1
     */
    @SuppressWarnings("Duplicates")
    public Result changeStock(Long goodsId) {

        Goods goods = goodsReposiroty.findOne(goodsId);
        if (goods == null) return Result.fail("商品不存在");

        int stock = goods.getStock();
        if (stock <= 0) {
            return Result.fail("库存不足");
        }

        goods.setStock(--stock);
        if (stock <= 0) {
            goods.setStatus(2);
        }
        goodsReposiroty.save(goods);
        return Result.success();
    }

    /**
     * description:
     * @param count 要减少的库存
     */
    @SuppressWarnings("Duplicates")
    public Result changeStock(Long goodsId,int count) {

        Goods goods = goodsReposiroty.findOne(goodsId);
        if (goods == null) return Result.fail("商品不存在");

        int stock = goods.getStock();
        if (stock < count) {
            return Result.fail("库存不足");
        }

        stock -=count;
        goods.setStock(stock);
        if (stock <= 0) {
            goods.setStatus(2);
        }
        goodsReposiroty.save(goods);
        return Result.success();
    }

    public Result changeStock(Goods goods) {
        return changeStock(goods.getId());
    }

    public Result changeStock(Goods goods,int count) {
        return changeStock(goods.getId(),count);
    }


    /**
     * description:
     *
     * @see GoodsType.GoodsTypeEnum 商品类别
     * 商品类型 1今日新品 2日常家居 3食品饮料 4 所有商品
     *
     *          MOBILE_RECHARGE("mobile_recharge","手机卡"),
                DIDI_CARD("didi_card", "滴滴卡"),
                MATERIAL("material","实物"),
                FOODS("foods", "食物，饮料"),
                EVERYDAY_GOODS("everyday_goods", "日常家居"),
                VB("vb", "V币");
     */
    public Result goodsList(Integer type, int page, int size) {
        switch (type) {
            case 1:
                return todayNewGoods();
            case 2:
                return goodsPageQuery("everyday_goods", page, size);
            case 3:
                return goodsPageQuery("foods", page, size);
            case 4:
                return goodsPageQuery(page, size);
            default:
                return Result.fail("请填写正确的type");
        }
    }

    /**
     * description: 分页查询指定goodsType的商品列表
     *
     *
     */
    public Result goodsPageQuery(String code , Integer page, Integer size) {

        if(StringUtils.isBlank(code)) return Result.fail("不存在此类型商品");

        Page<Goods> pageObj = goodsReposiroty.findStoreGoods(code, goodsReposiroty.pageRequest(page, size) );
        PageResult<Goods> pageResult = new PageResult(pageObj);

        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }

    /**
     * description: 分页查询所有商品
     */
    public Result goodsPageQuery(int page, int size) {

        Page<Goods> pageObj = goodsReposiroty.findStoreGoods(goodsReposiroty.pageRequest(page, size) );
        PageResult<Goods> pageResult = new PageResult(pageObj);

        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }

    /**
     * description: 今日新品,取更新时间最近5条的
     *
     */
    public Result todayNewGoods() {


        PageRequest pageRequest = goodsReposiroty.pageRequest(0, 5);
        Page<Goods> pageObj = goodsReposiroty.findLastUpdate(pageRequest);
        PageResult<Goods> pageResult = new PageResult(pageObj);
        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }


    public Result goodsDetail( Long id,Long userId) {

        Goods goods = goodsReposiroty.findOne(id);
        if(goods == null){
            return Result.fail("商品不存在");
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        GoodsDetailVO vo = GoodsDetailVO.builder()
                .id(goods.getId())
                .img(goods.getImg())
                .desc(goods.getDescription())
                .price(MoneyUtils.formatMoney(goods.getPrice()))
                .name(goods.getName())
                .tip("*兑换商品和活动均与设备生产商Apple Inc无关。")
                .stock(goods.getStock())
                .balance(MoneyUtils.formatMoney(userAccount.getBalance()))
                .build();

        return Result.success(vo);
    }

    @Transactional
    public Result convertGoods(ConvertGoodsDTO convertGoodsDTO, Long userId) {
        Long addressId = convertGoodsDTO.getAddressId();
        Address address = addressRepository.findOne(addressId);

        if(address == null) return Result.fail("地址不存在");

        Integer count = convertGoodsDTO.getCount();
        Goods goods = goodsReposiroty.findOne(convertGoodsDTO.getGoodsId());
        if(goods == null) return Result.fail("商品不存在");

        User user = userRepository.findOne(userId);
        Long addressUserId = address.getUserId();

        if(!addressUserId.equals(userId)) return Result.fail("无效的地址");

        Result result = changeStock(goods,count);

        if(result.getCode() == 1) return result;

        //单独判断下余额是否足够
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        BigDecimal balance = userAccount.getBalance();
        if(balance.compareTo(goods.getRealPrice()) < 0){
            return Result.fail("余额不足");
        }

        String code = goods.getGoodsType().getCode();
        Orders order = orderService.initOrder(goods.getRealPrice(), user.getPhone(), Orders.orderTypeEnum.MATERIAL_GOODS.getCode()+"", "徽酒", user,code);
        order.setAddressId(addressId);
        order.setUserAddres(address.getAddress());
        order.setOrderFrom(1);
        ordersRepository.saveAndFlush(order);

        String balanceFlowType = configService.getValueFromMap("balanceFlowType", "12");
        Result payResult = commonService.setBalance(userId, goods.getRealPrice().negate(), 12L, order.getId(), "", balanceFlowType);
        if(payResult.getCode() == 1){
            throw new RuntimeException(payResult.getMsg());
        }

        order.setPayStatus(Orders.PayStatusEnum.PAID.getCode());


        OrderItems orderItems = new OrderItems(goods,count,order);
        ordersRepository.save(order);
        orderItems.setOrders(order);
        orderItemReposiroty.save(orderItems);

        Map<String, String> mapVo = new HashMap<>();
        mapVo.put("price", MoneyUtils.formatMoney(goods.getRealPrice()));
        mapVo.put("goodsName", goods.getName() + "*" + count);
        return Result.success(mapVo);
    }

}
