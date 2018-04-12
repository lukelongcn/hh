package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.AddBigRichDTO;
import com.h9.admin.model.dto.AddWinnerUserDTO;
import com.h9.admin.model.vo.BigRichListVO;
import com.h9.admin.model.vo.JoinBigRichUser;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import com.h9.common.modle.dto.RewardQueryDTO;
import com.h9.admin.model.vo.LotteryFlowActivityVO;
import com.h9.admin.model.vo.RewardVO;
import com.h9.admin.service.ActivityService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: George
 * @date: 2017/11/7 19:42
 */
@RestController
@Api(description = "活动管理")
@RequestMapping(value = "/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Secured(accessCode = "activity:lottery:list")
    @GetMapping(value = "/lottery/page")
    @ApiOperation("分页获取抢红包")
    public Result<PageResult<RewardVO>> getRewards(RewardQueryDTO rewardQueryDTO) {
        return this.activityService.getRewards(rewardQueryDTO);
    }

    @Secured(accessCode = "activity:lottery:flow:list")
    @GetMapping(value = "/lottery/flow/page")
    @ApiOperation("分页获取抢红包参与列表")
    public Result<PageResult<LotteryFlowActivityVO>> getLotteryFlows(LotteryFlowActivityDTO lotteryFlowActivityDTO) {
        return this.activityService.getLotteryFlows(lotteryFlowActivityDTO);
    }

    /*@Secured
    @GetMapping(value = "/lottery/flow/{id}")
    @ApiOperation("分页获取抢红包参与详情")
    public Result<LotteryFlowDetailVO> getLotteryFlow(){
        return Result.success(new LotteryFlowDetailVO());
        //return this.activityService.getRewards(rewardQueryDTO);
    }*/

    @Secured
    @PostMapping(value = "/bigRich")
    @ApiOperation("新增大富贵期数")
    public Result addBigRichActivity(@Valid @RequestBody @ApiParam AddBigRichDTO addBigRichDTO) {
        addBigRichDTO.setId(-1L);
        return activityService.editBigRichActivity(addBigRichDTO);
    }

    @Secured
    @PutMapping(value = "/bigRich")
    @ApiOperation("编辑大富贵期数")
    public Result editBigRichActivity(@Valid @RequestBody @ApiParam AddBigRichDTO addBigRichDTO) {
        return activityService.editBigRichActivity(addBigRichDTO);
    }

    @Secured
    @GetMapping(value = "/bigRiches")
    @ApiOperation("大富贵期数列表")
    public Result<BigRichListVO> bigRichList(@RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(defaultValue = "1") Integer pageNumber) {
        return activityService.bigRichList(pageNumber, pageSize);
    }

    @Secured
    @PostMapping(value = "/bigRich/user")
    @ApiOperation("添加中奖用户")
    public Result addWinnerUser(@RequestBody @Valid @ApiParam AddWinnerUserDTO addWinnerUserDTO,
                                @SessionAttribute("curUserId") Long userId) {
        return activityService.addWinnerUser(addWinnerUserDTO, userId);
    }


    @Secured
    @GetMapping(value = "/bigRich/users/{id}")
    @ApiOperation("参与用户列表")
    public Result<JoinBigRichUser> bigRichUsers(@ApiParam("期数Id") @PathVariable Long id,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "1") Integer pageNumber) {
        return activityService.bigRichUsers(id, pageSize, pageNumber);
    }


    @Secured
    @PutMapping(value = "/bigRich/{id}/{status}")
    @ApiOperation("启用/禁用")
    public Result<JoinBigRichUser> modifyBigRichStatus(@ApiParam("期数Id") @PathVariable Long id,
                                                       @Param("1 启用，0 禁用") @PathVariable Integer status) {
        return activityService.modifyStatus(id, status);
    }


    @Secured
    @GetMapping("bigRich/startLottery")
    public Result startLottery(@RequestParam Long id){
        return activityService.startBigRIchLotteryTest(id);
    }
}
