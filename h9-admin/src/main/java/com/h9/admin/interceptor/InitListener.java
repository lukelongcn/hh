package com.h9.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.ArticleType;
import com.h9.common.db.entity.BannerType;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.entity.Permission;
import com.h9.common.db.repo.ArticleTypeRepository;
import com.h9.common.db.repo.BannerTypeRepository;

import com.h9.common.db.repo.GlobalPropertyRepository;
import com.h9.common.db.repo.PermissionRepository;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    @Resource
    private GlobalPropertyRepository globalPropertyRepository;
    @Resource
    private RedisBean redisBean;
    @Resource
    private PermissionRepository permissionRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        TODO 初始化数据
       this.initBannerType();
       this.initArticleType();
       this.initCache();
       this.initPermission();
    }

    private void initBannerType(){
        this.saveBannerType("顶部banner","topBanner",1);
        this.saveBannerType("功能banner","navigationBanner",1);
        this.saveBannerType("创意banner","ideaBanner",1);
    }

    private void saveBannerType(String name, String code, Integer enable){
        if(this.bannerTypeRepository.findByCode(code)==null){
            Date date = new Date();
            BannerType bannerType = new BannerType(name,code,enable,date,date);
            this.bannerTypeRepository.save(bannerType);
        }
    }

    private void initArticleType(){
        this.saveArticleType("推荐文章","recommendArticle",1);
    }

    private void saveArticleType(String name, String code, Integer enable){
        if(this.articleTypeRepository.findByCode(code)==null){
            ArticleType articleType = new ArticleType(name,code,enable);
            this.articleTypeRepository.save(articleType);
        }
    }

    private void initCache(){
        List<GlobalProperty> globalProperties = globalPropertyRepository.findAll();
        for (int i = 0; i < globalProperties.size(); i++) {
            GlobalProperty globalProperty =  globalProperties.get(i);
            String code = globalProperty.getCode();
            redisBean.setStringValue(RedisKey.getConfigValue(code),globalProperty.getVal());
            redisBean.setStringValue(RedisKey.getConfigType(code),globalProperty.getType()+"");
        }
    }

    private void createPermission(int first, int second,int third, String accessCode, String name, String description,
                                  Long parentId) {
        if (this.permissionRepository.findByAccessCode(accessCode) != null) {
            return;
        }
        long id = first*10000+second*100+third;
        Permission permission = new Permission(id,name,accessCode,parentId,description);
        this.permissionRepository.save(permission);
    }

    private void createPermission(int first, int second,int third, String accessCode, String name, String description) {
        this.createPermission(first, second, third, accessCode, name, description, null);
    }

    private void initPermission() {
        this.createPermission(0,0,1,"backstage","后台","后台");

        this.createPermission(1,0,0,"activity_management","活动管理","活动管理",1L);

        this.createPermission(2,0,0,"community_management","社区管理","社区管理",1L);

        this.createPermission(3,0,0,"transaction_management","交易管理","交易管理",1L);

        this.createPermission(4,0,0,"finance_management","财务管理","财务管理",1L);

        this.createPermission(5,0,0,"basis_setting","基础设置","基础设置",1L);

        this.createPermission(5,0,0,"log_management","日志管理","日志管理",1L);

        this.createPermission(99,0,0,"workbench","我的工作台","我的工作台",1L);
        this.createPermission(99,0,1,"workbench:statistics:lottery","数据统计","我的工作台-数据统计",990000L);

    }

    private void createRole() {

    }

}
