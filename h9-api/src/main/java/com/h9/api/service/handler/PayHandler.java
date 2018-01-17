package com.h9.api.service.handler;

import com.h9.api.provider.PayProvider;
import com.h9.common.base.Result;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * PayHandler:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/11
 * Time: 16:50
 */
@Component
public class PayHandler {

    @Resource
    private PayProvider payProvider;


    public void initPay(){
        payProvider.initConfig();
    }




}
