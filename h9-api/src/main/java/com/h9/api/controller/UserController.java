package com.h9.api.controller;

import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.provider.SMService;
import com.h9.api.service.UserService;
import com.h9.common.base.Result;
import org.hibernate.annotations.Source;
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

    @PostMapping("/user/phone/login")
    public Result phoneLogin(@Valid@RequestBody UserLoginDTO userLoginDTO){

        return userService.loginFromPhone(userLoginDTO);
    }

//    @PostMapping("/user/sms/register/{phone}")
//    public Result sendRegistSMS(@PathVariable String phone){
//        return userService.smsRegister(phone);
//
//    }

}
