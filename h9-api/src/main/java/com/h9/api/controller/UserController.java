package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.provider.SMService;
import com.h9.api.service.AccountService;
import com.h9.api.service.UserService;
import com.h9.common.base.Result;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by itservice on 2017/10/26.
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private SMService smService;


    /**
     * description: 手机号登录
     */
    @Secured
    @PostMapping("/user/phone/login")
    public Result phoneLogin(@Valid  @RequestBody UserLoginDTO userLoginDTO){
        return userService.loginFromPhone(userLoginDTO);
    }

    /**
     * description: 发送验证码
     */
    @Secured
    @PostMapping("/user/sms/register/{phone}")
    public Result sendRegistSMS(@PathVariable @NotEmpty(message = "手机号不能为空") String phone){

        return userService.smsRegister(phone);
    }

    /**
     * description: 修改个人信息
     */
    @Secured
    @PutMapping("/user/info")
    public Result updateInfo(@Valid@RequestBody UserPersonInfoDTO personInfoDTO){
        return userService.updatePersonInfo(personInfoDTO);
    }

    @GetMapping("/user/wechat/code")
    public void getCode(String url){
//        return userService.getCode();
    }








}
