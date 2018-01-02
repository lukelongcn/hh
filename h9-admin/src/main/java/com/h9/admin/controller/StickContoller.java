package com.h9.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.admin.service.StickService;
import com.h9.common.base.Result;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.jboss.logging.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Secured(accessCode = "stick:list")
    @GetMapping("/types")
    public Result listType(@RequestParam(required = false,name = "page",defaultValue = "1") int page,
                           @RequestParam(required = false,name = "page",defaultValue = "20") int limit){
        return stickService.getStick(page,limit);
    }






}
