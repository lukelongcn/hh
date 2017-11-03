package com.h9.admin.controller;

import com.h9.admin.interceptor.Secured;
import com.h9.admin.model.dto.BannerAddDTO;
import com.h9.common.base.Result;
import com.h9.common.db.entity.Banner;
import com.h9.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: George
 * @date: 2017/11/3 20:30
 */
@RestController
@Api
@RequestMapping(value = "/common")
public class CommonController {
    //@Secured
    @PostMapping(value="/file")
    @ApiOperation("上传文件")
    public Result upload(MultipartFile file){
        return FileUtil.upload(file);
    }

}
