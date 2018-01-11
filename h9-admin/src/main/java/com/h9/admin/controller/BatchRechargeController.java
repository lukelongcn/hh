package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.recharge.BatchRechargeDTO;
import com.h9.admin.service.BatchRechargeService;
import com.h9.common.base.Result;
import com.h9.common.db.bean.RedisBean;
import com.h9.common.db.bean.RedisKey;
import com.h9.common.utils.POIUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.hibernate.transform.ResultTransformer;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping(value = "/batch/recharge")
    @ApiOperation("导入批次")
    public Result batchRecharge(@Valid@RequestBody BatchRechargeDTO batchRechargeDTO) {

        return batchRechargeService.batchRecharge(batchRechargeDTO);
    }

    @Secured()
    @GetMapping(value = "/batches")
    @ApiOperation("")
    public Result batchList(@RequestParam(required = false,defaultValue = "1") Integer pageNumber,
                            @RequestParam(required = false,defaultValue = "20") Integer size) {

        return batchRechargeService.batchList(pageNumber,size);
    }
}
