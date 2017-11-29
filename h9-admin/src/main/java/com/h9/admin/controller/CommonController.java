package com.h9.admin.controller;

import com.google.gson.Gson;
import com.h9.admin.service.FileService;
import com.h9.common.base.Result;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author: George
 * @date: 2017/11/3 20:30
 */
@SuppressWarnings("Duplicates")
@RestController
@Api(description = "公用")
@RequestMapping(value = "/common")
public class CommonController {

    @Autowired
    private FileService fileService;

    @PostMapping("/images/upload")
    @ApiOperation(value = "图片上传")
    public Result uploadImage(MultipartFile file,String path) {
       return this.fileService.uploadImage(file,path);
    }

    @PostMapping("/file/ckeditor/upload")
    @ApiOperation(value = "ckeditor编辑器图片上传")
    public void uploadImage(MultipartFile upload,int CKEditorFuncNum,HttpServletResponse response) throws IOException {
        Result result = this.fileService.uploadImage(upload,null);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("this.editor.tools.callFunction(" + CKEditorFuncNum + ",'" + result.getData() + "',''" + ")");
        out.println("</script>");
        out.flush();
        out.close();
    }

    @PostMapping("/file/upload")
    @ApiOperation(value = "文件上传")
    public Result uploadFile(MultipartFile file,String path) {
        return this.fileService.uploadFile(file, path);
    }

}
