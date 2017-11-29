package com.h9.api.service;

import com.h9.api.model.dto.ConvertGoodsDTO;
import com.h9.api.model.vo.GoodsDetailVO;
import com.h9.api.model.vo.GoodsListVO;
import com.h9.api.model.vo.OrderListVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.MoneyUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itservice on 2017/11/20.
 */
@Transactional
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
    private AddressReposiroty addressReposiroty;

    /**
     * description: 减少商品库 -1
     */
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

    public Result changeStock(Goods goods) {
        return changeStock(goods.getId());
    }

    /**
     * description:
     *
     * @see com.h9.common.db.entity.GoodsType.GoodsTypeEnum 商品类别
     * 商品类型 1今日新品 2日常家居 3食品饮料 4 所有商品
     */
    public Result goodsList(Integer type, int page, int size) {
        switch (type) {
            case 1:
                return todayNewGoods();
            case 2:
                return goodsPageQuery(6, page, size);
            case 3:
                return goodsPageQuery(5, page, size);
            case 4:
                return goodsPageQuery(page, size);
            default:
                return Result.fail("请填写正确的type");
        }
    }

    /**
     * description: 分页查询指定goodsType的商品列表
     */
    public Result goodsPageQuery(Integer goodsTypeId, Integer page, Integer size) {

        GoodsType goodsType = goodsTypeReposiroty.findOne(Long.valueOf(goodsTypeId));
        if (goodsType == null) return Result.fail("此类别不存在");
        Page<Goods> pageObj = goodsReposiroty.findByGoodsType(goodsType, new PageRequest(page, size));
        PageResult<Goods> pageResult = new PageResult(pageObj);

        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }

    /**
     * description: 分页查询所有商品
     */
    public Result goodsPageQuery(int page, int size) {

        Page<Goods> pageObj = goodsReposiroty.findAll(new PageRequest(page, size));
        PageResult<Goods> pageResult = new PageResult(pageObj);

        return Result.success(pageResult.result2Result(GoodsListVO::new));
    }

    /**
     * description: 今日新品
     */
    public Result todayNewGoods() {

        return Result.success();
    }


    public Result goodsDetail(Long id) {

        Goods goods = goodsReposiroty.findOne(id);
        if(goods == null){
            return Result.fail("商品不存在");
        }

        GoodsDetailVO vo = GoodsDetailVO.builder()
                .img(goods.getImg())
                .desc(goods.getDescription())
                .price(MoneyUtils.formatMoney(goods.getPrice()))
                .name(goods.getName())
                .tip("*兑换商品和活动均与设备生产商Apple Inc无关。")
                .build();

        return Result.success(vo);
    }

    public Result convertGoods(ConvertGoodsDTO convertGoodsDTO, Long userId) {
        Long addressId = convertGoodsDTO.getAddressId();
        Address address = addressReposiroty.findOne(addressId);

        if(address == null) return Result.fail("地址不存在");

        Integer count = convertGoodsDTO.getCount();
        Goods goods = goodsReposiroty.findOne(convertGoodsDTO.getGoodsId());
        if(goods == null) return Result.fail("商品不存在");

        User user = userRepository.findOne(userId);

        Orders order = orderService.initOrder(goods.getRealPrice(), user.getPhone(), 12, "徽酒", user);

        ordersRepository.saveAndFlush(order);

        String balanceFlowType = configService.getValueFromMap("balanceFlowType", "12");
        commonService.setBalance(userId, goods.getRealPrice().negate(), 12L, order.getId(), "", balanceFlowType);
        order.setPayStatus(Orders.PayStatusEnum.PAID.getCode());
        ordersRepository.save(order);

        Map<String, String> mapVo = new HashMap<>();
        mapVo.put("price", MoneyUtils.formatMoney(goods.getRealPrice()));
        mapVo.put("goodsName", goods.getName() + "*" + count);
        return Result.success(mapVo);
    }
}
