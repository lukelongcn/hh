package com.h9.lottery.controller;

import com.h9.common.base.Result;
import com.h9.lottery.interceptor.Secured;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.asm.Advice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * ProductController:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/7
 * Time: 19:19
 */
@RestController
public class ProductController {
    @Resource
    private ProductService productService;

    @Secured
    @GetMapping("/product/check")
    @ApiOperation(value = "扫码抽奖")
    public Result appCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                          @SessionAttribute("curUserId") long userId,
                          @ModelAttribute LotteryDto lotteryVo, HttpServletRequest request){
        return productService.getAuthenticity(userId,lotteryVo,request);
    }
}
