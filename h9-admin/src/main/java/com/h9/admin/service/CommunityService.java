package com.h9.admin.service;

import com.h9.admin.model.dto.activity.ActivityEditDTO;
import com.h9.admin.model.dto.community.*;
import com.h9.common.common.ConfigService;
import com.h9.common.modle.dto.PageDTO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
    @Autowired
    private AnnouncementReposiroty announcementReposiroty;
    @Autowired
    private ConfigService configService;

    public Result<BannerType> addBannerType(BannerType bannerType) {
        if (this.bannerTypeRepository.findByCode(bannerType.getCode()) != null) {
            return Result.fail("标识已存在");
        }
        return Result.success(this.bannerTypeRepository.save(bannerType));
    }

    public Result<PageResult<BannerType>> getBannerTypes(PageDTO pageDTO) {
        PageRequest pageRequest = this.bannerTypeRepository.pageRequest(pageDTO.getPageNumber(), pageDTO.getPageSize());
        Page<BannerType> bannerTypes = this.bannerTypeRepository.findAllByPage(pageRequest);
        PageResult<BannerType> pageResult = new PageResult<>(bannerTypes);
        return Result.success(pageResult);
    }

    public Result<BannerType> updateBannerTypeStatus(long id) {
        BannerType bannerType = this.bannerTypeRepository.findOne(id);
        if (bannerType.getEnable() == BannerType.EnableEnum.ENABLED.getId()) {
            /*List<Banner> bannerList = (this.bannerRepository.findAllByBannerTypeId(bannerType.getId());
            if(!CollectionUtils.isEmpty(bannerList)){
                return Result.fail("")
            }*/
            bannerType.setEnable(BannerType.EnableEnum.DISABLED.getId());
        } else {
            bannerType.setEnable(BannerType.EnableEnum.ENABLED.getId());
        }
        return Result.success(this.bannerTypeRepository.save(bannerType));
    }

    public Result<Banner> addBanner(BannerAddDTO bannerAddDTO) {
        BannerType bannerType = this.bannerTypeRepository.findOne(bannerAddDTO.getBannerTypeId());
        if (bannerType == null) {
            return Result.fail("功能类别不存在");
        }
        if (this.bannerRepository.findByTitle(bannerAddDTO.getTitle()) != null) {
            return Result.fail("名称已存在");
        }
        Banner banner = bannerAddDTO.toBanner();
        banner.setBannerType(bannerType);
        return Result.success(this.bannerRepository.save(banner));
    }

    public Result<Banner> updateBanner(BannerEditDTO bannerEditDTO) {
        if (this.bannerRepository.findByIdNotAndTitle(bannerEditDTO.getId(), bannerEditDTO.getTitle()) != null) {
            return Result.fail("名称已存在");
        }
        Banner b = this.bannerRepository.findOne(bannerEditDTO.getId());
        BeanUtils.copyProperties(bannerEditDTO, b);
        return Result.success(this.bannerRepository.save(b));
    }

    public Result<PageResult<Banner>> getBanners(long bannerTypeId, PageDTO pageDTO) {
        PageRequest pageRequest = this.bannerRepository.pageRequest(pageDTO.getPageNumber(), pageDTO.getPageSize());
        Page<Banner> banners = this.bannerRepository.findAllByBannerType_Id(bannerTypeId, pageRequest);
        PageResult<Banner> pageResult = new PageResult<>(banners);
        return Result.success(pageResult);
    }

    public Result deleteBanner(long id) {
        this.bannerRepository.delete(id);
        return Result.success("删除成功");
    }

    public Result<ArticleType> addArticleType(ArticleType articleType) {
        return Result.success(this.articleTypeRepository.save(articleType));
    }

    public Result<Activity> addActivity(Activity activity) {
        if (activity.getCode() != null && this.activityRepository.findByCode(activity.getCode()) != null) {
            return Result.fail("关键字已存在");
        }
        return Result.success(this.activityRepository.save(activity));
    }

    public Result<Activity> updateActivity(ActivityEditDTO activityEditDTO) {
        if (activityEditDTO.getCode() != null && this.activityRepository.findByIdNotAndCode(activityEditDTO.getId()
                , activityEditDTO.getCode()) != null) {
            return Result.fail("关键字已存在");
        }
        Activity a = this.activityRepository.findOne(activityEditDTO.getId());
        BeanUtils.copyProperties(activityEditDTO, a);
        return Result.success(this.activityRepository.save(a));
    }

    public Result<Activity> updateActivityStatus(long id) {
        Activity activity = this.activityRepository.findOne(id);
        if (activity == null) {
            return Result.fail("活动不存在");
        }
        if (activity.getEnable() == Activity.EnableEnum.DISABLED.getId()) {
            activity.setEnable(Activity.EnableEnum.ENABLED.getId());
        } else {
            activity.setEnable(Activity.EnableEnum.DISABLED.getId());
        }
        return Result.success(this.activityRepository.save(activity));
    }

    public Result<PageResult<Activity>> getActivities(PageDTO pageDTO) {
        PageRequest pageRequest = this.activityRepository.pageRequest(pageDTO.getPageNumber(), pageDTO.getPageSize());
        Page<Activity> activitys = this.activityRepository.findAllByPage(pageRequest);
        PageResult<Activity> pageResult = new PageResult<>(activitys);
        return Result.success(pageResult);
    }

    public Result<Goods> addGoods(GoodsAddDTO goodsAddDTO) {
        Goods goods = goodsAddDTO.toGoods();
        goods.setGoodsType(this.goodsTypeReposiroty.findOne(goodsAddDTO.getGoodsTypeId()));
        return Result.success(this.goodsReposiroty.save(goods));
    }

    public Result<Goods> updateGoods(GoodsEditDTO goodsEditDTO) {
        Goods goods = goodsEditDTO.toGoods();
        goods.setGoodsType(this.goodsTypeReposiroty.findOne(goodsEditDTO.getGoodsTypeId()));
        return Result.success(this.goodsReposiroty.save(goods));
    }

    public Result<Goods> updateGoodsStatus(long id) {
        Goods goods = this.goodsReposiroty.findOne(id);
        if (goods == null) {
            return Result.fail("商品不存在");
        }
        if (Goods.StatusEnum.ONSHELF.getId() == goods.getStatus()) {
            goods.setStatus(Goods.StatusEnum.OFFSHELF.getId());
        } else {
            goods.setStatus(Goods.StatusEnum.ONSHELF.getId());
        }
        return Result.success(this.goodsReposiroty.save(goods));
    }

    public Result<PageResult<Goods>> getGoods(PageDTO pageDTO) {
        PageRequest pageRequest = this.goodsReposiroty.pageRequest(pageDTO.getPageNumber(), pageDTO.getPageSize());
        Page<Goods> goods = this.goodsReposiroty.findAllByPage(pageRequest);
        PageResult<Goods> pageResult = new PageResult<>(goods);
        return Result.success(pageResult);
    }

   /* public Result<Goods> updateGoodsStatus(long goodsId){
        Goods goods = this.goodsReposiroty.findOne(goodsId);
        if
        return Result.success(this.goodsReposiroty.save(goodsEditDTO.toGoods()));
    }*/

    public Result<Announcement> addAnnouncement(Announcement announcement) {
        return Result.success(this.announcementReposiroty.save(announcement));
    }

    public Result<Announcement> updateAnnouncement(AnnouncementEditDTO announcementEditDTO) {
        Announcement announcement = this.announcementReposiroty.findOne(announcementEditDTO.getId());
        announcement = announcementEditDTO.toAnnouncement(announcement);
        return Result.success(this.announcementReposiroty.save(announcement));
    }

    public Result<Announcement> updateAnnouncementStatus(long id) {
        Announcement announcement = this.announcementReposiroty.findOne(id);
        if (announcement.getEnable() == Announcement.EnableEnum.DISABLED.getId()) {
            announcement.setEnable(Announcement.EnableEnum.ENABLED.getId());
        } else {
            announcement.setEnable(Announcement.EnableEnum.DISABLED.getId());
        }
        return Result.success(this.announcementReposiroty.save(announcement));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
// TODO: 2017/11/22 与获取文章列表实现一样，但是这边有事务的时候set的变化会被提交
    //因为这边类上有@Transactional注解，而articleService那边没有
    public Result<PageResult<Announcement>> getAnnouncements(PageDTO pageDTO) {
        Page<Announcement> announcements = this.announcementReposiroty.findAllByPage(pageDTO.toPageRequest());
        Map preLink = configService.getMapConfig("preLink");
        announcements.forEach(item -> this.setAnnouncementUrl(item, preLink));
        return Result.success(new PageResult<>(announcements));
    }

    public Result deleteAnnouncement(long id) {
        this.announcementReposiroty.delete(id);
        return Result.success("成功");
    }

    public Result<BannerType> updateBannerType(BannerTypeEditDTO bannerType) {
        if (this.bannerTypeRepository.findByIdNotAndCode(bannerType.getId(), bannerType.getCode()) != null) {
            return Result.fail("标识已存在");
        }
        BannerType b = getNewBannerType(bannerType);
        return Result.success(this.bannerTypeRepository.save(b));
    }

    private BannerType getNewBannerType(BannerTypeEditDTO bannerType) {
        BannerType b = this.bannerTypeRepository.findOne(bannerType.getId());
        BeanUtils.copyProperties(bannerType, b);
        return b;
    }

    private void setAnnouncementUrl(Announcement announcement, Map preLink) {
        String url = announcement.getUrl();
        if (StringUtils.isBlank(url)) {
            if (preLink != null) {
                announcement.setUrl(preLink.get("article").toString() + announcement.getId());
            }
        }
    }
}
