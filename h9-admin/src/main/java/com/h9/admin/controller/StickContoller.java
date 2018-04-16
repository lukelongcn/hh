package com.h9.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.stick.StickDTO;
import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.admin.model.dto.stick.UpdateStickDTO;
import com.h9.admin.service.StickService;
import com.h9.common.base.Result;

import org.jboss.logging.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;


/**
 * Created with IntelliJ IDEA.
 *
 * CommunityContoller:liyuan
 * Date: 2017/12/29
 * Time: 17:13
 */
@RestController
@RequestMapping("/stick")
public class StickContoller {

    Logger logger = Logger.getLogger(StickContoller.class);
    @Resource
    private StickService stickService;

    @Secured(accessCode = "stick:addType")
    @PostMapping("/type")
    public Result addType( @RequestBody @Validated StickTypeDTO stickTypeDTO){
        logger.debugv(JSONObject.toJSONString(stickTypeDTO));
        return stickService.addStickType(stickTypeDTO);
    }

    /**
     * 编辑分类
     */
    @Secured(accessCode =  "stick:updateType")
    @PostMapping("/updateType")
    public Result updateType(@RequestBody @Validated StickTypeDTO stickTypeDTO){
        return stickService.updateType(stickTypeDTO);
    }

    @Secured(accessCode =  "stick:listType")
    @GetMapping("/types")
    public Result listType(@RequestParam(required = false,name = "page",defaultValue = "1") int pageNumber,
                           @RequestParam(required = false,name = "page",defaultValue = "20") int pageSize){
        return stickService.getStick(pageNumber,pageSize);
    }

    @Secured(accessCode =  "stick:typeDetail")
    @GetMapping("/typeDetail/{id}")
    public Result typeDetail(@PathVariable(value = "id")Long id ){
        return stickService.typeDetail(id);
    }
    /**
     * 拿到反馈列表
     */
    @ApiOperation("拿到反馈列表")
    @Secured(accessCode =  "stick:getReport")
    @GetMapping("/getReport")
    public Result getReport(@RequestParam(defaultValue = "1") Integer pageNumber,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        return stickService.getReport(pageNumber,pageSize);
    }

    /**
     * 拿到打赏记录
     * @param pageNumber 页码
     * @param pageSize 个数
     * @return Result
     * */
    @Secured(accessCode =  "stick:getReward")
    @ApiOperation("拿到打赏记录")
    @GetMapping("/getReward")
    public Result getReward(@RequestParam(defaultValue = "1") Integer pageNumber,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "stickId")long stickId){
       return stickService.getReward(pageNumber,pageSize,stickId);
    }

    /**
     * 添加马甲贴子
     */
    @Secured(accessCode = "stick:addStick")
    @PostMapping("/addStick")
    public Result addStick(@Valid@RequestBody StickDTO stickDTO){
        return stickService.addStick(stickDTO);
    }

    /**
     * 编辑贴子
     */
    @Secured(accessCode = "stick:updateStick")
    @PostMapping("/updateStick")
    public Result updateStick(@Valid@RequestBody UpdateStickDTO updateStickDTO){
        return stickService.updateStick(updateStickDTO);
    }
    /**
     * 评论列表
     */
    @Secured(accessCode = "stick:getComment")
    @GetMapping("/getComment")
    public Result getComment(@RequestParam(defaultValue = "1") Integer pageNumber,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "stickId")long stickId){
        return stickService.getComment(pageNumber,pageSize,stickId);
    }
    /**
     * 删除
     */
    @Secured(accessCode = "stick:delete")
    @PostMapping("/delete")
    public Result delete( @RequestParam(value = "stickId")long stickId){
        return stickService.delete(stickId);
    }

    /**
     * 拿到所有贴子详情
     * @param pageNumber 页码
     * @param pageSize 大小
     * @return Result
     */
    @Secured(accessCode = "stick:allDetail")
    @GetMapping("/allDetail")
    public Result allDetail(@RequestParam(defaultValue = "1") Integer pageNumber,
                            @RequestParam(defaultValue = "10") Integer pageSize){
        return stickService.allDetail(pageNumber,pageSize);
    }

    /**
     * 锁定状态改变
     */
    @Secured(accessCode = "stick:lock")
    @PostMapping("/lock")
    public Result lock(@RequestParam(value = "stickId")long stickId){
        return stickService.lock(stickId);
    }

    /**
     * 审批状态改变
     */
    @Secured(accessCode = "stick:examine")
    @PostMapping("/examine")
    public Result examine(@RequestParam(value = "stickId")long stickId){
        return stickService.examine(stickId);
    }

    /**
     * 重置贴子所有状态
     */
    @Secured(accessCode = "stick:reset")
    @PostMapping("/reset")
    public Result reset(@RequestParam(value = "stickId")long stickId){
        return stickService.reset(stickId);
    }

    /**
     * 评论通过状态
     */
    @Secured(accessCode = "stick:commentState")
    @PostMapping("/commentState")
    public Result commentState(@RequestParam(value = "stickCommentId")long stickComentId){
        return stickService.commentState(stickComentId);
    }

    /**
     * 分类使用状态
     */
    @Secured(accessCode = "stick:typeState")
    @PostMapping("/typeState")
    public Result typeState(@RequestParam(value = "stickTypeId")long stickTypeId){
        return stickService.typeState(stickTypeId);
    }

    /**
     * 删除评论
     */
    @Secured(accessCode = "stick:deleteComment")
    @PostMapping("/deleteComment")
    public Result deleteComment(@RequestParam(value = "stickCommentId")long stickCommentId){
        return stickService.deleteComment(stickCommentId);
    }

    /**
     * 贴子详情
     */
    @Secured(accessCode = "stick:deleteComment")
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(value = "id")long id){
        return stickService.detail(id);
    }
}
