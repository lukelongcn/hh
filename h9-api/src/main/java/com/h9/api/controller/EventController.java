package com.h9.api.controller;

import com.h9.api.model.dto.VerifyTokenDTO;
import com.h9.common.utils.CheckoutUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by itservice on 2018/1/25.
 */

@Controller
public class EventController {

    @GetMapping(value = "/wx/event")
    public String verifyToken( VerifyTokenDTO verifyTokenDTO) {
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
