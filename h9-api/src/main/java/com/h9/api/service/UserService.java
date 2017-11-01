package com.h9.api.service;

import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.handle.UnAuthException;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.model.vo.UserInfoVO;

import com.h9.api.provider.SMService;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.provider.model.OpenIdCode;
import com.h9.api.provider.model.WeChatUser;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.*;
import com.h9.common.db.repo.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.MessageFormat;
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
    private SMService smService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SMSLogReposiroty smsLogReposiroty;
    @Resource
    private UserAccountReposiroty userAccountReposiroty;
    @Resource
    private UserExtendsReposiroty userExtendsReposiroty;


    private Logger logger = Logger.getLogger(this.getClass());

    public Result loginFromPhone(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();
        if (phone.length() > 11) return Result.fail("请输入正确的手机号码");
        String code = userLoginDTO.getCode();
        String redisCode = redisBean.getStringValue(String.format(RedisKey.getSmsCodeKey(phone), phone));
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
            userAccountReposiroty.save(userAccount);

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

    private LoginResultVO getLoginResult( User user) {
        //生成token
        String token = UUID.randomUUID().toString();
        String tokenUserIdKey = RedisKey.getTokenUserIdKey(token);
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
        user.setNickName(phone.replace(charSequence,"****"));
        user.setLastLoginTime(new Date());
        return user;
    }

    /**
     * description:
     *
     * @see com.h9.api.enums.SMSTypeEnum 短信类别
     */
    public Result sendSMS(String phone, int smsType) {

        if (smsType == SMSTypeEnum.BIND_MOBILE.getCode()) {

            String code = RandomStringUtils.random(4, "0123456789");
            String content = "您的校验码是：" + code + "。请不要把校验码泄露给其他人。如非本人操作，可不用理会！";

            smService.sendSMS(phone, content);
            String key = RedisKey.getSmsCodeKey(phone);
            redisBean.setStringValue(key, code);

            return Result.success("发送成功");
        } else {
            return registerSMS(phone);
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
            String codeKey = String.format(RedisKey.getSmsCodeKey(phone), phone);
            logger.info("用户:" + phone + " code:" + code);
            redisBean.setStringValue(codeKey, code, 10, TimeUnit.MINUTES);
            return new Result(0, "短信发送成功");
        }
    }


    public Result updatePersonInfo(UserPersonInfoDTO personInfoDTO) {

        User user = getCurrentUser();
        if (user == null) return Result.fail("此用户不存在");
        UserExtends userExtends = userExtendsReposiroty.findByUserId(user.getId());
        String avatar = personInfoDTO.getAvatar();
        if (!StringUtils.isBlank(avatar))
            user.setAvatar(avatar);

        Integer sex = personInfoDTO.getSex();
        if (sex != null)
            userExtends.setSex(sex);

        Integer marriageStatus = personInfoDTO.getMarriageStatus();
        if (marriageStatus != null)
            userExtends.setMarriageStatus(marriageStatus);

        String job = personInfoDTO.getJob();
        if (job != null)
            userExtends.setJob(job);

        Date birthday = personInfoDTO.getBirthday();
        if (birthday != null)
            userExtends.setBirthday(birthday);

        Integer education = personInfoDTO.getEducation();
        if (education != null)
            userExtends.setEducation(education);

        userRepository.save(user);
        userExtendsReposiroty.save(userExtends);
        return Result.success();
    }

    public Result bindPhone(String code, String phone) {

        User user = getCurrentUser();
        if (user == null) return Result.fail("此用户不存在");

        if (!StringUtils.isBlank(user.getPhone())) return Result.fail("您已绑定手机号码了");
        String key = RedisKey.getSmsCodeKey(phone);

        String redisCode = redisBean.getStringValue(key);
        if (redisCode == null) return Result.fail("验证码错误");

        if (!redisCode.equals(code)) return Result.fail("验证码错误");

        user.setPhone(phone);
        userRepository.save(user);
        return Result.success();
    }





    public Result getUserInfo() {
        User user = getCurrentUser();
        UserExtends userExtends = userExtendsReposiroty.findByUserId(user.getId());
        UserInfoVO vo = UserInfoVO.convert(user, userExtends);
        return Result.success(vo);
    }


    private User getCurrentUser() {
        String userIdStr = MDC.get("userId");
        try {
            Long userId = Long.valueOf(userIdStr);
            User user = userRepository.findOne(userId);
            if (user == null) throw new UnAuthException("用户不存在");
            return user;
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(), e);
            throw new UnAuthException("用户不存在");
        }
    }

    @Value("${wechat.js.appid}")
    private String jsAppId;
    @Value("${wechat.js.secret}")
    private String jsSecret;
    @Value("${common.code.url}")
    private String commonCodeUrl;
    @Resource
    private WeChatProvider weChatProvider;

    public String getCode(String url){
        byte[] urlByte = Base64.getEncoder().encode(url.getBytes());
        return MessageFormat.format(commonCodeUrl, jsAppId, new String(urlByte));
    }

    public Result getOpenId(String code){
        OpenIdCode openIdCode = weChatProvider.getOpenId(jsAppId, jsSecret, code);
        if(openIdCode == null&&StringUtils.isEmpty(openIdCode.getOpenid())){
            return Result.fail("微信登录失败");
        }
        String openId = openIdCode.getOpenid();
        User user = userRepository.findByOpenId(openId);

        if (user != null) {
            LoginResultVO loginResult = getLoginResult(user);
            user.setLoginCount(user.getLoginCount()+1);
            user.setLastLoginTime(user.getLastLoginTime());
            userRepository.save(user);
            return Result.success(loginResult);
        }else{
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
            userAccountReposiroty.save(userAccount);

            LoginResultVO loginResult = getLoginResult(user);
            return Result.success(loginResult);
        }

    }





}
