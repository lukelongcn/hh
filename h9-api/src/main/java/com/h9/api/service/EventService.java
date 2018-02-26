package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.RedEnvelopeDTO;
import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.api.model.vo.ScanRedEnvelopeVO;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.wxEvent.EventHandlerStrategy;
import com.h9.api.service.wxEvent.EventHandlerStrategyFactory;
import com.h9.api.service.wxEvent.model.Message4wxText;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.Transactions;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.repo.TransactionsRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.CheckoutUtil;
import com.h9.common.utils.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by itservice on 2018/1/25.
 */
@Service
public class EventService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private RedisBean redisBean;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserService userService;
    @Resource
    private CommonService commonService;
    @Resource
    private TransactionsRepository transactionsRepository;
    @Resource
    private WeChatProvider weChatProvider;


    public String handle(VerifyTokenDTO verifyTokenDTO) {
        String signature = verifyTokenDTO.getSignature();
        String timestamp = verifyTokenDTO.getTimestamp();
        String nonce = verifyTokenDTO.getNonce();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("验证成功");
            return verifyTokenDTO.getEchostr();
        } else {
            logger.info("验证失败");
            return "验证失败";
        }
    }
//
//    public String wxEventCallBack(Map<String, String> map) {
//
////        String event = map.get("Event");
////        if (WeChatProvider.EventEnum.SUBSCRIBE.getValue().equals(event)) {
////            handleSubscribeAndScan(map);
////        } else if (WeChatProvider.EventEnum.SCAN.getValue().equals(event)) {
////            handleSubscribeAndScan(map);
////        }
////        eventContext.handler(map);
//
////        handler(map);
//        return "";
//    }


    @Transactional
    public Result handleSubscribeAndScan(Map<String, String> map) {

        String eventKey = map.get("EventKey");

        if (eventKey.contains("qrscene_")) {
            eventKey = eventKey.replace("qrscene_", "");
        }


        //发送方帐号（一个OpenID）
        String openId = map.get("FromUserName");
        User user = null;

        String codeJson = null;
        String client = map.get("client");
        if ("app".equals(client)) {
            eventKey = map.get("tempId");
            String sequenceId = redisBean.getStringValue(RedisKey.getQrCodeTempId(eventKey));
            if (StringUtils.isBlank(sequenceId)) {
                logger.info("tempId 为空，二维码失效或者已被领取");
                return Result.fail("谢谢惠顾");
            }
            codeJson = redisBean.getStringValue(RedisKey.getQrCode(sequenceId));
            String userIdStr = map.get("scanUserId");
            Long sacnUserId = Long.valueOf(userIdStr);
            user = userRepository.findOne(sacnUserId);
        } else {
            user = userRepository.findByOpenId(openId);
            codeJson = redisBean.getStringValue(RedisKey.getQrCode(eventKey));
        }

        if (StringUtils.isBlank(codeJson)) {
            logger.info("codeJson 为空: " + codeJson);
            return Result.fail("谢谢惠顾");
        }
        if (user == null) {
            // 注册用户
            user = userService.registUser(openId);
        }

        RedEnvelopeDTO redEnvelopeDTO = JSONObject.parseObject(codeJson, RedEnvelopeDTO.class);

        logger.info("redEnvelopeDTO: " + JSONObject.toJSONString(redEnvelopeDTO));
        if (redEnvelopeDTO.getStatus() == 0) {

            logger.info("二维码失效了");
            return Result.fail("谢谢惠顾");
        }

        if (user.getId().equals(redEnvelopeDTO.getUserId())) {
            return Result.fail("自己不能扫自己的推广红包");
        }

        User originUser = userRepository.findOne(redEnvelopeDTO.getUserId());

        Transactions transactions = new Transactions(null, redEnvelopeDTO.getUserId(), user.getId(),
                redEnvelopeDTO.getMoney(), "红包推广",
                BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE.getId(),
                redEnvelopeDTO.getTempId(), originUser.getPhone(),
                user.getPhone(), originUser.getNickName(), user.getNickName());

        transactionsRepository.saveAndFlush(transactions);
        //展示码的人 扣钱
        Result result = commonService.setBalance(redEnvelopeDTO.getUserId(), redEnvelopeDTO.getMoney().abs().negate(),
                BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE.getId(),
                transactions.getId(), "", "红包推广");

        if (result.getCode() == 0) {
            //扫码者加钱
            commonService.setBalance(user.getId(), redEnvelopeDTO.getMoney(),
                    BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE.getId(),
                    transactions.getId(), "", "红包推广");

            //让redis 二维码 消失
            redisBean.setStringValue(RedisKey.getQrCode(eventKey), "", 1, TimeUnit.SECONDS);
            //让 redis tempId 消失
            redisBean.setStringValue(RedisKey.getQrCodeTempId(redEnvelopeDTO.getTempId()), "", 1, TimeUnit.SECONDS);
            //发送模块消息给两方用户
            weChatProvider.sendTemplate(openId, redEnvelopeDTO.getMoney());
            weChatProvider.sendTemplate(redEnvelopeDTO.getOpenId(), redEnvelopeDTO.getMoney().abs().negate());
            ScanRedEnvelopeVO vo = new ScanRedEnvelopeVO(MoneyUtils.formatMoney(redEnvelopeDTO.getMoney()),
                    originUser.getAvatar(), originUser.getNickName());
            return Result.success(vo);
        }

        return Result.fail();
    }

    /**
     * 处理事件
     *
     * @param map 封装后的http 请求参数
     */
    public String handler(Map<String, String> map) {
        try {
            logger.info("wx request params:" + JSONObject.toJSONString(map));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            logger.info("解析微信请求出错");
            return "";
        }

        String strategyKey = map.get("Event");
        EventHandlerStrategy eventHandlerStrategy = EventHandlerStrategyFactory.getInstance().getStrategy(strategyKey);
        Object obj = eventHandlerStrategy.handler(map);
        if (obj == null) {
            return "";
        }
        //TODO 将对象转换成xml
        return "";
    }
}
