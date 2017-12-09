package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.BankBin;
import com.h9.common.db.repo.BankBinRepository;
import com.h9.common.utils.DateUtil;
import com.transfer.SqlUtils;
import com.transfer.db.entity.BankInfo;
import com.transfer.db.entity.UserInfo;
import com.transfer.db.repo.BankInfoRepository;
import com.transfer.db.repo.C_CardsRepository;
import com.transfer.db.repo.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.BufferedWriter;
import java.io.IOException;
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
    private ConfigService configService;



    public void user() {

        int page = 1;
        int limit = 1000;
        int totalPage = 0;
        PageResult<UserInfo> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        BufferedWriter userWtriter = SqlUtils. getBuffer("./sql/user.sql");
        BufferedWriter userAccountWtriter = SqlUtils.getBuffer("./sql/user_account.sql");
        BufferedWriter userExtendsWtriter = SqlUtils.getBuffer("./sql/user_extends.sql");
        do {
            userInfoPageResult = userInfoRepository.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<UserInfo> userInfos = userInfoPageResult.getData();
            for (UserInfo userInfo : userInfos) {
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
            page = page + 1;
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
            sql.append(userInfo.getId()+",");
            sql.append(SqlUtils.concatDate());
            sql.append(SqlUtils.concatDate());
            sql.append(SqlUtils.concatSql(userInfo.getUserimg()));
            sql.append(userInfo.getId()+",");
            sql.append(SqlUtils.concatDate());
            sql.append(1+",");
            sql.append(SqlUtils.concatSql(userInfo.getUsername()));
            sql.append(SqlUtils.concatSql(userInfo.getOpenID()));
            sql.append(SqlUtils.concatSql(userInfo.getPassword()));
            sql.append(SqlUtils.concatSql(userInfo.getPhone()));
            sql.append(SqlUtils.concatSql(userInfo.getUserGuid(),true));
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



    @Resource
    private C_CardsRepository c_cardsRepository;


    public String getDefaultHead() {
        String defaultHead = configService.getStringConfig("defaultHead");
        if (StringUtils.isBlank(defaultHead)) {
            logger.info("没有在参数配置中找到默认头像的配置");
            defaultHead = "";
        }
        return defaultHead;
    }

    @Resource
    private BankInfoRepository bankInfoRepository;

    public void userCard(){
        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<BankInfo> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        do {
            page = page + 1;
            userInfoPageResult = bankInfoRepository.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<BankInfo> userInfos = userInfoPageResult.getData();
            for (BankInfo userInfo : userInfos) {
                toBankInfo(userInfo);
            }
            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv("银行卡信息迁移进度 " + rate + "% " + page + "/" + totalPage);
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
    }
    @Resource
    private BankBinRepository bankBinRepository;


    public void toBankInfo(BankInfo userInfo){
        BankBin bankBin = bankBinRepository.findByBankBin(userInfo.getBankBin());
        if (bankBin == null) {
            bankBin = new BankBin();
            bankBin.setBankBin(userInfo.getBankBin());
            bankBin.setBankType(userInfo.getNewb());
            bankBinRepository.save(bankBin);
        }

    }


}

