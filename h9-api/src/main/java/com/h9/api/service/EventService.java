package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.EventDTO;
import com.h9.api.model.dto.RedEnvelopeDTO;
import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.api.provider.WeChatProvider;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.db.entity.BalanceFlow;
import com.h9.common.db.entity.Transactions;
import com.h9.common.db.entity.User;
import com.h9.common.db.repo.TransactionsRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.utils.CheckoutUtil;
import com.h9.common.utils.XMLUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    public String wxEventCallBack(HttpServletRequest request) {
        Map<String, String> map = null;
        try {
            map = XMLUtils.parseXml(request);
            logger.info("wx request params:" + JSONObject.toJSONString(map));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            logger.info("解析微信请求出错");
            return "";
        }
        String event = map.get("Event");
        if (WeChatProvider.EventEnum.SUBSCRIBE.getValue().equals(event)) {
            handleSubscribe(map);
        } else if (WeChatProvider.EventEnum.SCAN.getValue().equals(event)) {
            handleScan(map);
        }

        return "ok";
    }

    private void handleScan(Map<String, String> map) {


    }

    @Transactional
    private void handleSubscribe(Map<String, String> map) {

        String eventKey = map.get("EventKey");

        eventKey = eventKey.replace("qrscene_", "");

        String codeJson = redisBean.getStringValue(RedisKey.getQrCode(eventKey));

        if (StringUtils.isBlank(codeJson)) {

            logger.info("codeJson 为空: " + codeJson);
            return;
        }

        RedEnvelopeDTO redEnvelopeDTO = JSONObject.parseObject(codeJson, RedEnvelopeDTO.class);

        logger.info("redEnvelopeDTO: " + JSONObject.toJSONString(redEnvelopeDTO));
        if (redEnvelopeDTO.getStatus() == 0) {

            logger.info("二维码失效了");
            return;
        }

        //发送方帐号（一个OpenID）
        String openId = map.get("FromUserName");
        User user = userRepository.findByOpenId(openId);
        if (user == null) {
            // 注册用户
            user = userService.registUser();
        }
        Transactions transactions = new Transactions(null, redEnvelopeDTO.getUserId(), user.getId(), redEnvelopeDTO.getMoney(), "红包");
        transactionsRepository.saveAndFlush(transactions);

        //扫码者加钱,展示码的人 扣钱
        commonService.setBalance(user.getId(), redEnvelopeDTO.getMoney(),
                BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE.getId(),
                transactions.getId(), "", "红包");

        commonService.setBalance(redEnvelopeDTO.getUserId(), redEnvelopeDTO.getMoney().abs().negate(),
                BalanceFlow.BalanceFlowTypeEnum.RED_ENVELOPE.getId(),
                transactions.getId(), "", "红包");

        //让redis 二维码 消失
        redisBean.setStringValue(RedisKey.getQrCode(eventKey), "", 1, TimeUnit.SECONDS);

    }
}
