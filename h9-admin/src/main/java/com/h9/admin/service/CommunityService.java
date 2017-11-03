package com.h9.admin.service;

import com.h9.admin.model.dto.BannerTypeEditDTO;
import com.h9.admin.model.dto.PageDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.BannerTypeRepository;
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
    private BannerTypeRepository bannerTypeRepository;

    public Result<BannerType> addBannerType(BannerType bannerType){
        if(this.bannerTypeRepository.findByCode(bannerType.getCode())!=null){
            return Result.fail("标识已存在");
        }
        return Result.success(this.bannerTypeRepository.save(bannerType));
    }

    public Result<BannerType> updateBannerType(BannerTypeEditDTO bannerType){
        if(this.bannerTypeRepository.findByIdNotAndCode(bannerType.getId(),bannerType.getCode())!=null){
            return Result.fail("标识已存在");
        }
        BannerType b = getNewBannerType(bannerType);
        return Result.success(this.bannerTypeRepository.save(b));
    }

    private  BannerType getNewBannerType(BannerTypeEditDTO bannerType){
        BannerType b = this.bannerTypeRepository.findOne(bannerType.getId());
        BeanUtils.copyProperties(bannerType,b);
        return b;
    }

    public Result<PageResult<BannerType>> getBannerTypes(PageDTO pageDTO){
        PageRequest pageRequest = this.bannerTypeRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<BannerType> bannerTypes = this.bannerTypeRepository.findAllByPage(pageRequest);
        PageResult<BannerType> pageResult = new PageResult<>(bannerTypes);
        return Result.success(pageResult);
    }

    public Result<BannerType> updateBannerTypeStatus(long id){
        BannerType bannerType = this.bannerTypeRepository.findOne(id);
        if(bannerType.getEnable()==1){
            bannerType.setEnable(0);
        }else{
            bannerType.setEnable(1);
        }
        return Result.success(this.bannerTypeRepository.save(bannerType));
    }
}
