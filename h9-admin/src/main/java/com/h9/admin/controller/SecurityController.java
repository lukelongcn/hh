package com.h9.admin.controller;

import com.h9.admin.model.dto.SystemUserDTO;
import com.h9.admin.model.po.SystemUser;
import com.h9.admin.service.SystemUserService;
import com.h9.common.base.Result;
import com.h9.common.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: George
 * @date: 2017/10/30 19:15
 */
@RestController
@Api
@RequestMapping(value = "/security")
public class SecurityController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("/login")
    @ApiOperation(value = "登录") // hidden=true隐藏接口
    public Result login(@Valid @ModelAttribute SystemUserDTO systemUserDTO) {
        Subject subject = SecurityUtils.getSubject();
        String password = MD5Util.getMD5(systemUserDTO.getPassword());
        logger.info("login start userName=" + systemUserDTO.getName() + " password=" + password);
        SystemUser user = systemUserService.getByName(systemUserDTO.getName());
        if (user != null && user.getStatus() != 0) {
            return Result.fail("您的账号已被冻结");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(systemUserDTO.getName(), password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            this.logger.error("登录发生异常", e);
            return Result.fail("您的账号或密码输入错误");
        }

        return new Result(0, "登录成功");
    }

}
