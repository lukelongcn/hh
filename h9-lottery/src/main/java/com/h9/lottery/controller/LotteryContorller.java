package com.h9.lottery.controller;

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
import javax.websocket.server.PathParam;

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

    @Secured
    @GetMapping("/qr")
    @ApiOperation(value = "扫码抽奖")
    public Result appCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                              @SessionAttribute("curUserId") long userId,
                            LotteryDto lotteryVo, HttpServletRequest request){
        return lotteryService.appCode(userId,lotteryVo,request);
    }


    @Secured
    @PostMapping("/{code}/start")
    @ApiOperation(value = "开始抽奖")
    public Result startCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                          @SessionAttribute("curUserId") long userId
                            ,@PathVariable("code") String code){
        return lotteryService.lottery(userId,code);
    }



    @Secured
    @GetMapping("/room/{code}")
    @ApiOperation(value = "奖励房间")
    public Result<LotteryResult> getLotteryRoom(
            @ApiParam(value = "用户token" ,name = "token",required = true,type="header")
            @SessionAttribute("curUserId") long userId,@PathVariable("code") String code){
        logger.debugv("userId {0} code {1}" ,userId, code);
        return lotteryService.getLotteryRoom(userId,code);
    }







}
