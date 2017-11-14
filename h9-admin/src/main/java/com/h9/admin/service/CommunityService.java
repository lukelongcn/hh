package com.h9.admin.service;

import com.h9.admin.model.dto.activity.ActivityEditDTO;
import com.h9.admin.model.dto.community.*;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
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
    private ActivityRepository activityRepository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private GoodsReposiroty goodsReposiroty;
    @Autowired
    private GoodsTypeReposiroty goodsTypeReposiroty;

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
        if(bannerType.getEnable()==BannerType.EnableEnum.ENABLED.getId()){
            /*List<Banner> bannerList = (this.bannerRepository.findAllByBannerTypeId(bannerType.getId());
            if(!CollectionUtils.isEmpty(bannerList)){
                return Result.fail("")
            }*/
            bannerType.setEnable(BannerType.EnableEnum.DISABLED.getId());
        }else{
            bannerType.setEnable(BannerType.EnableEnum.ENABLED.getId());
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

    public Result<Activity> addActivity(Activity activity){
        if(this.activityRepository.findByCode(activity.getCode())!=null){
            return Result.fail("关键字已存在");
        }
        return Result.success(this.activityRepository.save(activity));
    }

    public Result<Activity> updateActivity(ActivityEditDTO activityEditDTO){
        if(this.activityRepository.findByIdNotAndCode(activityEditDTO.getId(),activityEditDTO.getCode())!=null){
            return Result.fail("关键字已存在");
        }
        Activity a = this.activityRepository.findOne(activityEditDTO.getId());
        BeanUtils.copyProperties(activityEditDTO,a);
        return Result.success(this.activityRepository.save(a));
    }

    public Result<Activity> updateActivityStatus(long id){
        Activity activity = this.activityRepository.findOne(id);
        if(activity==null){
            return Result.fail("活动不存在");
        }
        if(activity.getEnable()==Activity.EnableEnum.DISABLED.getId()){
            activity.setEnable(Activity.EnableEnum.ENABLED.getId());
        }else{
            activity.setEnable(Activity.EnableEnum.DISABLED.getId());
        }
        return Result.success(this.activityRepository.save(activity));
    }

    public Result<PageResult<Activity>> getActivities(PageDTO pageDTO){
        PageRequest pageRequest = this.activityRepository.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<Activity> activitys = this.activityRepository.findAllByPage(pageRequest);
        PageResult<Activity> pageResult = new PageResult<>(activitys);
        return Result.success(pageResult);
    }

    public Result<Goods> addGoods(GoodsAddDTO goodsAddDTO){
        Goods goods = goodsAddDTO.toGoods();
        goods.setGoodsType(this.goodsTypeReposiroty.findOne(goodsAddDTO.getGoodsTypeId()));
        return Result.success(this.goodsReposiroty.save(goods));
    }

    public Result<Goods> updateGoods(GoodsEditDTO GoodsEditDTO){
        Goods goods = GoodsEditDTO.toGoods();
        goods.setGoodsType(this.goodsTypeReposiroty.findOne(GoodsEditDTO.getGoodsTypeId()));
        return Result.success(this.goodsReposiroty.save(goods));
    }

    public Result<Goods> updateGoodsStatus(long id){
        Goods goods = this.goodsReposiroty.findOne(id);
        if(goods == null){
            return Result.fail("商品不存在");
        }
        if(Goods.StatusEnum.ONSHELF.getId()==goods.getStatus()){
            goods.setStatus(Goods.StatusEnum.OFFSHELF.getId());
        }else{
            goods.setStatus(Goods.StatusEnum.ONSHELF.getId());
        }
        return Result.success(this.goodsReposiroty.save(goods));
    }

    public Result<PageResult<Goods>> getGoods(PageDTO pageDTO){
        PageRequest pageRequest = this.goodsReposiroty.pageRequest(pageDTO.getPageNumber(),pageDTO.getPageSize());
        Page<Goods> goodss = this.goodsReposiroty.findAllByPage(pageRequest);
        PageResult<Goods> pageResult = new PageResult<>(goodss);
        return Result.success(pageResult);
    }

   /* public Result<Goods> updateGoodsStatus(long goodsId){
        Goods goods = this.goodsReposiroty.findOne(goodsId);
        if
        return Result.success(this.goodsReposiroty.save(goodsEditDTO.toGoods()));
    }*/

    /*public Result<Announcement> addActivity(Announcement announcement){
        if(this.activityRepository.findByCode(activity.getCode())!=null){
            return Result.fail("关键字已存在");
        }
        return Result.success(this.activityRepository.save(activity));
    }*/
}
