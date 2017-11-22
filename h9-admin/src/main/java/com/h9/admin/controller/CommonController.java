package com.h9.admin.controller;

import com.google.gson.Gson;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: George
 * @date: 2017/11/3 20:30
 */
@SuppressWarnings("Duplicates")
@RestController
@Api
@RequestMapping(value = "/common")
public class CommonController {

    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    String secretKey = "3wKtwQGZfA7xFIsAt3I1LDaOR_kF6UVvRojLdi9k";
    @Value("${qiniu.servlet.bucket}")
    private String bucket;
    @Value("${qiniu.img.path}")
    private String imgPath;
    @Value("${h9.current.envir}")
    private String envir;

    @PostMapping("/file/upload")
    public Result upload(MultipartFile file,String path) {
        if (file == null) return Result.fail("请选择图片");
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        StringBuilder savePath = new StringBuilder("images/").append(envir);
        if(StringUtils.isNotBlank(path)){
            if ("/".equals(path)) {
                savePath.append(path).append(UUID.randomUUID());
            }else {
                savePath.append(path).append("/").append(UUID.randomUUID());
            }
        }else{
            savePath.append("/other/").append(UUID.randomUUID());
        }
        /*String upToken = auth.uploadToken(bucket,null,3600,new StringMap()
                .putNotEmpty("saveKey", savePath.toString()),false);*/
        String upToken = auth.uploadToken(bucket,savePath.toString(), 3600, new StringMap().put("insertOnly", 1 ));
        try {
            Response response = uploadManager.put(file.getInputStream(), savePath.toString(), upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return Result.success("上传成功",imgPath+"/"+savePath);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.fail("上传失败");
    }
}
