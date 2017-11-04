package com.h9.admin.service;

import com.h9.admin.model.dto.community.BannerAddDTO;
import com.h9.admin.model.dto.community.BannerEditDTO;
import com.h9.admin.model.dto.community.BannerTypeEditDTO;
import com.h9.admin.model.dto.PageDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Activity;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.Banner;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.ArticleTypeRepository;
import com.h9.common.db.repo.BannerRepository;
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
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

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
            /*List<Banner> bannerList = (this.bannerRepository.findAllByBannerTypeId(bannerType.getId());
            if(!CollectionUtils.isEmpty(bannerList)){
                return Result.fail("")
            }*/
            bannerType.setEnable(0);
        }else{
            bannerType.setEnable(1);
        }
        return Result.success(this.bannerTypeRepository.save(bannerType));
    }

    public Result<Banner> addBanner(BannerAddDTO bannerAddDTO){
        BannerType bannerType = this.bannerTypeRepository.findOne(bannerAddDTO.getBannerTypeId());
        if(bannerType==null){
            return Result.fail("功能类别不存在");
        }
        if(this.bannerRepository.findByTitle(bannerAddDTO.getTitle())!=null){
            return Result.fail("名称已存在");
        }
        Banner banner = bannerAddDTO.toBanner();
        banner.setBannerType(bannerType);
        return Result.success(this.bannerRepository.save(banner));
    }

    public Result<Banner> updateBanner(BannerEditDTO bannerEditDTO){
        if(this.bannerRepository.findByIdNotAndTitle(bannerEditDTO.getId(),bannerEditDTO.getTitle())!=null){
            return Result.fail("名称已存在");
        }
        Banner b = this.bannerRepository.findOne(bannerEditDTO.getId());
        BeanUtils.copyProperties(bannerEditDTO,b);
        return Result.success(this.bannerRepository.save(b));
    }

    public Result<PageResult<Banner>> getBanners(long banner_type_id,PageDTO pageDTO){
        PageRequest pageRequest = this.bannerRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<Banner> banners = this.bannerRepository.findAllByBannerType_Id(banner_type_id,pageRequest);
        PageResult<Banner> pageResult = new PageResult<>(banners);
        return Result.success(pageResult);
    }

    public Result deleteBanner(long id){
       this.bannerRepository.delete(id);
        return Result.success("删除成功");
    }

    public Result<ArticleType> addArticleType(ArticleType articleType){
        return Result.success(this.articleTypeRepository.save(articleType));
    }

   /* public Result<Activity> addActivity(Activity activity){
        if(this.bannerTypeRepository.findByCode(bannerType.getCode())!=null){
            return Result.fail("标识已存在");
        }
        return Result.success(this.bannerTypeRepository.save(bannerType));
    }*/
}
