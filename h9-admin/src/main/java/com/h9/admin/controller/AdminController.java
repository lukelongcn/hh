package com.h9.admin.controller;

import com.h9.admin.model.dto.GlobalPropertyDTO;
import com.h9.admin.service.ConfigService;
import com.h9.common.base.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: George
 * @date: 2017/10/30 15:03
 */
@RestController
@Api
@RequestMapping(value = "/super")
public class AdminController {
    @Autowired
    private ConfigService configService;

    @PutMapping("/global_property")
    public Result updateGlobalProperty(@Valid @ModelAttribute GlobalPropertyDTO globalPropertyDTO){
        return  this.configService.updateGlobalProperty(globalPropertyDTO);
    }

   /* @PutMapping("/global_property/all")
    public Result updateAllGlobalProperty(){
        return  this.configService.updateGlobalProperty(globalPropertyDTO);
    }*/

}
