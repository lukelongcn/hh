package com.h9.admin.service;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: George
 * @date: 2017/11/23 10:18
 */
@Service
@Transactional
public class FileService {
    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    String secretKey;
    @Value("${qiniu.servlet.bucket}")
    private String bucket;
    @Value("${qiniu.img.path}")
    private String imgPath;
    @Value("${h9.current.envir}")
    private String envir;

    public Result uploadImage(MultipartFile file, String path) {
        if (file == null) return Result.fail("请选择图片");
        path = this.buildFilePath("images",path);
        return this.upload(file, path);
    }

    public Result uploadFile(MultipartFile file, String path) {
        if (file == null) return Result.fail("请选择文件");
        path = this.buildFilePath("file",path);
        return this.upload(file, path);
    }

    private String buildFilePath(String type,String path) {
        StringBuilder key = new StringBuilder(type).append("/").append(envir);
        if(StringUtils.isNotBlank(path)){
            if ("/".equals(path)) {
                key.append(path).append(UUID.randomUUID());
            }else {
                key.append(path).append("/").append(UUID.randomUUID());
            }
        }else{
            key.append("/other/").append(UUID.randomUUID());
        }
        return key.toString();
    }

    private Result upload(MultipartFile file,String key) {
        //构造一个带指定Zone对象的配置类,华南zone1,华东zone0
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        // String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,key.toString(), 3600, new StringMap().put("insertOnly", 1 ));
        try {
            Response response = uploadManager.put(file.getInputStream(), key.toString(), upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return Result.success("上传成功",imgPath+"/"+key);
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

  /*  public String uploadCKEditorImage(MultipartFile file, int CKEditorFuncNum) {

    }*/
}
