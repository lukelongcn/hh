package com.h9.admin.controller;

import com.h9.admin.job.BlackListJob;
import com.h9.common.base.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/12/26.
 */
@RestController
public class JobController {

    @Resource
    private BlackListJob blackListJob;

    @Value("${h9.current.envir}")
    private String currentEnvir;

    @GetMapping("/job/blackList")
    public Result blackListJob() {
//        if (currentEnvir.equals("product")) {
//            return Result.fail("不支持此环境");
//        }

        blackListJob.scan();
        return Result.success("开始执行定时任务");
    }
}
