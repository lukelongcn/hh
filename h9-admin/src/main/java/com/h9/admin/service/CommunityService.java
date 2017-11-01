package com.h9.admin.service;

import com.h9.common.base.Result;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.BannerTypeReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: George
 * @date: 2017/11/1 17:06
 */
@Service
@Transactional
public class CommunityService {

    @Autowired
    private BannerTypeReposiroty bannerTypeReposiroty;

    public Result<BannerType> addBannerType(BannerType bannerType){
        return Result.success(this.bannerTypeReposiroty.save(bannerType));
    }
}
