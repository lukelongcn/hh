package com.h9.api.controller;

import com.h9.api.interceptor.Secured;
import com.h9.api.service.TravelService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 旅游健康卡接口
 */
@RestController
public class TravelController {

    @Resource
    private TravelService travelService;
    /**
     *
     * description: 旧版本使用
     * @param  tab 1 为旅游加体检，2为体检 ，3 为旅游
     */
    @GetMapping("/travel/health/{tab}")
    public Result bannerList(@SessionAttribute("curUserId")long userId, @PathVariable Integer tab){
        return travelService.bannerList(userId,tab);
    }

    /**
     * 新版本使用此接口
     * @param userId
     * @param tab 1 为旅游加体检，2为体检 ，3 为旅游
     * @return
     */

    @GetMapping("/travel/health")
    public Result bannerList2(@SessionAttribute("curUserId")long userId, @RequestParam(defaultValue = "1") Integer tab){
        return travelService.bannerList(userId,tab);
    }
}
