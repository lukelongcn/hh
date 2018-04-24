package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.vo.AgreementVO;
import com.h9.api.provider.WeChatProvider;
import com.h9.common.annotations.PrintReqResLog;
import com.h9.common.base.Result;
import com.h9.common.common.ConfigService;
import com.h9.common.db.entity.config.HtmlContent;
import com.h9.common.db.repo.AgreementRepository;
import com.h9.common.modle.vo.Config;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * CommonController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 11:22
 */
@RestController
@RequestMapping("/common")
@Api(value = "公共模块相关信息", description = "公共模块相关信息")
public class CommonController {
    @Resource
    private AgreementRepository agreementRepository;

    @Resource
    private ConfigService configService;

    /**
     * @param code 页面标识
     * @return 页面内容
     */
    @ApiOperation(value = "获取content")
    @GetMapping(value = "/page/{code}", produces = MediaType.TEXT_HTML_VALUE)
    @PrintReqResLog(printRequestParams = true)
    public String agreement(@NotBlank(message = "页面丢失") @PathVariable("code") String code) {
        HtmlContent htmlContent = agreementRepository.findByCode(code);
        if (htmlContent == null) {
            return "页面不存在";
        }
        if (htmlContent.getTitle() == null) {
            return "404:页面丢失，标题不存在";
        }
        String content = "<html>\n" +
                "<head>\n" +
                "<title>" + htmlContent.getTitle() + "</title>\n" +
                "</head>\n" +
                "<body  style=\"font-size:14px;\">" +
                htmlContent.getContent() +
                "</body>\n" +
                "</html>";
        return content;
    }


    @ApiOperation(value = "获取content")
    @GetMapping(value = "/pageJson/{code}")
    @PrintReqResLog(printRequestParams = true)
    public Result agreementJson(@NotBlank(message = "页面丢失") @PathVariable("code") String code) {
        HtmlContent htmlContent = agreementRepository.findByCode(code);
        if (htmlContent == null) {
            return Result.fail("页面不存在");
        }
        if (htmlContent.getTitle() == null) {
            return Result.fail("404:页面丢失，标题不存在");
        }
        AgreementVO agreementVO = new AgreementVO(htmlContent);
        return Result.success("获取成功", agreementVO);
    }


    @Resource
    private WeChatProvider weChatProvider;

    /*****
     * @param appId 需要获取授权的appId
     * url回调路径 需要base64编码
     * @param response 重定向
     * @throws IOException 异常
     */
    @ApiOperation(value = "获取code")
    @GetMapping("/wechat/code")
    public void getCode(@RequestParam(value = "appId", required = false) String appId, @RequestParam(value = "url") String state, HttpServletResponse response) throws IOException {
        response.sendRedirect(weChatProvider.getJSCode(appId, state));
    }


    /***
     *
     * @param code 授权码
     * @param state 回调路径
     * @param response 重定向
     * @throws IOException 异常
     */
    @ApiOperation(value = "微信回调")
    @GetMapping("/wechat/callback")
    public void callback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws IOException {
        response.sendRedirect(weChatProvider.getCode(code, state));
    }

    /**
     * ("1 为文本 ，2为对象 ，3 为数组")
     *
     * @param type
     * @param code
     * @return
     */
    @Secured
    @GetMapping(value = "/config/params")
    public Result listWhiteList(@RequestParam Integer type, @RequestParam String code) {

        if (type < 1 || type > 3) {
            return Result.fail("type 范围在 1-3");
        }
        Result result = new Result(0, "");
        switch (type) {
            case 1:
                String value = configService.getStringConfig(code);
                result.setData(value);
                break;
            case 2:
                Map<String, String> valueMap = configService.getMapConfig(code);
                List list = new ArrayList();
                valueMap.forEach((k, v) -> {
                    Map newMap = new HashMap();
                    newMap.put("key", k);
                    newMap.put("value", v);
                    list.add(newMap);
                });
                result.setData(list);
                break;
            case 3:
                List<String> stringListConfig = configService.getStringListConfig(code);
                result.setData(stringListConfig);
                break;
            default:
                List<Config> configValue = configService.getMapListConfig(code);
                result.setData(configValue);
                break;
        }
        return result;
    }
}
