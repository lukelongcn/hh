package com.h9.lottery.controller;

import com.h9.common.annotations.PrintReqResLog;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.lottery.interceptor.Secured;
import com.h9.lottery.model.dto.LotteryFlowDTO;
import com.h9.lottery.model.dto.LotteryResult;
import com.h9.lottery.model.vo.LotteryDto;
import com.h9.lottery.service.LotteryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * LotteryContorller:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 15:18
 */
@RestController
@Api(value = "抽奖活动", description = "抽奖活动",position = 0,basePath = "/h9/lottery")
public class LotteryContorller {

     Logger logger = Logger.getLogger(LotteryContorller.class);

    @Resource
    private LotteryService lotteryService;

    @Secured(bindPhone = false)
    @GetMapping("/qr")
    @ApiOperation(value = "扫码抽奖")
    public Result appCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                              @SessionAttribute("curUserId") long userId,
                          @ModelAttribute LotteryDto lotteryVo, HttpServletRequest request){
        return lotteryService.appCode(userId,lotteryVo,request);
    }


    @Secured(bindPhone = false)
    @GetMapping("/start")
    @ApiOperation(value = "开始抽奖")
    public Result startCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                          @SessionAttribute("curUserId") long userId
                            ,@RequestParam("code") String code){
        logger.debugv("start userId {0} code {1}" ,userId, code);
        return lotteryService.lottery(userId,code);
    }


    @Secured(bindPhone = false)
    @GetMapping("/room")
    @ApiOperation(value = "奖励房间")
    public Result<LotteryResult> getLotteryRoom(
            @ApiParam(value = "用户token" ,name = "token",required = true,type="header")
            @SessionAttribute("curUserId") long userId,@RequestParam("code") String code){
        logger.debugv("room userId {0} code {1}" ,userId, code);
        return lotteryService.getLotteryRoom(userId,code);
    }




    @Secured(bindPhone = false)
    @GetMapping("/history")
    @ApiOperation(value = "抽奖记录")
    public Result<PageResult<LotteryFlowDTO>>  getLotteryRoom(
            @ApiParam(value = "用户token" ,name = "token",required = true,type="header")
            @SessionAttribute("curUserId") long userId,
            @RequestParam(value = "page",required = false) int page,
            @RequestParam(value = "limit",required = false) int limit
    ){
        return lotteryService.history(userId,page,limit);
    }


    @GetMapping("/forward/{code}")
    @ApiOperation(value = "扫码抽奖")
    public void forward(@PathVariable("code") String code, HttpServletResponse response) throws IOException {
        String forward = lotteryService.forward(code);
        logger.debugv("forward path:{0}",forward);
        response.sendRedirect(forward);
    }




}
