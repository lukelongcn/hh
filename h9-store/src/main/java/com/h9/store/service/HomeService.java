package com.h9.store.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
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

        Result<List<Goods>> findResult = orderService.findHotConvertOrders(0, 100);
        if (findResult.getCode() == 0) {
            List<Goods> orderList = findResult.getData();

            List<GoodsListVO> goodsListVO = orderList.stream()
                    .map(el -> new GoodsListVO(el))
                    .collect(Collectors.toList());

            vo.setHotGoods(goodsListVO);
        }

        return Result.success(vo);
    }
}
