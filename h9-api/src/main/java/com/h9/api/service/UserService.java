package com.h9.api.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.dto.WechatConfig;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.model.vo.UserInfoVO;
import com.h9.api.provider.SMSProvide;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.provider.model.OpenIdCode;
import com.h9.api.provider.model.WeChatUser;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by itservice on 2017/10/26.
 */
@Service
@Transactional
public class UserService {
    @Value("${h9.current.envir}")
    private String currentEnvironment;
    @Resource
    private RedisBean redisBean;
    @Resource
    private SMSProvide smService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserExtendsRepository userExtendsRepository;
    @Resource
    private CommonService commonService;


    @Resource
    private GlobalPropertyRepository globalPropertyRepository;
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleTypeRepository articleTypeRepository;
    @Resource
    private SmsService smsService;

    @Resource
    private ConfigService configService;
    private Logger logger = Logger.getLogger(this.getClass());

    public Result loginFromPhone(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        if (phone.length() > 11) return Result.fail("请输入正确的手机号码");
        String code = userLoginDTO.getCode();
        String redisCode = redisBean.getStringValue(String.format(RedisKey.getSmsCodeKey(phone, SMSTypeEnum.REGISTER.getCode()), phone));
        if (!"dev".equals(currentEnvironment)) {
            if (!code.equals(redisCode)) return Result.fail("验证码不正确");
            redisBean.expire(redisCode, 1, TimeUnit.SECONDS);
        }
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            //第一登录 生成用户信息
            user = initUserInfo(phone);
            int loginCount = user.getLoginCount();
            user.setLoginCount(++loginCount);
            user.setLastLoginTime(new Date());
            User userFromDb = userRepository.saveAndFlush(user);

            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(userFromDb.getId());
            userAccountRepository.save(userAccount);

            UserExtends userExtends = new UserExtends();
            userExtends.setUserId(userFromDb.getId());
            userExtends.setSex(1);
            userExtendsRepository.save(userExtends);
        } else {
            int loginCount = user.getLoginCount();
            user.setLoginCount(++loginCount);
            user.setLastLoginTime(new Date());
            user = userRepository.saveAndFlush(user);
        }

        LoginResultVO vo = getLoginResult(user);
        return Result.success(vo);
    }

    /***
     * 处理登录token
     * @param user
     * @return
     */
    private LoginResultVO getLoginResult(User user) {
        //生成token
        String token = UUID.randomUUID().toString();

        String tokenUserIdKey = "";
        if (StringUtils.isNotEmpty(user.getPhone())) {
            tokenUserIdKey = RedisKey.getTokenUserIdKey(token);
        } else {
            tokenUserIdKey = RedisKey.getWeChatUserId(token);
        }
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 30, TimeUnit.DAYS);
        return LoginResultVO.convert(user, token);
    }

    /**
     * description: 初化一个用户，并返回这个用户对象
     */
    private User initUserInfo(String phone) {
        if (phone == null) return null;
        User user = new User();
        user.setAvatar("");
        user.setLoginCount(0);
        user.setPhone(phone);
        CharSequence charSequence = phone.subSequence(4, 8);
        user.setNickName(phone.replace(charSequence, "****"));
        user.setLastLoginTime(new Date());
//        GlobalProperty defaultHead = globalPropertyRepository.findByCode("defaultHead");

        String defaultHead = configService.getStringConfig("defaultHead");
        if (StringUtils.isBlank(defaultHead)) {
            logger.info("没有在参数配置中找到默认头像的配置");
            defaultHead = "";
        }
        user.setAvatar(defaultHead);
        return user;
    }


    public Result updatePersonInfo(Long userId, UserPersonInfoDTO personInfoDTO) {
        User user = userRepository.findOne(userId);
        if (user == null) return Result.fail("此用户不存在");
        UserExtends userExtends = userExtendsRepository.findByUserId(user.getId());
        String avatar = personInfoDTO.getAvatar();
        if (StringUtils.isNotBlank(avatar))
            user.setAvatar(avatar);

        String sex = personInfoDTO.getSex();

        if (StringUtils.isNotBlank(sex)) {

            if (sex.equals("1") || sex.equals("0")) {
                userExtends.setSex(Integer.valueOf(sex));
            }

            if (sex.equals("男")) {
                userExtends.setSex(1);
            }
            if (sex.equals("女")) {
                userExtends.setSex(0);
            }
        }

        String marriageStatus = personInfoDTO.getMarriageStatus();
        if (StringUtils.isNotBlank(marriageStatus))
            userExtends.setMarriageStatus(marriageStatus);

        String job = personInfoDTO.getJob();
        if (StringUtils.isNotBlank(job))
            userExtends.setJob(job);

        Date birthday = personInfoDTO.getBirthday();
        if (birthday != null)
            userExtends.setBirthday(birthday);

        String education = personInfoDTO.getEducation();
        if (StringUtils.isNotBlank(education))
            userExtends.setEducation(education);

        String nickName = personInfoDTO.getNickName();

        if (StringUtils.isNotBlank(nickName))
            user.setNickName(nickName);
        userRepository.save(user);
        userExtendsRepository.save(userExtends);
        return Result.success();
    }

    @Transactional
    public Result bindPhone(Long userId, String token, String code, String phone) {

        User user = getCurrentUser(userId);
        if (user == null) return Result.fail("此用户不存在");

        if (!StringUtils.isBlank(user.getPhone())) return Result.fail("您已绑定手机号码了");

        String key = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.BIND_MOBILE.getCode());
        String redisCode = redisBean.getStringValue(key);
        if (redisCode == null) return Result.fail("验证码已失效");

        if (!redisCode.equals(code)) return Result.fail("验证码错误");

//        Result verifyResult = smsService.verifySmsCodeByType(userId, SMSTypeEnum.BIND_MOBILE.getCode(), user.getPhone(), code);
//        if (verifyResult != null) return verifyResult;

        redisBean.setStringValue(key, "", 3, TimeUnit.MINUTES);

        User phoneUser = userRepository.findByPhone(phone);
        if (phoneUser != null) {
            if (StringUtils.isNotEmpty(phoneUser.getOpenId())) {
                return Result.fail("此手机已被其他用户绑定了");
            } else {
                //迁移资金积累
                UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
                BigDecimal balance = userAccount.getBalance();
                //增加双方流水
                if (balance.compareTo(new BigDecimal(0)) > 0) {
                    //变更微信account
                    commonService.setBalance(user.getId(), balance.abs().negate(), BalanceFlow.FlowType.ACCOUNT_TRANSFER, phoneUser.getId(), "", "");
                    commonService.setBalance(phoneUser.getId(), balance.abs(), BalanceFlow.FlowType.ACCOUNT_TRANSFER, phoneUser.getId(), "", "");
                    phoneUser.setOpenId(user.getOpenId());
                    phoneUser.setUnionId(user.getUnionId());

                    userRepository.save(phoneUser);
                }
                user.setStatus(User.StatusEnum.INVALID.getId());
                user.setNickName(phoneUser.getNickName());
                user.setAvatar(phoneUser.getAvatar());

                //信息转移
                UserExtends phoneUserExtends = userExtendsRepository.findByUserId(phoneUser.getId());
                UserExtends userExtends = userExtendsRepository.findByUserId(userId);

                BeanUtils.copyProperties(phoneUserExtends, userExtends,"userId");

                userExtendsRepository.save(userExtends);
                userRepository.save(user);
            }
        } else {
            user.setPhone(phone);
            userRepository.save(user);
        }
        redisBean.expire(key, 1, TimeUnit.SECONDS);
        String weChatUserId = RedisKey.getWeChatUserId(token);
        redisBean.expire(weChatUserId, 1, TimeUnit.MICROSECONDS);

        String tokenUserIdKey = RedisKey.getTokenUserIdKey(token);
        redisBean.expire(tokenUserIdKey, 30, TimeUnit.DAYS);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "");

        return Result.success();
    }


    public User getCurrentUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Value("${wechat.js.appid}")
    private String jsAppId;
    @Value("${wechat.js.secret}")
    private String jsSecret;
    @Resource
    private WeChatProvider weChatProvider;


    public Result loginByWechat(String code) {
        OpenIdCode openIdCode = weChatProvider.getOpenId(jsAppId, jsSecret, code);
        if (openIdCode == null && StringUtils.isEmpty(openIdCode.getOpenid())) {
            return Result.fail("微信登录失败");
        }
        String openId = openIdCode.getOpenid();
        User user = userRepository.findByOpenId(openId);

        if (user != null) {
            LoginResultVO loginResult = getLoginResult(user);
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLoginTime(user.getLastLoginTime());
            userRepository.save(user);
            return Result.success(loginResult);
        } else {
            WeChatUser userInfo = weChatProvider.getUserInfo(openIdCode);
            user = userInfo.convert();
            user.setLoginCount(1);
            user.setLastLoginTime(new Date());
            User userFromDb = userRepository.saveAndFlush(user);

            UserExtends userExtends = new UserExtends();
            userExtends.setUserId(userFromDb.getId());
            userExtends.setSex(userInfo.getSex());
            userExtendsRepository.save(userExtends);

            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(user.getId());
            userAccountRepository.save(userAccount);

            LoginResultVO loginResult = getLoginResult(user);
            return Result.success(loginResult);
        }

    }


    public Result findAllOptions(Long userId) {

        UserExtends userExtends = userExtendsRepository.findByUserId(userId);

//        GlobalProperty profileEmotion = globalPropertyRepository.findByCode("profileEmotion");
        Map profileEmotion = configService.getMapConfig("profileEmotion");
//        GlobalProperty profileEducation = globalPropertyRepository.findByCode("profileEducation");
        Map profileEducation = configService.getMapConfig("profileEducation");

//        GlobalProperty profileJob = globalPropertyRepository.findByCode("profileJob");
        Map profileJob = configService.getMapConfig("profileJob");


//        GlobalProperty profileSex = globalPropertyRepository.findByCode("profileSex");
        Map profileSex = configService.getMapConfig("profileSex");


        String educationCode = userExtends.getEducation();

        List<String> educationList = map2list(profileEducation, educationCode);

        String marriageStatus = userExtends.getMarriageStatus();
        List<String> emotionList = map2list(profileEmotion, marriageStatus);

        String jobCode = userExtends.getJob();
        List<String> jobList = map2list(profileJob, jobCode);

        Integer sex = userExtends.getSex();
        List<String> sexList = map2list(profileSex, sex + "");

        Map<String, Object> mapVo = new HashMap<>();
        mapVo.put("educationList", educationList);
        mapVo.put("emotionList", emotionList);
        mapVo.put("jobList", jobList);
        mapVo.put("sexList", sexList);
        User user = userRepository.findOne(userId);
        mapVo.put("avatar", user.getAvatar());
        mapVo.put("nickName", user.getNickName());
        mapVo.put("tel", user.getPhone());
        mapVo.put("birthday", DateUtil.formatDate(userExtends.getBirthday(), DateUtil.FormatType.DAY));

        return Result.success(mapVo);
    }

    private List<Map<String, String>> map2list(Map<String, String> map, String code) {

        List<Map<String, String>> list = new ArrayList<>();
        Set<String> ketSet = map.keySet();

        for (String key : ketSet) {
            Map<String, String> temp = new HashMap<>();
//            temp.put(key, map.get(key));
            temp.put("key", key);
            temp.put("value", map.get(key));
            if (key.equals(code)) {
                temp.put("select", "1");
            } else {
                temp.put("select", "0");
            }
            list.add(temp);
        }

        return list;
    }

    public Result questionHelp() {

        ArticleType articleType = articleTypeRepository.findByCode("questionHelp");
        List<Article> questHelp = articleRepository.findByType(articleType);

        if (questHelp == null) return Result.success();
        List<Map<String, String>> ListvO = new ArrayList<>();
        questHelp.forEach(article -> {
            Map<String, String> map = new HashMap<>();
            map.put("title", article.getTitle());
            map.put("articleId", article.getId() + "");
            ListvO.add(map);
        });
        return Result.success(ListvO);
    }


    public Result<WechatConfig> getConfig(String url) {
        return weChatProvider.getConfig(url);
    }


}
