package com.h9.api.controller;

import com.h9.api.service.FileService;
import com.h9.common.base.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by itservice on 2017/10/30.
 */
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * description: 上传文件
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        return fileService.upload(file);
    }
}
