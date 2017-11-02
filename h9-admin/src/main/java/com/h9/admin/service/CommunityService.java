package com.h9.admin.service;

import com.h9.admin.model.dto.BannerTypeEditDTO;
import com.h9.admin.model.dto.PageDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.BannerTypeReposiroty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        if(this.bannerTypeReposiroty.findByCode(bannerType.getCode())!=null){
            return Result.fail("标识已存在");
        }
        return Result.success(this.bannerTypeReposiroty.save(bannerType));
    }

    public Result<BannerType> updateBannerType(BannerTypeEditDTO bannerType){
        if(this.bannerTypeReposiroty.findByIdNotAndCode(bannerType.getId(),bannerType.getCode())!=null){
            return Result.fail("标识已存在");
        }
        BannerType b = getNewBannerType(bannerType);
        return Result.success(this.bannerTypeReposiroty.save(b));
    }

    private  BannerType getNewBannerType(BannerTypeEditDTO bannerType){
        BannerType b = this.bannerTypeReposiroty.findOne(bannerType.getId());
        BeanUtils.copyProperties(bannerType,b);
        return b;
    }

    public Result<PageResult<BannerType>> getBannerTypes(PageDTO pageDTO){
        PageRequest pageRequest = this.bannerTypeReposiroty.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<BannerType> bannerTypes = this.bannerTypeReposiroty.findAllByPage(pageRequest);
        PageResult<BannerType> pageResult = new PageResult<>(bannerTypes);
        return Result.success(pageResult);
    }

    public Result<BannerType> updateBannerTypeStatus(long id){
        BannerType bannerType = this.bannerTypeReposiroty.findOne(id);
        if(bannerType.getEnable()==1){
            bannerType.setEnable(0);
        }else{
            bannerType.setEnable(1);
        }
        return Result.success(this.bannerTypeReposiroty.save(bannerType));
    }
}
