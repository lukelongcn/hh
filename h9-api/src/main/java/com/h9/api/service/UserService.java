package com.h9.api.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.provider.SMService;
import com.h9.api.util.UserUtil;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.SMSLog;
import com.h9.common.db.entity.User;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.db.repo.SMSLogReposiroty;
import com.h9.common.db.repo.UserAccountReposiroty;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.MD5Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    private Logger logger = Logger.getLogger(this.getClass());

    public Result loginFromPhone(UserLoginDTO userLoginDTO) {
        String phone = userLoginDTO.getPhone();

        if (phone.length() > 11) return Result.fail("请输入正确的手机号码");

        String code = userLoginDTO.getCode();

        String redisCode = redisBean.getStringValue(String.format(RedisKey.getSmsCodeKey(phone), phone));
        if ("dev".equals(currentEnvironment)) {

        } else {
            if (!code.equals(redisCode)) return Result.fail("验证码不正确");
        }

        List<User> userList = userRepository.findByPhone(phone);
        User user = null;
        if (CollectionUtils.isEmpty(userList)) {
            //第一登录 生成用户信息
            user = initUserInfo(phone);
            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(user.getId());
            userAccountReposiroty.save(userAccount);
        } else {
            user = userList.get(0);
        }

        int loginCount = user.getLoginCount();
        user.setLoginCount(++loginCount);
        user = userRepository.saveAndFlush(user);

        //生成token
        String token = UUID.randomUUID().toString();
        String tokenKey = RedisKey.getTokenKey(phone);
        redisBean.setStringValue(tokenKey, token, 7, TimeUnit.DAYS);
        String tokenUserIdKey = RedisKey.getTokenUserIdKey(token);
        redisBean.setStringValue(tokenUserIdKey, user.getId() + "", 7, TimeUnit.DAYS);

        LoginResultVO vo = LoginResultVO.convert(user, token);
        return Result.success(vo);
    }

    /**
     * description: 初化一个用户，并返回这个用户对象
     */
    public User initUserInfo(String phone) {
        if (phone == null) return null;
        User user = new User();
        user.setAvatar("");
        user.setLoginCount(0);
        user.setPhone(phone);
        user.setNickName("");
        user.setLastLoginTime(new Date());
        user.setSex(1);
        return user;
    }

    public Result smsRegister(String phone) {

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
        Result returnMsg = null;
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
        Long userId = UserUtil.getCurrentUserId();
        User user = userRepository.findOne(userId);
        if (user == null) return Result.fail("此用户不存在");

        String avatar = personInfoDTO.getAvatar();
        if (!StringUtils.isBlank(avatar))
            user.setAvatar(avatar);

        Integer sex = personInfoDTO.getSex();
        if (sex != null)
            user.setSex(sex);

        Integer marriageStatus = personInfoDTO.getMarriageStatus();
        if (marriageStatus != null)
            user.setMarriageStatus(marriageStatus);

        String job = personInfoDTO.getJob();
        if (job != null)
            user.setJob(job);

        Date birthday = personInfoDTO.getBirthday();
        if (birthday != null)
            user.setBirthday(birthday);

        Integer education = personInfoDTO.getEducation();
        if (education != null)
            user.setEducation(education);

        userRepository.save(user);
        return Result.success();
    }

}
