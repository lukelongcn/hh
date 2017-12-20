package com.h9.api.controller;

import com.google.gson.Gson;
import com.h9.api.service.FileService;
import com.h9.common.annotations.PrintReqResLog;
import com.h9.common.base.Result;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by itservice on 2017/11/2.
 */
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * description: 上传图片(只能上传图片)
     */

    @PostMapping("/file/upload")
    public Result upload(MultipartFile file) {
        return fileService.fileUpload(file);
    }

    /**
     * description: 上传文件（对格式没有要求）
     */

    @PostMapping("/file/uploadFile")
    public Result uploadImg(MultipartFile file) {
        return fileService.fileUploadImg(file);
    }
}
