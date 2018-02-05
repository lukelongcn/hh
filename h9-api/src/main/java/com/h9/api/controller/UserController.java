package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.TransferDTO;
import com.h9.api.model.dto.UserLoginDTO;
import com.h9.api.model.dto.UserPersonInfoDTO;
import com.h9.api.model.vo.LoginResultVO;
import com.h9.api.provider.WeChatProvider;
import com.h9.api.service.SmsService;
import com.h9.api.service.UserService;
import com.h9.common.annotations.PrintReqResLog;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;

/**
 * Created by itservice on 2017/10/26.
 */
@RestController
@Api(value = "用户相关接口", description = "用户相关接口")
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
    public Result<LoginResultVO> phoneLogin(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                            @RequestHeader(value = "client", required = false, defaultValue = "0") Integer client) {
        return userService.loginFromPhone(userLoginDTO, client);
    }


    /**
     * description: 发送验证码
     */
    @Secured(bindPhone = false)
    @GetMapping("/user/sms/{phone}/{type}")
    @ApiOperation("发送非注册验证码")
    public Result sendSMS(@PathVariable("phone") String phone, @PathVariable Integer type, @SessionAttribute("curUserId") Long userId) {
        return smsService.sendSMSCode(userId, phone, type);
    }

    /**
     * description: 发送注册的验证码
     */
    @GetMapping("/user/register/{phone}")
    @ApiOperation("发送注册的验证码")
    public Result sendRedistSMS(@PathVariable("phone") String phone) {
        return smsService.sendSMSCode(phone);
    }

    /**
     * description: 修改个人信息
     */
    @Secured
    @PutMapping("/user/info")
    @ApiOperation("修改个人信息")
    public Result updateInfo(@SessionAttribute("curUserId") Long userId,
                             @Valid @RequestBody UserPersonInfoDTO personInfoDTO) {
        return userService.updatePersonInfo(userId, personInfoDTO);
    }


    @GetMapping("/wechat/login")
    public Result getCode(@RequestParam(value = "code", required = false)
                          @NotNull(message = "微信登录失败") String code) {
        return userService.loginByWechat(code);
    }


    /**
     * description: 绑定手机号码
     */
    @Secured(bindPhone = false)
    @PostMapping("/user/phone/bind")
    public Result bindPhone(@SessionAttribute(value = "curUserId") Long userId,
                            @RequestHeader("token") String token,
                            @Valid @RequestBody UserLoginDTO personInfoDTO
    ) {
        return userService.bindPhone(userId, token, personInfoDTO.getCode(), personInfoDTO.getPhone());
    }


    /**
     * description: 获取用户信息
     */
    @Secured(bindPhone = true)
    @GetMapping("/user/info/options")
    public Result options(@SessionAttribute("curUserId") Long userId) {
        return userService.findAllOptions(userId);
    }

    /**
     * description: 常见问题说明
     */
    @GetMapping("/user/help")
    public Result questionHelp() {
        return userService.questionHelp();
    }


    /**
     * description: 微信配置
     */
    @GetMapping("/wechat/config")
    public Result Config(HttpServletRequest request) {
        String refer = request.getHeader("Referer");
        return userService.getConfig(refer);
    }


    /**
     * description: 转账信息
     */
    @GetMapping("/user/transfer/info/{phone}")
    @Secured
    public Result transferInfo(@SessionAttribute("curUserId") Long userId, @PathVariable String phone) {
        return userService.transferInfo(userId, phone);
    }

    /**
     * description: 用户转账
     */
    @PostMapping("/user/transfer")
    @Secured
    public Result transfer(@SessionAttribute("curUserId") Long userId, @Valid @RequestBody TransferDTO transferDTO) {
        return userService.transfer(userId, transferDTO);
    }

    /**
     * description: 转账记录
     *
     * @param type 1 为转账 2 为推广红包
     */
    @Secured
    @GetMapping("/user/transactions")
    public Result transactions(@SessionAttribute("curUserId") Long userId,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer limit,
                               @RequestParam(defaultValue = "1") Integer type) {
        return userService.transactions(userId, page, limit, type);
    }

    /**
     * description: 推广红包二维码界面
     *
     */
    @Secured
    @GetMapping("/user/redEnvelope/code")
    public Result redEnvelope(HttpServletRequest request,
                              HttpServletResponse response,
                              @SessionAttribute("curUserId") Long userId, @RequestParam BigDecimal money) {
        return userService.getRedEnvelope(request, response, userId, money);
    }

    /**
     * description: app 推广红包二维码 生成
     *
     */
    @PrintReqResLog(printRequestParams = true)
    @GetMapping("/user/redEnvelope/qrcode")
    public void ownRedEnvelope(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam String tempId) {
        userService.getOwnRedEnvelope(request, response, tempId);
    }

    @GetMapping("/user/temp/redirect")
    public void tempRedirect(HttpServletResponse response,@RequestParam String id){
        userService.tempRedirect(response,id);

    }
    /**
     * description: 扫描 推广红包
     */
    @Secured
    @GetMapping("/user/redEnvelope/scan/qrcode")
    public Result scanQRCode(@RequestParam String tempId,@SessionAttribute("curUserId") Long userId,
                             HttpServletRequest request) {
        return userService.scanQRCode(tempId,userId);
    }

    private Logger logger = Logger.getLogger(this.getClass());

    @Value("${path.app.wechat_host}")
    private String wxHost;

//    @GetMapping("/user/redEnvelope/scan/redirect")
//    public void redirect(@RequestParam String tempId,HttpServletResponse response) {
//
//        String url = wxHost+"/h9-weixin/#/account/hongbao/result?id="+tempId;
//        try {
//            logger.info("url: "+url);
//            response.sendRedirect(url);
//        } catch (IOException e) {
//            logger.info("重定向失败 url: "+url);
//        }
//    }


    /**
     * description: 采用轮洵策略查询红包二维码的状态
     *
     */
    @Secured
    @GetMapping("/user/redEnvelope/code/{tempId}")
    public Result redEnvelopeStatus(@SessionAttribute("curUserId") Long userId, @PathVariable String tempId) {
        return userService.redEnvelopeStatus(tempId);
    }

//    @Secured
//    @GetMapping("/user/redEnvelope/qr/record")
//    public Result qrRecord(@SessionAttribute("curUserId")Long userId,
//                           @RequestParam(defaultValue = "1") Integer page,
//                           @RequestParam(defaultValue = "10") Integer limit){
//        return userService.qrRecord(userId,page,limit);
//    }

}
