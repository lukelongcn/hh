package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.model.vo.UserInfoVO;

import com.h9.api.provider.SMService;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.provider.model.OpenIdCode;
import com.h9.api.provider.model.WeChatUser;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.sun.javafx.text.ScriptMapper.INVALID;

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
    private SMService smService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private UserExtendsReposiroty userExtendsReposiroty;
    @Resource
    private CommonService commonService;


    @Resource
    private GlobalPropertyRepository globalPropertyRepository;

    private Logger logger = Logger.getLogger(this.getClass());

    public Result loginFromPhone(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        if (phone.length() > 11) return Result.fail("请输入正确的手机号码");
        String code = userLoginDTO.getCode();
        String redisCode = redisBean.getStringValue(String.format(RedisKey.getSmsCodeKey(phone, SMSTypeEnum.REGISTER.getCode()), phone));
        if (!"dev".equals(currentEnvironment)) {
            if (!code.equals(redisCode)) return Result.fail("验证码不正确");
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
            userExtendsReposiroty.save(userExtends);
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
        String tokenUserIdKey = StringUtils.isNotEmpty(user.getPhone())?RedisKey.getTokenUserIdKey(token):RedisKey.getWeChatUserId(token);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 30, TimeUnit.DAYS);
//        UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
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
        GlobalProperty defaultHead = globalPropertyRepository.findByCode("defaultHead");
        user.setAvatar(defaultHead.getVal());
        return user;
    }

    /**
     * description:
     *
     * @see com.h9.api.enums.SMSTypeEnum 短信类别
     */
    public Result sendSMS(String phone, int smsType) {

        SMSTypeEnum smstypeEnum = SMSTypeEnum.findByCode(smsType);
        if (smstypeEnum == null) return Result.fail("请传入正确的短信类别");

        if (smsType == SMSTypeEnum.REGISTER.getCode()) {

            return registerSMS(phone);

        } else {
            String code = RandomStringUtils.random(4, "0123456789");
            String content = "您的校验码是：" + code + "。请不要把校验码泄露给其他人。如非本人操作，可不用理会！";

            if ("dev".equals(currentEnvironment)) {
                code = "0000";
            } else {
                smService.sendSMS(phone, content);
            }

            String key = RedisKey.getSmsCodeKey(phone, smsType);
            redisBean.setStringValue(key, code);

            return Result.success("发送成功");
        }

    }

    private Result registerSMS(String phone) {
        //短信限制 一分钟一次lastSendKey
        String lastSendKey = String.format(RedisKey.getLastSendKey(phone), phone);
        String lastSendValue = redisBean.getStringValue(lastSendKey);
        if (!StringUtils.isBlank(lastSendValue)) {
            return new Result(1, "系统繁忙,请稍后再试~");
        }
        //短信限制 一天最多十次
        String countKey = String.format(RedisKey.getSendCountKey(phone), phone);
        String countStr = redisBean.getStringValue(countKey);
        int count = 0;
        if (countStr != null) {
            try {
                count = Integer.valueOf(countStr);
                if (count > 9) return new Result(1, "系统繁忙,请稍后再试~");
            } catch (NumberFormatException e) {
                logger.info(e.getMessage(), e);
                redisBean.setStringValue(countKey, 0 + "", 1, TimeUnit.DAYS);
            }
        } else {
            redisBean.setStringValue(countKey, 0 + "", 1, TimeUnit.DAYS);
        }

        logger.info("今天已发送次: " + count);
        String code = RandomStringUtils.random(4, "0123456789");
        String content = "您的校验码是：" + code + "。请不要把校验码泄露给其他人。如非本人操作，可不用理会！";
        Result returnMsg;
        if ("dev".equals(currentEnvironment)) {
            returnMsg = new Result(0, "");
            code = "0000";
        } else {
            returnMsg = smService.sendSMS(phone, content);
        }
        //处理结果
        if (returnMsg != null && returnMsg.getCode() != 0) {
            //失败
            SMSLog smsLog = new SMSLog(content, phone, false);
            smsLogReposiroty.save(smsLog);
            return new Result(1, "请稍后再试");
        } else {
            //成功
            SMSLog smsLog = new SMSLog(content, phone, true);
            smsLogReposiroty.save(smsLog);
            redisBean.setStringValue(lastSendKey, System.currentTimeMillis() + "", 60, TimeUnit.SECONDS);
            //发送次数加1,1天超时
            redisBean.setStringValue(countKey, ((++count)) + "", 1, TimeUnit.DAYS);
            String codeKey = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.REGISTER.getCode());
            logger.info("用户:" + phone + " code:" + code);
            redisBean.setStringValue(codeKey, code, 10, TimeUnit.MINUTES);
            return new Result(0, "短信发送成功");
        }
    }


    public Result updatePersonInfo(Long userId, UserPersonInfoDTO personInfoDTO) {
        User user = userRepository.findOne(userId);
        if (user == null) return Result.fail("此用户不存在");
        UserExtends userExtends = userExtendsReposiroty.findByUserId(user.getId());
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
        userExtendsReposiroty.save(userExtends);
        return Result.success();
    }

    @Transactional
    public Result bindPhone(Long userId,String token, String code, String phone) {

        User user = getCurrentUser(userId);
        if (user == null) return Result.fail("此用户不存在");

        if (!StringUtils.isBlank(user.getPhone())) return Result.fail("您已绑定手机号码了");
        String key = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.BIND_MOBILE.getCode());

        String redisCode = redisBean.getStringValue(key);
        if (redisCode == null) return Result.fail("验证码已失效");
        if (!redisCode.equals(code)) return Result.fail("验证码错误");
        redisBean.setStringValue(key, "", 1, TimeUnit.SECONDS);

        User phoneUser = userRepository.findByPhone(phone);
        if(phoneUser != null){
            if(StringUtils.isNotEmpty(phoneUser.getOpenId())){
                return Result.fail("此手机已被其他用户绑定了");
            }else{
                //迁移资金积累
                UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
                BigDecimal balance = userAccount.getBalance();
                //增加双方流水
                if(balance.compareTo(new BigDecimal(0))>0){
                    //变更微信account
                   commonService.setBalance(user.getId(), balance.abs().negate(), 1l, phoneUser.getId(), "", "");
                   commonService.setBalance(phoneUser.getId(),balance.abs(),1l,phoneUser.getId(),"","");
                   phoneUser.setOpenId(user.getOpenId());
                   phoneUser.setUnionId(user.getUnionId());
                }
                user.setStatus(User.StatusEnum.INVALID.getId());
                userRepository.save(user);
            }
        }else{
            user.setPhone(phone);
            userRepository.save(user);
            String tokenUserIdKey = RedisKey.getTokenUserIdKey(token);
            redisBean.setStringValue(tokenUserIdKey,user.getId()+"");
            redisBean.expire(tokenUserIdKey, 30, TimeUnit.DAYS);
        }
        return Result.success();
    }


    public Result getUserInfo(Long userId) {
        User user = userRepository.findOne(userId);
        UserExtends userExtends = userExtendsReposiroty.findByUserId(user.getId());
        UserInfoVO vo = UserInfoVO.convert(user, userExtends);
        return Result.success(vo);
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
            userExtendsReposiroty.save(userExtends);

            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(user.getId());
            userAccountRepository.save(userAccount);

            LoginResultVO loginResult = getLoginResult(user);
            return Result.success(loginResult);
        }

    }


    public Result findAllOptions(Long userId) {

        UserExtends userExtends = userExtendsReposiroty.findByUserId(userId);
        GlobalProperty profileEmotion = globalPropertyRepository.findByCode("profileEmotion");
        GlobalProperty profileEducation = globalPropertyRepository.findByCode("profileEducation");
        GlobalProperty profileJob = globalPropertyRepository.findByCode("profileJob");
        GlobalProperty profileSex = globalPropertyRepository.findByCode("profileSex");


        String educationCode = userExtends.getEducation();
        List<String> educationList = map2list(JSONObject.parseObject(profileEducation.getVal(), Map.class),educationCode);

        String marriageStatus = userExtends.getMarriageStatus();
        List<String> emotionList = map2list(JSONObject.parseObject(profileEmotion.getVal(), Map.class),marriageStatus);

        String jobCode = userExtends.getJob();
        List<String> jobList = map2list(JSONObject.parseObject(profileJob.getVal(), Map.class),jobCode);

        Integer sex = userExtends.getSex();
        List<String> sexList = map2list(JSONObject.parseObject(profileSex.getVal(), Map.class),sex+"");
        Map<String, Object> mapVo = new HashMap<>();
        mapVo.put("educationList", educationList);
        mapVo.put("emotionList", emotionList);
        mapVo.put("jobList", jobList);
        mapVo.put("sexList", sexList);

        return Result.success(mapVo);
    }

    private  List<Map<String,String>> map2list(Map<String, String> map,String code) {

        List<Map<String,String>> list = new ArrayList<>();
        Set<String> ketSet = map.keySet();

        for (String key : ketSet) {
            Map<String, String> temp = new HashMap<>();
//            temp.put(key, map.get(key));
            temp.put("key", key);
            temp.put("value", map.get(key));
            if (key.equals(code)) {
                temp.put("select", "1");
            }else{
                temp.put("select", "0");
            }
            list.add(temp);
        }

        return list;
    }
}
