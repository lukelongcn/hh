package com.h9.admin.interceptor;

import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.repo.ArticleTypeRepository;
import com.h9.common.db.repo.BannerTypeRepository;

import org.jboss.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:权限初始化监听器
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017-08-09
 * Time: 15:47
 */
@Component
public class InitListener implements ApplicationListener<ApplicationReadyEvent> {
    private Logger logger = Logger.getLogger(InitListener.class);
    @Resource
    private BannerTypeRepository bannerTypeRepository;
    @Resource
    private ArticleTypeRepository articleTypeRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        TODO 初始化数据
       this.initBannerType();
       this.initArticleType();

    }

    private void initBannerType(){
        this.saveBannerType("顶部banner","topBanner",1);
        this.saveBannerType("功能banner","navigationBanner",1);
        this.saveBannerType("创意banner","ideaBanner",1);
    }

    private void saveBannerType(String name, String code, Integer enable){
        if(this.bannerTypeRepository.findByCode(code)==null){
            BannerType bannerType = new BannerType(name,code,enable);
            this.bannerTypeRepository.save(bannerType);
        }
    }

    private void initArticleType(){
        this.savetArticleType("公告","noticeArticle",1);
        this.savetArticleType("推荐文章","recommendArticle",1);
    }

    private void savetArticleType(String name, String code, Integer enable){
        if(this.articleTypeRepository.findByCode(code)==null){
            ArticleType articleType = new ArticleType(name,code,enable);
            this.articleTypeRepository.save(articleType);
        }
    }


}
