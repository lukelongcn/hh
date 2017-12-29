package com.h9.admin.controller;

import com.h9.admin.model.dto.stick.StickTypeDTO;
import com.h9.admin.service.StickService;
import com.h9.common.base.Result;
import net.bytebuddy.implementation.bind.annotation.Default;
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

    @Resource
    private StickService stickService;


    @PostMapping("/type")
    public Result addType(@Validated StickTypeDTO stickTypeDTO){
        return stickService.addStickType(stickTypeDTO);
    }

    @GetMapping("/types")
    public Result listType(@RequestParam(required = false,name = "page",defaultValue = "1") int page,
                           @RequestParam(required = false,name = "page",defaultValue = "20") int limit){
        return stickService.getStick(page,limit);
    }






}
