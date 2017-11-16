package com.transfer.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.common.base.PageResult;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.db.entity.UserExtends;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserExtendsRepository;
import com.h9.common.db.repo.UserRepository;
import com.transfer.db.entity.UserInfo;
import com.transfer.db.repo.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
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
        int limit = 500;
        int totalPage = 0;

        PageResult<UserInfo> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        do {
            page = page + 1;
            userInfoPageResult = userInfoRepository.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<UserInfo> userInfos = userInfoPageResult.getData();
            for (UserInfo userInfo : userInfos) {
                covertUser(userInfo);
            }

            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= 1 && userInfoPageResult.getCount() != 0)
                logger.debugv("用户迁移进度 " + rate + "% " + page + "/" + totalPage);
        } while (userInfoPageResult.getCount() != 0);

    }

    @Transactional
    private void covertUser(UserInfo userInfo) {
        if (StringUtils.isNotEmpty(userInfo.getOpenID())
                || StringUtils.isNotEmpty(userInfo.getPhone())) {
            User h9UserId = userRepository.findByH9UserId(userInfo.getId());

            if (h9UserId != null) {
                return;
            }

            User user = userInfo.corvert();
            if (StringUtils.isEmpty(user.getAvatar())) {
                user.setAvatar(getDefaultHead());
            }
            user = userRepository.saveAndFlush(user);
            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(user.getId());
            BigDecimal moneyCount = userInfo.getMoneyCount();
            if (moneyCount != null) {
                //TODO做负数处理
                BigDecimal balance = moneyCount.compareTo(new BigDecimal(0)) >= 0 ? moneyCount : new BigDecimal(0);
                userAccount.setBalance(balance);
            }
            Integer integralCount = userInfo.getIntegralCount();
            if (integralCount != null) {
                userAccount.setvCoins(new BigDecimal(integralCount));
            }
            userAccountRepository.save(userAccount);
            UserExtends userExtends = new UserExtends();
            userExtends.setUserId(user.getId());
            userExtends.setImgId(userInfo.getImgId());

            userExtendsRepository.save(userExtends);
        }

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
