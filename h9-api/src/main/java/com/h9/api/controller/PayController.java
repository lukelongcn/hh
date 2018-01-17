package com.h9.api.controller;

import com.h9.api.model.dto.PayNotifyVO;
import com.h9.api.service.RechargeService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * PayController:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 20:29
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private RechargeService rechargeService;


    @PostMapping("/callback")
    public Map<String, String> callback(@RequestBody PayNotifyVO payNotifyVO){
        return rechargeService.callback(payNotifyVO);
    }




}
