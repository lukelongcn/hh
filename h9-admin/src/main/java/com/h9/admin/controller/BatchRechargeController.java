package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.recharge.BatchRechargeDTO;
import com.h9.admin.model.dto.recharge.RechargeDTO;
import com.h9.admin.service.BatchRechargeService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import io.swagger.annotations.ApiOperation;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by itservice on 2018/1/11.
 */
@RestController
public class BatchRechargeController {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private RedisBean redisBean;
    @Resource
    private BatchRechargeService batchRechargeService;


    @Secured()
    @PostMapping(value = "/batch/recharge/file")
    @ApiOperation("批量充值文件上传")
    public Result batchRechargeFileUpload(MultipartFile file) {

        return batchRechargeService.batchRechargeFile(file);
    }


    @Secured()
    @PostMapping(value = "/batch/import")
    @ApiOperation("导入批次")
    public Result batchRecharge(@Valid@RequestBody BatchRechargeDTO batchRechargeDTO,@SessionAttribute("curUserId") Long userId) {

        return batchRechargeService.importBatchRecharge(batchRechargeDTO,userId);
    }

    @Secured()
    @GetMapping(value = "/batches")
    @ApiOperation("批次列表")
    public Result batchList(@RequestParam(required = false,defaultValue = "1") Integer pageNumber,
                            @RequestParam(required = false,defaultValue = "20") Integer size) {

        return batchRechargeService.batchList(pageNumber,size);
    }

    @Secured()
    @GetMapping(value = "/batch/record/{batchId}")
    @ApiOperation("批次记录列表")
    public Result batchList(@PathVariable Long batchId) {

        return batchRechargeService.rechargeRecordList(batchId);
    }


    @Secured()
    @PostMapping(value = "/batch/recharge")
    @ApiOperation("充值")
    public Result recharge(@RequestBody RechargeDTO rechargeDTO,@SessionAttribute("curUserId") Long userId) {
        return batchRechargeService.recharge(rechargeDTO.getIds(),userId);
    }


    @Secured()
    @GetMapping(value = "/batch/template")
    @ApiOperation("模板地址")
    public Result templateDownLoad() {
        return batchRechargeService.templateUrl();
    }

}
