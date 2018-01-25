package com.h9.api.controller;

import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.common.utils.CheckoutUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itservice on 2018/1/25.
 */

@RestController
public class EventController {

    @GetMapping(value = "/wx/event/verify/token")
    public String verifyToken(@RequestBody VerifyTokenDTO verifyTokenDTO) {
        String signature = verifyTokenDTO.getSignature();
        String timestamp = verifyTokenDTO.getTimestamp();
        String nonce = verifyTokenDTO.getNonce();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {

            return verifyTokenDTO.getNonce();
        }

        return "";
    }


}
