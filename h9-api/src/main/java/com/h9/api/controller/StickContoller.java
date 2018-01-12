package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.StickCommentDTO;
import com.h9.api.model.dto.StickDto;
import com.h9.api.service.StickService;
import com.h9.common.base.Result;

import org.apache.commons.collections.ResettableListIterator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickContoller:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 14:58
 */
@RestController
@RequestMapping("/stick")
public class StickContoller {

    @Resource
    private StickService stickService;


    @GetMapping("/type/sample")
    public Result getTypes(){
        return stickService.getStickType();
    }


    @Secured
    @PostMapping("")
    public Result addStick(@SessionAttribute("curUserId") long userId,
                           @RequestBody @Validated StickDto stickDto){
        return stickService.addStick(userId,stickDto);
    }



    @GetMapping("/{type}/list")
    public Result listStick(@PathVariable("type") String type,
                            @RequestParam(required = false,defaultValue = "1") Integer page,
                            @RequestParam(required = false) Integer limit){
        return stickService.listStick(type,page, limit);
    }


    @GetMapping("/home")
    public Result home(){
        return stickService.home();
    }



    @GetMapping("/{type}/detail")
    public Result home(@PathVariable("type") long typeId) {
        return stickService.typeDetail(typeId);
    }


    /**
     * 获取帖子详情
     * @param id 帖子id
     * @return Result
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id")long id){
        return stickService.detail(id);
    }

    /**
     * 搜索帖子
     * @param str 匹配字符串
     * @return Result
     */
    @GetMapping("/search")
    public Result search(@RequestParam(value = "str")String str,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit){
        return stickService.search(str,page,limit);
    }

    /**
     * 点赞帖子或评论
     * @param userId 用户id
     * @param id 贴子id或评论id
     * @param type 1：帖子 2：评论
     * @return Result
     */
    @Secured
    @PostMapping("/like")
    public Result like(@SessionAttribute("curUserId")long userId,
                       @NotNull(message = "id不能为空")@RequestParam(value = "id") long id,
                       @NotNull(message = "点赞类型不能为空")@RequestParam(value = "type") Integer type){
        return stickService.like(userId,id,type);
    }

    /**
     * 添加回复
     * @param userId 用户id
     * @param stickCommentDTO 请求对象
     * @return Result
     */
    @Secured
    @PostMapping("/addComment")
    public Result addComment(@SessionAttribute("curUserId")long userId,
                             @Valid @RequestBody StickCommentDTO stickCommentDTO){
        return stickService.addComment(userId,stickCommentDTO);
    }

    /**
     * 贴子评论列表
     * @param stickId 贴子id
     * @param page 页码
     * @param limit 个数
     * @return Result
     */
    @GetMapping("/getComment")
    public Result getComment(@RequestParam("stickId")long stickId,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit){
        return stickService.getComment(stickId,page,limit);
    }


    /**
     * 打赏列表信息
     * @param stickId 贴子id
     * @return Result
     */
    @GetMapping("/getReward/{stickId}")
    public Result getReward(@PathVariable("stickId")long stickId){
        return stickService.getReward(stickId);
    }

    /**
     * 点击赞赏金额
     * @param money 赞赏金额
     * @return Result
     */
    @Secured
    @GetMapping("/rewardJiuyuan/{stickId}/{money}")
    public Result rewardJiuyuan(@SessionAttribute("curUserId")long userId,
                                @PathVariable("stickId")long stickId,
                                @PathVariable("money")BigDecimal money){
        return stickService.rewardJiuyuan(userId,stickId,money);
    }

    /**
     * 打赏金额
     */
    @Secured
    @PostMapping("/reward/{stickId}/{money}")
    public Result reward(@SessionAttribute("curUserId")long userId,
                         @PathVariable("stickId")long stickId,
                         @PathVariable("money")BigDecimal money){
        return stickService.reward(userId,stickId,money);
    }

    /**
     * 删除帖子
     * @param userId token
     * @param stickId 帖子id
     * @return Result
     */
    @Secured
    @PostMapping("/delete/{stickId}")
    public Result delete(@SessionAttribute("curUserId")long userId,
                         @PathVariable("stickId")long stickId){
        return stickService.delete(userId,stickId);
    }

    /**
     * 删除帖子评论
     * @param userId token
     * @param stickCommentId 帖子评论id
     * @return Result
     */
    @Secured
    @PostMapping("/commentDelete/{stickCommentId}")
    public Result commentDelete(@SessionAttribute("curUserId")long userId,
                                @PathVariable("stickCommentId")long stickCommentId){
        return stickService.commentDelete(userId,stickCommentId);
    }


    /**
     *
     * @param userId token
     * @param stickId 帖子id
     * @param stickDto 请求对象
     * @return Result
     */
    @PostMapping("/update/{stickId}")
    public Result update(@SessionAttribute("curUserId")long userId,
                         @PathVariable("stickId")long stickId,
                         @RequestBody @Validated StickDto stickDto){
        return stickService.updateStick(userId,stickId,stickDto);
    }

    @GetMapping("/getReportType")
    public Result getReportType(){
        return stickService.getReportType();
    }

    @PostMapping("report/{stickId}/{content}")
    public Result getReport(@SessionAttribute("curUserId")long userId,
                            @PathVariable("stickId")long stickId,
                            @PathVariable("content")String content){
        return stickService.report(userId,stickId,content);
    }
}
