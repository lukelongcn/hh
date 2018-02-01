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
     * description: 图片列表，
     * @param  tab 1 为旅游加体检，2为体检 ，3 为旅游
     */
    @Secured
    @GetMapping("/travel/health/{tab}")
    public Result bannerList(@SessionAttribute("curUserId")long userId, @PathVariable Integer tab){
        return travelService.bannerList(userId,tab);
    }
}
