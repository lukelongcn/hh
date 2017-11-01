package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.common.base.Result;
import org.hibernate.annotations.GeneratorType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * TestController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:26
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * description: 手机号登录
     */
    @GetMapping("/hello")
    public Result phoneLogin(){
        return Result.success();
    }



}
