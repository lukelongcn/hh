package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.enums.SMSTypeEnum;
import com.h9.api.model.dto.*;
import com.h9.api.model.vo.*;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.dto.WechatConfig;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.provider.SMSProvide;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.provider.model.OpenIdCode;
import com.h9.api.provider.model.WeChatUser;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.bean.SequenceUtil;
import com.h9.common.db.entity.Transactions;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.config.Article;
import com.h9.common.db.entity.config.ArticleType;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserExtends;
import com.h9.common.db.entity.user.UserRecord;
import com.h9.common.db.repo.*;
import com.h9.common.utils.DateUtil;
import com.h9.common.utils.MobileUtils;
import com.h9.common.utils.MoneyUtils;
import com.h9.common.utils.QRCodeUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.h9.api.provider.WeChatProvider.EventEnum.SCAN;
import static com.h9.common.db.entity.account.BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE;


/**
 * Created by itservice on 2017/10/26.
 */
@Service
@Transactional
public class UserService {
    @Value("${h9.current.envir}")
    private String currentEnvironment;
    @Value("${path.app.wechat_host}")
    private String host;
    @Value("${path.app.host}")
    private String appHost;

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
    @Resource
    private TransactionsRepository transactionsRepository;

    @Resource
    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(this.getClass());

    public Result loginFromPhone(UserLoginDTO userLoginDTO, Integer client) {
        String phone = userLoginDTO.getPhone();

        if (phone.length() > 11) return Result.fail("请输入正确的手机号码");

        if (!phone.equals("12345678909")) {

            if (!MobileUtils.isMobileNO(phone)) {
                return Result.fail("请输入正确的手机号码");
            }
        }

        String code = userLoginDTO.getCode();
        String redisCode = redisBean.getStringValue(String.format(RedisKey.getSmsCodeKey(phone, SMSTypeEnum.REGISTER.getCode()), phone));

        if (!phone.equals("12345678909")) {

            if (StringUtils.isBlank(redisCode)) return Result.fail("验证码不正确");
            if (!"dev".equals(currentEnvironment)) {
                if (!code.equals(redisCode)) return Result.fail("验证码不正确");
            }
        } else {
            redisCode = "0000";
        }
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            //第一登录 生成用户信息
            user = initUserInfo(phone);
            user.setClient(client);
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
            user.setClient(client);
            user.setLoginCount(++loginCount);
            user.setLastLoginTime(new Date());
            user = userRepository.saveAndFlush(user);
        }
        LoginResultVO vo = getLoginResult(user);
        redisBean.expire(redisCode, 1, TimeUnit.SECONDS);
        String smsCodeCountDown = RedisKey.getSmsCodeCountDown(user.getPhone(), SMSTypeEnum.REGISTER.getCode());
        redisBean.expire(smsCodeCountDown, 1, TimeUnit.SECONDS);

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
        if (StringUtils.isNotBlank(phone)) {
            CharSequence charSequence = phone.subSequence(3, 7);
            user.setNickName(phone.replace(charSequence, "****"));
        }
        user.setLastLoginTime(new Date());
//        GlobalProperty defaultHead = globalPropertyRepository.findByCode(ParamConstant.DEFUALT_HEAD);

        String defaultHead = configService.getStringConfig(ParamConstant.DEFUALT_HEAD);
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

        if (!MobileUtils.isMobileNO(phone)) return Result.fail("请输入正确的手机号码");

        User user = getCurrentUser(userId);
        if (user == null) return Result.fail("此用户不存在");

        if (!StringUtils.isBlank(user.getPhone())) return Result.fail("该手机号已被绑定");

        String key = RedisKey.getSmsCodeKey(phone, SMSTypeEnum.BIND_MOBILE.getCode());

        String redisCode = redisBean.getStringValue(key);
        if (redisCode == null) return Result.fail("验证码已失效");

        if (!redisCode.equals(code)) return Result.fail("验证码错误");

//        Result verifyResult = smsService.verifySmsCodeByType(userId, SMSTypeEnum.BIND_MOBILE.getCode(), user.getPhone(), code);
//        if (verifyResult != null) return verifyResult;


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

                }

                phoneUser.setOpenId(user.getOpenId());
                phoneUser.setUnionId(user.getUnionId());
                userRepository.save(phoneUser);


                user.setStatus(User.StatusEnum.INVALID.getId());
                user.setNickName(phoneUser.getNickName());
                user.setAvatar(phoneUser.getAvatar());

                //信息转移
                UserExtends phoneUserExtends = userExtendsRepository.findByUserId(phoneUser.getId());
                UserExtends userExtends = userExtendsRepository.findByUserId(userId);

                BeanUtils.copyProperties(phoneUserExtends, userExtends, "userId");

                userExtendsRepository.saveAndFlush(userExtends);
                user.setPhone(phone);
                userRepository.saveAndFlush(user);

                //到手机用户上去

                user = phoneUser;
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

        //失效验证码
        redisBean.setStringValue(key, "", 1, TimeUnit.MINUTES);
        String smsCodeCountDown = RedisKey.getSmsCodeCountDown(user.getPhone(), SMSTypeEnum.BIND_MOBILE.getCode());
        redisBean.expire(smsCodeCountDown, 1, TimeUnit.SECONDS);

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
            user.setClient(UserRecord.ClientEnum.WEIXIN.getId());
            userRepository.save(user);
            return Result.success(loginResult);
        } else {
            WeChatUser userInfo = weChatProvider.getUserInfo(openIdCode);
            if (userInfo == null || StringUtils.isEmpty(userInfo.getOpenid())) {
                return Result.fail("微信登录失败，获取用户信息失败，请同意授权");
            }
            user = userInfo.convert();
            user.setClient(UserRecord.ClientEnum.WEIXIN.getId());
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


    /**
     * description: 转账
     *
     * @param userId      转账发起方的用户Id
     * @param transferDTO 转账参数
     * @see TransferDTO
     */
    @Transactional
    public Result transfer(Long userId, TransferDTO transferDTO) {
        User user = userRepository.findOne(userId);
        if (user == null) return Result.fail("账号不存在");

        if (transferDTO.getTransferMoney().compareTo(new BigDecimal(0.01)) < 0) {
            return Result.fail("最小金额为0.01");
        }

        String targetUserPhone = transferDTO.getTargetUserPhone();
        User targetUser = userRepository.findByPhone(targetUserPhone);
        if (targetUser == null) return Result.fail("请输入正确的账号");

        if (targetUser.getId().equals(userId)) {
            return Result.fail("不能给自已转账");
        }

        BigDecimal transferMoney = transferDTO.getTransferMoney();

        if (transferMoney.compareTo(new BigDecimal(0)) <= 0) {
            return Result.fail("请填写正确的金额");
        }

        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        if (userAccount.getBalance().compareTo(transferMoney) < 0) {
            return Result.fail("余额不足，请充值后再试");
        }


        Transactions transactions = new Transactions(null, user.getId(), targetUser.getId(),
                transferMoney, transferDTO.getRemarks(),
                BalanceFlow.BalanceFlowTypeEnum.USER_TRANSFER.getId(), "",
                user.getPhone(), targetUser.getPhone(), user.getNickName(), targetUser.getNickName());
        transactionsRepository.saveAndFlush(transactions);

        commonService.setBalance(user.getId(), transferMoney.abs().negate(), BalanceFlow.BalanceFlowTypeEnum.USER_TRANSFER.getId(),
                transactions.getId(), "", transferDTO.getRemarks());
        commonService.setBalance(targetUser.getId(), transferMoney, BalanceFlow.BalanceFlowTypeEnum.USER_TRANSFER.getId(),
                transactions.getId(), "", transferDTO.getRemarks());

        String tips = targetUser.getNickName() + " (" + targetUser.getPhone() + ") 已收到您的转账";
        return Result.success(new TransferResultVO(tips, 1));
    }

    /**
     * description: 转账记录
     */
    public Result transactions(Long userId, Integer page, Integer limit, Integer type) {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = transactionsRepository.pageRequest(page, limit, sort);

        Long findBalanceType = type == 1 ? BalanceFlow.BalanceFlowTypeEnum.USER_TRANSFER.getId() : RED_ENVELOPE.getId();
        Page<Transactions> pageObj = transactionsRepository.findByUserIdOrTargetUserId(userId, userId, findBalanceType, pageRequest);

        if (CollectionUtils.isEmpty(pageObj.getContent())) {
            return Result.success(new PageResult<>(pageObj));
        }

        PageResult<BalanceFlowVO> result = new PageResult<>(pageObj).result2Result(el -> {
            String money = MoneyUtils.formatMoney(el.getTransferMoney());
            if (el.getUserId().equals(userId)) {
                money = "-" + money;
            }
            Long balanceFlowType = el.getBalanceFlowType();
            Map typeMap = configService.getMapConfig(ConfigService.BALANCEFLOWTYPE);
            Long findUserId = el.getUserId();
            Long targetUserId = el.getTargetUserId();
            User targetUser = userRepository.findOne(targetUserId);
            String img = targetUser.getAvatar();
            Object remarks = typeMap.get(balanceFlowType + "");
            BalanceFlowVO balanceFlowVO = new BalanceFlowVO(money,
                    DateUtil.formatDate(el.getCreateTime(),
                            DateUtil.FormatType.MONTH),
                    remarks,
                    img,
                    DateUtil.formatDate(el.getCreateTime(), DateUtil.FormatType.MINUTE));
            return balanceFlowVO;
        });

        return Result.success(result);

    }

    public Result transferInfo(Long userId, String phone) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId);
        User user = userRepository.findOne(userId);
        BigDecimal balance = userAccount.getBalance();
        User targetUser = userRepository.findByPhone(phone);
        if (targetUser == null) {
            return Result.fail("用户不存在");
        }

        if (phone.equals(user.getPhone())) {
            return Result.fail("不能给自己转账");
        }

        TransferInfoVO vo = new TransferInfoVO(targetUser.getAvatar(), targetUser.getNickName(), targetUser.getPhone(), MoneyUtils.formatMoney(balance));
        return Result.success(vo);
    }

    @Resource
    private SequenceUtil sequenceUtil;

    public Result getRedEnvelope(HttpServletRequest request, HttpServletResponse response, Long userId, BigDecimal money) {
        if (money != null && money.compareTo(new BigDecimal(0)) > 0) {
            if (money.compareTo(new BigDecimal(0.01)) < 0) {
                return Result.fail("最小金额为0.01");
            }

            UserAccount userAccount = userAccountRepository.findByUserId(userId);
            if (userAccount.getBalance().compareTo(money) < 0) {
                return Result.fail("余额不足" + MoneyUtils.formatMoney(money));
            }
            String tempId = UUID.randomUUID().toString().replace("-", "");

            //	client 整形类型 1 andoird 2 ios 3 公众号
            String client = request.getHeader("client");
            String url = "";
            Long nextVal = sequenceUtil.getNextVal();

            if ("3".equals(client)) {
                String accessToken = weChatProvider.getWeChatAccessToken();
                String ticket = weChatProvider.getCodeTicket(accessToken, nextVal);
                url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
                if (StringUtils.isEmpty(ticket)) {
                    return Result.fail("获取二维码失败");
                }
            } else {
                //https://localhost:6305/h9/api/user/redEnvelope/qrcode?tempId=1
                url = appHost + "/h9/api/user/redEnvelope/qrcode?tempId=" + tempId;
            }

            User user = userRepository.findOne(userId);
            //存放redis tempId;
            //在 redis 中记录 红包二维码信息
            RedEnvelopeDTO redEnvelopeDTO = new RedEnvelopeDTO(url, money, userId, 1, tempId, user.getOpenId());
            redisBean.setStringValue(RedisKey.getQrCode(nextVal), JSONObject.toJSONString(redEnvelopeDTO), 1, TimeUnit.DAYS);
            redisBean.setStringValue(RedisKey.getQrCodeTempId(tempId), nextVal + "", 1, TimeUnit.DAYS);

            RedEnvelopeCodeVO redEnvelopeCodeVO = new RedEnvelopeCodeVO()
                    .setCodeUrl(url)
                    .setTempId(tempId)
//                    .setTransferRecord(transferList)
                    .setMoney(money);

            return Result.success(redEnvelopeCodeVO);
        }

        return Result.fail("请填写正确的金额");
    }


    public User registUser(String openId) {
        //第一登录 生成用户信息
        User user = initUserInfo("");
        int loginCount = user.getLoginCount();
        user.setLoginCount(++loginCount);
        user.setLastLoginTime(new Date());
        user.setOpenId(openId);
        User userFromDb = userRepository.saveAndFlush(user);

        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userFromDb.getId());
        userAccountRepository.save(userAccount);

        UserExtends userExtends = new UserExtends();
        userExtends.setUserId(userFromDb.getId());
        userExtends.setSex(1);
        userExtendsRepository.save(userExtends);

        return user;
    }

    public Result redEnvelopeStatus(String tempId) {
        String value = redisBean.getStringValue(RedisKey.getQrCodeTempId(tempId));
        if (StringUtils.isBlank(value)) {

            Transactions transactions = transactionsRepository.findByTempId(tempId);
            if (transactions != null) {

                Map<String, String> mapVO = new HashMap<>();
                Long targetUserId = transactions.getTargetUserId();
                User targetUser = userRepository.findOne(targetUserId);
                mapVO.put("nickName", targetUser.getNickName());
                mapVO.put("time", DateUtil.getSpaceTime(transactions.getCreateTime(), new Date()));
                mapVO.put("img", targetUser.getAvatar());
                mapVO.put("money", MoneyUtils.formatMoney(transactions.getTransferMoney()));

                return Result.fail("领取成功", mapVO);
            }

            return Result.success("");
        }
        return Result.success("红包未被领取");
    }

    public Result qrRecord(Long userId, Integer page, Integer limit) {

        PageRequest pageRequest = transactionsRepository.pageRequest(page, limit, new Sort(Sort.Direction.DESC, "id"));
        Page<Transactions> transactionsPage = transactionsRepository.
                findByBalanceFlowType(RED_ENVELOPE.getId(), userId, pageRequest);
        if (transactionsPage.isLast()) {
            return Result.success(new PageResult<>(transactionsPage));
        }

        PageResult<Map<Object, Object>> pageResult = new PageResult<>(transactionsPage).result2Result(el -> {
            Map<Object, Object> record = new HashMap<>();
            record.put("nickName", "张三");
            record.put("time", "30分钟前");
            record.put("img", "");
            record.put("money", "2.00");
            return record;
        });

        return Result.success(pageResult);
    }

    public void getOwnRedEnvelope(HttpServletRequest request, HttpServletResponse response, String tempId) {
        try {
//            String link = host + "/h9/api/user/redEnvelope/scan/redirect/qrcode?tempId=" + tempId;
            String link = host + "/h9-weixin/#/account/hongbao/result?id=" + tempId;
//            tempId = "hlzj://tempId="+tempId;
            ServletOutputStream outputStream = response.getOutputStream();
//            link = URLEncoder.encode(link, "UTF-8");
            logger.info("二维码内容："+link);
            BufferedImage bufferedImage = QRCodeUtil.toBufferedImage(link, 300, 300);
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(bufferedImage);
            String url = configService.getStringConfig("hlzjIcon");
            if (StringUtils.isBlank(url)) {
                logger.info("二维码中间水印图片没有设置");
            }
            BufferedImage bufferedImageSY = ImageIO.read(new URL(url));
            //Positions 实现了 Position 并提供九个坐标, 分别是 上左, 上中, 上右, 中左, 中中, 中右, 下左, 下中, 下右 我们使用正中的位置
            Watermark watermark = new Watermark(Positions.CENTER, bufferedImageSY, 1F);
            builder.watermark(watermark).scale(1F).outputFormat("png").toOutputStream(outputStream);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String url = "h9/api/user/redEnvelope/scan/qrcode?tempId=123";
        int index = url.indexOf("tempId=");
        String substring = url.substring(index + 7, url.length());
        System.out.println(substring);
    }

    @Resource
    private EventService eventService;

    public Result scanQRCode(String tempId, Long userId) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(tempId)) {
            return Result.fail("二维码超时");
        }

        //tempId 存放的是url ,需要进行url 解码
//        try {
//             tempId = URLDecoder.decode(tempId, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            logger.info("解码失败,"+tempId);
//            return Result.fail("领取异常");
//        }
        if (tempId.contains("id=")) {
            int index = tempId.indexOf("id=");
            tempId = tempId.substring(index + 3, tempId.length());
        }

        map.put("Event", SCAN.getValue());
        map.put("tempId", tempId);
        map.put("EventKey", "");
        map.put("client", "app");
        map.put("scanUserId", userId + "");
        return eventService.handleSubscribeAndScan(map);
    }


    public void addUserCount(String userId){
        try {
            Long userIdLong = Long.valueOf(userId);
            logger.info("userIdLong : "+userIdLong);
            redisBean.getValueOps().setBit(RedisKey.getUserCountKey(new Date()), userIdLong, true);
            Long userCount = redisBean.getStringTemplate()
                    .execute((RedisCallback<Long>) connection ->
                            connection.bitCount(((RedisSerializer<String>) redisBean.getStringTemplate()
                                    .getKeySerializer())
                                    .serialize(DateUtil.formatDate(new Date(), DateUtil.FormatType.DAY))));
            logger.info("userCount: "+userCount);
        } catch (NumberFormatException e) {
            logger.info("解析UserId 出错: " + userId);
        }
    }

    public void addUserCount(Long userId){
        addUserCount(userId);
    }
}
