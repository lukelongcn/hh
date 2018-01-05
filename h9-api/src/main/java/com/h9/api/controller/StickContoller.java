package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.model.dto.StickDto;
import com.h9.api.service.StickService;
import com.h9.common.base.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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



}
