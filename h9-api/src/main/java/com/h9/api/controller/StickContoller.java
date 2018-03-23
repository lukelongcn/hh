package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.LikeDTO;
import com.h9.api.model.dto.LocationDTO;
import com.h9.api.model.dto.ReportDTO;
import com.h9.api.model.dto.StickCommentDTO;
import com.h9.api.model.dto.StickDto;
import com.h9.api.model.dto.StickRewardJiuYuanDTO;
import com.h9.api.service.StickService;
import com.h9.common.base.Result;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 *
 * StickContoller:liyuan shadow.liu@hey900.com
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


    /**
     * 发帖
     */
    @Secured
    @PostMapping("")
    public Result addStick(@SessionAttribute("curUserId") long userId,
                           @RequestBody @Validated StickDto stickDto,
                           HttpServletRequest request){
        return stickService.addStick(userId,stickDto,request);
    }

    /**
     * 获取地址
     */
    @PostMapping("/address")
    public Result address(@Valid@RequestBody LocationDTO locationDTO){
        return stickService.address(locationDTO);
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


    /**
     * 分类头部信息
     */
    @GetMapping("/{type}/detail")
    public Result home(@PathVariable("type") long typeId) {
        return stickService.typeDetail(typeId);
    }


    /**
     * 获取帖子详情
     * @param id 帖子id
     * @return Result
     */
    @Secured
    @GetMapping("/detail/{id}")
    public Result detail(@SessionAttribute("curUserId") long userId,@PathVariable("id")long id){
        return stickService.detail(userId,id);
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
     * id 贴子id或评论id
     * type 1：帖子 2：评论
     * @return Result
     */
    @Secured
    @PostMapping("/like")
    public Result like(@SessionAttribute("curUserId")long userId,
                       @Valid@RequestBody LikeDTO likeDTO){
        Long id = likeDTO.getId();
        Integer type = likeDTO.getType();
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
                             @Valid @RequestBody StickCommentDTO stickCommentDTO,
                             HttpServletRequest request){
        return stickService.addComment(userId,stickCommentDTO,request);
    }

    /**
     * 贴子评论列表
     * @param stickId 贴子id
     * @param page 页码
     * @param limit 个数
     * @return Result
     */
    @Secured
    @GetMapping("/getComment")
    public Result getComment(@SessionAttribute("curUserId")long userId,
                             @RequestParam("stickId")long stickId,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit){
        return stickService.getComment(userId,stickId,page,limit);
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
     * @return Result
     */
    @Secured
    @PostMapping("/rewardJiuyuan")
    public Result rewardJiuyuan(@SessionAttribute("curUserId")long userId,
                                @Valid@RequestBody StickRewardJiuYuanDTO stickRewardJiuYuanDTO){
        return stickService.rewardJiuyuan(userId, stickRewardJiuYuanDTO);
    }

    /**
     * 打赏金额
     */
    @Secured
    @PostMapping("/reward")
    public Result reward(@SessionAttribute("curUserId")long userId,
                         @Valid@RequestBody StickRewardJiuYuanDTO stickRewardJiuYuanDTO,
                         HttpServletRequest request){
        return stickService.reward(userId,stickRewardJiuYuanDTO,request);
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
     * 编辑贴子
     * @param userId token
     * @param stickId 帖子id
     * @param stickDto 请求对象
     * @return Result
     */
    @Secured
    @PostMapping("/update/{stickId}")
    public Result update(@SessionAttribute("curUserId")long userId,
                         @PathVariable("stickId")long stickId,
                         @RequestBody @Validated StickDto stickDto,
                         HttpServletRequest request){
        return stickService.updateStick(userId,stickId,stickDto,request);
    }

    /**
     * 举报类别
     * @return Result
     */
    @GetMapping("/getReportType")
    public Result getReportType(){
        return stickService.getReportType();
    }

    /**
     * 举报贴子
     * @param userId 用户id
     * stickId 贴子id
     * content 内容
     * @return Result
     */
    @Secured
    @PostMapping("report")
    public Result getReport(@SessionAttribute("curUserId")long userId,
                            @Valid@RequestBody ReportDTO reportDTO){
        return stickService.report(userId,reportDTO);
    }
}
