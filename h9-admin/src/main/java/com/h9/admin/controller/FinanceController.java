package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.common.modle.dto.LotteryFLowRecordDTO;
import com.h9.admin.model.dto.finance.TransferLotteryFLowDTO;
import com.h9.admin.model.dto.finance.WithdrawRecordQueryDTO;
import com.h9.admin.model.vo.LotteryFlowFinanceVO;
import com.h9.admin.model.vo.LotteryFlowRecordVO;
import com.h9.common.modle.vo.admin.WithdrawRecordVO;
import com.h9.admin.service.FinanceService;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.WithdrawalsRecord;
import com.h9.common.modle.dto.LotteryFlowFinanceDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: George
 * @date: 2017/11/9 11:32
 */
@RestController
@Api(description = "财务管理")
@RequestMapping(value = "/finance")
public class FinanceController {
    @Autowired
    private FinanceService financeService;

    @Secured
    @GetMapping(value="/withdraw_record/page")
    @ApiOperation("分页获取提现记录")
    public Result<PageResult<WithdrawRecordVO>> getWithdrawRecords(WithdrawRecordQueryDTO withdrawRecordQueryDTO) throws InvocationTargetException, IllegalAccessException {
        return this.financeService.getWithdrawRecords(withdrawRecordQueryDTO);
    }

    @Secured
    @PostMapping(value="/withdraw_record/{id}/status")
    @ApiOperation("提现退回")
    public Result<WithdrawalsRecord> cancelWithdrawRecord(@PathVariable long id)  {
        return this.financeService.updateWithdrawRecordStatus(id);
    }

    @Secured
    @GetMapping(value = "/lottery/flow/page")
    @ApiOperation("分页未转账列表")
    public Result<PageResult<LotteryFlowFinanceVO>> getLotteryFlows(LotteryFlowFinanceDTO lotteryFlowFinanceDTO) throws InvocationTargetException, IllegalAccessException {
        return this.financeService.getLotteryFlows(lotteryFlowFinanceDTO);
    }

    @Secured
    @PostMapping(value="/lottery/flow/record")
    @ApiOperation("转账")
    public Result transferFromLotteryFlows( @RequestBody TransferLotteryFLowDTO transferLotterryFLowDTO)  {
        return this.financeService.transferFromLotteryFlows(transferLotterryFLowDTO.getIds());
    }

    @Secured
    @GetMapping(value = "/lottery/flow/record/page")
    @ApiOperation("分页已转账列表")
    public Result<PageResult<LotteryFlowRecordVO>> getLotteryFlowRecords(LotteryFLowRecordDTO lotteryFLowRecordDTO) throws InvocationTargetException, IllegalAccessException {
        return this.financeService.getLotteryFlowRecords(lotteryFLowRecordDTO);
    }

    @Secured
    @PutMapping(value="/lottery/flow/record/{id}/status")
    @ApiOperation("重新转账")
    public Result transferFromLotteryFlowRecord( @PathVariable long id)  {
        return this.financeService.transferFromLotteryFlowRecord(id);
    }


}
