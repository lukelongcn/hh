package com.h9.api.service;

import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.common.utils.CheckoutUtil;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by itservice on 2018/1/25.
 */
@Service
public class EventService {
    private Logger logger = Logger.getLogger(this.getClass());
    public String handle(VerifyTokenDTO verifyTokenDTO) {
        String signature = verifyTokenDTO.getSignature();
        String timestamp = verifyTokenDTO.getTimestamp();
        String nonce = verifyTokenDTO.getNonce();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("验证成功");
            return verifyTokenDTO.getEchostr();
        }else{
            logger.info("验证失败");
            return "验证失败";
        }
    }
}
