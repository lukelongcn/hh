package com.transfer.api;

import com.h9.common.base.Result;
import com.transfer.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Api:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/27
 * Time: 11:25
 */
@RestController
public class Api {
    @Resource
    private UserService userService;

    public Result initUser() {
        userService.user();
        return Result.success();
    }
}
