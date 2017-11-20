package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.common.ConfigService;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserExtendsRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.DateUtil;
import com.transfer.SqlUtils;
import com.transfer.Util;
import com.transfer.db.entity.UserInfo;
import com.transfer.db.repo.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.jboss.logging.Logger.getLogger;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * UserService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/14
 * Time: 9:51
 */
@Component
public class UserService {

    Logger logger = getLogger(UserService.class);
    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserExtendsRepository userExtendsRepository;
    @Resource
    private ConfigService configService;



    public void user() {

        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<UserInfo> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        BufferedWriter userWtriter = SqlUtils. getBuffer("./user.sql");
        BufferedWriter userAccountWtriter = SqlUtils.getBuffer("./user_account.sql");
        BufferedWriter userExtendsWtriter = SqlUtils.getBuffer("./user_extends.sql");
        do {
            page = page + 1;
            userInfoPageResult = userInfoRepository.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<UserInfo> userInfos = userInfoPageResult.getData();
            for (UserInfo userInfo : userInfos) {
//                covertUser(userInfo);
                try {
                    String sql = covertToUser(userInfo);
                    if(sql!=null){
                        userWtriter.write(sql);
                        userWtriter.newLine();
                    }
                    String userAccount = covertToUserAccount(userInfo);
                    if(userAccount!=null){
                        userAccountWtriter.write(userAccount);
                        userAccountWtriter.newLine();
                    }
                    String userExtends = covertToUserExtends(userInfo);
                    if(userExtends!=null){
                        userExtendsWtriter.write(userExtends);
                        userExtendsWtriter.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv("用户迁移进度 " + rate + "% " + page + "/" + totalPage);
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
        SqlUtils.close(userWtriter);
        SqlUtils.close(userAccountWtriter);
        SqlUtils.close(userExtendsWtriter);
    }



    @Transactional
    private String covertToUser(UserInfo userInfo) {
        if (StringUtils.isNotEmpty(userInfo.getOpenID())
                || StringUtils.isNotEmpty(userInfo.getPhone())) {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into user(id,create_time,update_time,avatar,h9_user_id,last_login_time");
            sql.append(",login_count,nick_name,open_id,password,phone,uuid) value(");
            sql.append(userInfo.getId());
            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");

            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");
            if(!StringUtils.isEmpty(userInfo.getUserimg())){
                sql.append(",'"+userInfo.getUserimg()+"'");
            }else{
                sql.append(",null");
            }
            sql.append(","+userInfo.getId());
            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");
            sql.append(","+1);
            if(!StringUtils.isEmpty(userInfo.getUsername())){
                sql.append(",'"+ SqlUtils.fomart(userInfo.getUsername())+"'");
            }else{
                sql.append(",null");
            }
            if(!StringUtils.isEmpty(userInfo.getOpenID())){
                sql.append(",'"+userInfo.getOpenID()+"'");
            }else{
                sql.append(",null");
            }
            if(!StringUtils.isEmpty(userInfo.getPassword())){
                sql.append(",'"+userInfo.getPassword()+"'");
            }else{
                sql.append(",null");
            }
            if(!StringUtils.isEmpty(userInfo.getPhone())){
                sql.append(",'"+userInfo.getPhone()+"'");
            }else{
                sql.append(",null");
            }
            if(!StringUtils.isEmpty(userInfo.getUserGuid())){
                sql.append(",'"+userInfo.getUserGuid()+"'");
            }else{
                sql.append(",null");
            }
            sql.append(");");
            return sql.toString();
        }
        return null;
    }

    @Transactional
    private String covertToUserAccount(UserInfo userInfo) {
        if (StringUtils.isNotEmpty(userInfo.getOpenID())
                || StringUtils.isNotEmpty(userInfo.getPhone())) {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into user_account(user_id,create_time,update_time,balance,v_coins) ");
            sql.append("value(");
            sql.append(userInfo.getId());
            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");
            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");
            BigDecimal moneyCount = userInfo.getMoneyCount();
            if(moneyCount == null){
                moneyCount = new BigDecimal(0);
            }
            sql.append(","+moneyCount);
            Integer integralCount = userInfo.getIntegralCount();
            if(integralCount==null){
                integralCount = 0;
            }
            sql.append(","+integralCount);
            sql.append(");");
            return sql.toString();
        }
        return null;
    }

    @Transactional
    private String covertToUserExtends(UserInfo userInfo) {
        if (StringUtils.isNotEmpty(userInfo.getOpenID())
                || StringUtils.isNotEmpty(userInfo.getPhone())) {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into user_extends(user_id,create_time,update_time) ");
            sql.append("value(");
            sql.append(userInfo.getId());
            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");
            sql.append(",'"+DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND)+"'");
            sql.append(");");
            return sql.toString();
        }
        return null;
    }



    public String getDefaultHead() {
        String defaultHead = configService.getStringConfig("defaultHead");
        if (StringUtils.isBlank(defaultHead)) {
            logger.info("没有在参数配置中找到默认头像的配置");
            defaultHead = "";
        }
        return defaultHead;
    }
}
