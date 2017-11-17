package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.service.SmsService;
import com.h9.api.service.UserService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by itservice on 2017/10/26.
 */
@RestController
@Api(value = "用户相关接口",description = "用户相关接口")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private SmsService smsService;

    /**
     * description: 手机号登录
     */
    @PostMapping("/user/phone/login")
    @ApiOperation("手机号登录")
    public Result<LoginResultVO> phoneLogin(@Valid@RequestBody UserLoginDTO userLoginDTO){
        return userService.loginFromPhone(userLoginDTO);
    }




    /**
     * description: 发送验证码
     */
//    @Secured(bindPhone = false)
    @GetMapping("/user/sms/{phone}/{type}")
    @ApiOperation("发送非注册验证码")
    public Result sendSMS(@PathVariable("phone") String phone,@PathVariable Integer type,@SessionAttribute("curUserId")Long userId){
        return smsService.sendSMSCode(userId,phone, type);
    }




    /**
     * description: 发送注册的验证码
     */
    @GetMapping("/user/register/{phone}")
    @ApiOperation("发送注册的验证码")
    public Result sendRedistSMS(@PathVariable("phone") String phone){
        return smsService.sendSMSCode(phone);
    }


    /**
     * description: 修改个人信息
     */
    @Secured
    @PutMapping("/user/info")
    @ApiOperation("修改个人信息")
    public Result updateInfo(@SessionAttribute("curUserId")Long userId,
                                 @Valid@RequestBody UserPersonInfoDTO personInfoDTO){
        return userService.updatePersonInfo(userId,personInfoDTO);
    }


    @GetMapping("/wechat/login")
    public Result getCode(@RequestParam(value = "code",required = false)
                                    @NotNull(message = "微信登录失败") String code){
        return userService.loginByWechat(code);
    }


    /**
     * description: 绑定手机号码
     */
    @Secured(bindPhone = false)
    @PostMapping("/user/phone/bind")
    public Result bindPhone(@SessionAttribute(value = "curUserId")Long userId,
                            @RequestHeader("token") String token,
                            @Valid@RequestBody UserLoginDTO personInfoDTO
    ){
        return userService.bindPhone(userId,token,personInfoDTO.getCode(),personInfoDTO.getPhone());
    }


    /**
     * description: 获取用户信息
     */
    @Secured(bindPhone = true)
    @GetMapping("/user/info/options")
    public Result options(@SessionAttribute("curUserId")Long userId){
        return userService.findAllOptions(userId);
    }

    /**
     * description: 常见问题说明
     */
    @GetMapping("/user/help")
    public Result questionHelp(){
        return userService.questionHelp();
    }


    /**
     * description: 微信配置
     *
     *
     */
    @GetMapping("/wechat/config")
    public Result Config(HttpServletRequest request){
        String refer = request.getHeader("Referer");
        return userService.getConfig(refer);
    }
}
