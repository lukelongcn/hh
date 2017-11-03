package com.h9.lottery.controller;

import com.h9.common.base.Result;
import com.h9.lottery.interceptor.Secured;
import com.h9.lottery.model.dto.LotteryFlow;
import com.h9.lottery.model.dto.LotteryResult;

import com.h9.lottery.service.LotteryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
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
    @GetMapping("/{code}")
    @ApiOperation(value = "扫码抽奖")
    public Result appCode(@ApiParam(value = "用户token" ,name = "token",required = true,type="header")
                              @SessionAttribute("curUserId") long userId,
                              @PathParam("code") @NotEmpty(message = "条码不存在") String code){
        logger.debugv("userId {0} code {1}" ,userId, code);
        //        TODO
        return  Result.success();
    }

    @Secured
    @GetMapping("/room/{code}")
    @ApiOperation(value = "奖励房间")
    public Result<LotteryResult> getLotteryRoom(
            @ApiParam(value = "用户token" ,name = "token",required = true,type="header")
            @SessionAttribute("curUserId") long userId,@PathParam("code") String code){
        logger.debugv("userId {0} code {1}" ,userId, code);
        return Result.success();
    }


    @Secured
    @GetMapping("/history")
    @ApiOperation(value = "奖励结果页面")
    public Result<LotteryFlow> getLotteryH(@SessionAttribute("curUserId") long userId){
        logger.debugv("userId {0}" ,userId);
        return Result.success();
    }





}
