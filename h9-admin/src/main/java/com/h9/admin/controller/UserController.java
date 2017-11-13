package com.h9.admin.controller;

import com.h9.admin.interceptor.PrintParam;
import com.h9.admin.interceptor.PrintType;
import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.SystemUserDTO;
import com.h9.admin.model.vo.LoginResultVO;
import com.h9.admin.service.UserService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by itservice on 2017/10/26.
 */
@RestController
@Api(description = "用户")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    @Secured
    public String hello(){
        return "hello";
    }

    @PostMapping("postTest")
    public String postTest(){
        return "postRest";
    }

    @PutMapping("putTest")
    public String putTest() {
        return "putTest";
    }

    @PostMapping("zeroTest")
    public void zeroTest(){
        int i = 1/0;
    }

    @PrintParam(printType = PrintType.PRINT,print = {"name"})
    @PostMapping("/login")
    @ApiOperation(value = "登录") // hidden=true隐藏接口
    public Result<LoginResultVO> login( @Valid @RequestBody SystemUserDTO systemUserDTO) {
        return  this.userService.login(systemUserDTO.getName(),systemUserDTO.getPassword());
    }

    @Secured
    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public Result logout(@RequestHeader("token")String token) {
        return  this.userService.logout(token);
    }

}
