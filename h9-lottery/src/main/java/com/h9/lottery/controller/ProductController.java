package com.h9.lottery.controller;

import com.h9.common.base.Result;
import com.h9.lottery.interceptor.Secured;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 *
 * ProductController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 19:19
 */
@RestController
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping("/product/check")
    @ApiOperation(value = "扫码抽奖")
    public Result appCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                          @RequestHeader(value = "token",required = false) String token,
                          @Valid @ModelAttribute LotteryDto lotteryVo, HttpServletRequest request){
        return productService.getAuthenticity(token,lotteryVo,request);
    }
}
