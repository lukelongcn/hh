package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import com.h9.common.modle.dto.RewardQueryDTO;
import com.h9.admin.model.vo.LotteryFlowActivityVO;
import com.h9.admin.model.vo.RewardVO;
import com.h9.admin.service.ActivityService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: George
 * @date: 2017/11/7 19:42
 */
@RestController
@Api("活动管理")
@RequestMapping(value = "/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Secured(accessCode = "activity:lottery:list")
    @GetMapping(value = "/lottery/page")
    @ApiOperation("分页获取抢红包")
    public Result<PageResult<RewardVO>> getRewards(RewardQueryDTO rewardQueryDTO){
        return this.activityService.getRewards(rewardQueryDTO);
    }

    @Secured(accessCode = "activity:lottery:flow:list")
    @GetMapping(value = "/lottery/flow/page")
    @ApiOperation("分页获取抢红包参与列表")
    public Result<PageResult<LotteryFlowActivityVO>> getLotteryFlows(LotteryFlowActivityDTO lotteryFlowActivityDTO){
        return this.activityService.getLotteryFlows(lotteryFlowActivityDTO);
    }

    /*@Secured
    @GetMapping(value = "/lottery/flow/{id}")
    @ApiOperation("分页获取抢红包参与详情")
    public Result<LotteryFlowDetailVO> getLotteryFlow(){
        return Result.success(new LotteryFlowDetailVO());
        //return this.activityService.getRewards(rewardQueryDTO);
    }*/
}
