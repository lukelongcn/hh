package com.h9.store.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.Goods;
import com.h9.common.db.entity.Orders;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.db.repo.BannerRepository;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.utils.MoneyUtils;
import com.h9.store.modle.vo.GoodsListVO;
import com.h9.store.modle.vo.HomeVO;
import com.h9.store.modle.vo.StoreHomeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2017/11/29.
 */
@Service
public class HomeService {

    @Resource
    private BannerRepository bannerRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private GoodsReposiroty goodsReposiroty;
    @Resource
    private OrderService orderService;
    public Result storeHome(Long userId) {

        List<Banner> bannerList = bannerRepository.findActiviBanner(new Date(), 2);

        Map<String, List<HomeVO>> banners = bannerList.stream()
                .map(HomeVO::new)
                .collect(Collectors.groupingBy(el -> el.getCode()));

        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        BigDecimal balance = userAccount.getBalance();

        StoreHomeVO vo = new StoreHomeVO().setBanners(banners)
                .setBalance(MoneyUtils.formatMoney(balance));

        Result<List<Orders>> findResult = orderService.findConvertOrders(0, 6);
        if (findResult.getCode() == 0) {
            List<Orders> orderList = findResult.getData();
            List<GoodsListVO> goodsListVO = orderList.stream().map(el -> new GoodsListVO(el.getOrderItems().get(0).getGoods())).collect(Collectors.toList());
            vo.setHotGoods(goodsListVO);
        }

        return Result.success(vo);
    }
}
