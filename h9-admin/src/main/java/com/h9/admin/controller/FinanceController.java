package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.vo.WithdrawRecordVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.BannerType;
import com.h9.common.modle.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: George
 * @date: 2017/11/9 11:32
 */
@RestController
@Api(description = "财务")
@RequestMapping(value = "/finance")
public class FinanceController {

    @Secured
    @GetMapping(value="/withdraw_raecord/page")
    @ApiOperation("分页获取提现记录")
    public Result<PageResult<WithdrawRecordVO>> getBannerTypes(PageDTO pageDTO){
        //return this.communityService.getBannerTypes(pageDTO);
        return  Result.success(new PageResult<WithdrawRecordVO>());
    }
}
