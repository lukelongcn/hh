package com.h9.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.stick.StickDTO;
import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.admin.service.StickService;
import com.h9.common.base.Result;
//import com.sun.org.glassfish.gmbal.Description;

import org.jboss.logging.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;


/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * CommunityContoller:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 17:13
 */
@RestController
@RequestMapping("/stick")
public class StickContoller {

    Logger logger = Logger.getLogger(StickContoller.class);
    @Resource
    private StickService stickService;

    @Secured(accessCode = "stick:add")
    @PostMapping("/type")
    public Result addType( @RequestBody @Validated StickTypeDTO stickTypeDTO){
        logger.debugv(JSONObject.toJSONString(stickTypeDTO));
        return stickService.addStickType(stickTypeDTO);
    }

    @Secured(accessCode =  "stick:list")
    @GetMapping("/types")
    public Result listType(@RequestParam(required = false,name = "page",defaultValue = "1") int page,
                           @RequestParam(required = false,name = "page",defaultValue = "20") int limit){
        return stickService.getStick(page,limit);
    }

    /**
     * 拿到反馈列表
     */
    @ApiOperation("拿到反馈列表")
    @GetMapping("/getReport")
    public Result getReport(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit){
        return stickService.getReport(page,limit);
    }

    /**
     * 拿到打赏记录
     * @param page 页码
     * @param limit 个数
     * @return Result
     * */
    @ApiOperation("拿到打赏记录")
    @GetMapping("/getReward")
    public Result getReward(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit){
       return stickService.getReward(page,limit);
    }

    /**
     * 添加马甲贴子
     */
    @PostMapping("/addStick")
    public Result addStick(@Valid@RequestBody StickDTO stickDTO){
        return stickService.addStick(stickDTO);
    }

    /**
     * 评论列表
     */
    @GetMapping("/getComment")
    public Result getComment(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit){
        return stickService.getComment(page,limit);
    }

}
