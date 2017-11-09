package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.provider.WeChatProvider;
import com.h9.common.db.repo.AgreementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * CommonController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 11:22
 */
@RestController
@RequestMapping("/common")
@Api(value = "公共模块相关信息",description = "公共模块相关信息")
public class CommonController {
    @Resource
    private AgreementRepository agreementRepository;

    /**
     * @param code  页面标识
     * @return  页面内容
     */
    @ApiOperation(value = "获取content")
    @GetMapping(value = "/page/{code}",produces = MediaType.TEXT_HTML_VALUE)
    public String agreement(@PathVariable("code") String code){
        return agreementRepository.agreement(code);
    }

    @Resource
    private WeChatProvider weChatProvider;

    /*****
     * @param appId 需要获取授权的appId
     * @param  url回调路径 需要base64编码
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "获取code")
    @GetMapping("/wechat/code")
    public void getCode(@RequestParam(value = "appId",required = false) String appId, @RequestParam(value = "url") String state, HttpServletResponse response) throws IOException {
        response.sendRedirect(weChatProvider.getJSCode(appId,state));
    }


    /***
     *
     * @param code 授权码
     * @param state 回调路径
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "微信回调")
    @GetMapping("/wechat/callback")
    public void callback(@RequestParam("code") String code, @RequestParam("state") String state,HttpServletResponse response) throws IOException {
        response.sendRedirect(weChatProvider.getCode(code,state));
    }
}
