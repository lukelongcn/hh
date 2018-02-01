package com.h9.api.service;

import com.h9.api.model.vo.TravelPageVO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.config.Banner;
import com.h9.common.db.entity.config.BannerType;
import com.h9.common.db.repo.BannerRepository;
import com.h9.common.db.repo.BannerTypeRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.h9.common.db.entity.config.BannerType.LocaltionEnum.*;

/**
 * Created by itservice on 2018/2/1.
 */
@Service
public class TravelService {
    @Resource
    private BannerTypeRepository bannerTypeRepository;
    @Resource
    private BannerRepository bannerRepository;

    /**
     * description: tab 1 为旅游加体检，2为体检 ，3 为旅游
     */
    public Result bannerList(long userId, Integer tab) {
        int location = tab2Location(tab);
        List<BannerType> bannerTypeList = bannerTypeRepository.findByLocation(location);

        List<TravelPageVO> listVO = bannerTypeList
                .stream()
                .map(bannerType -> {
                    List<Banner> bannerList = bannerRepository.findAllByBannerTypeIdOrder(bannerType.getId());
                    TravelPageVO vo = new TravelPageVO(bannerType.getCode(), bannerList);
                    return vo;
                }).collect(Collectors.toList());

        return Result.success(listVO);
    }

    public int tab2Location(Integer tab) {

        switch (tab) {
            case 1:
                return BannerType.LocaltionEnum.TRAVEL_ALL.getId();
            case 2:
                return TRAVEL_CHECK.getId();
            case 3:
                return TRAVEL.getId();
        }
        return TRAVEL_ALL.getId();
    }
}
