package com.h9.common.utils;

import com.google.gson.Gson;
import com.h9.common.base.Result;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: George
 * @date: 2017/11/3 20:00
 */
@Component
public class FileUtil {
    @Value("${qiniu.AccessKey}")
   public static String accessKey;
    @Value("${qiniu.SecretKey}")
    public static final String secretKey = "3wKtwQGZfA7xFIsAt3I1LDaOR_kF6UVvRojLdi9k";
    @Value("${qiniu.servlet.bucket}")
    public static String bucket;
    @Value("${qiniu.img.path}")
    public  static String imgPath;

    public static Result upload(MultipartFile file) {
        if (file == null) return Result.fail("请选择图片");
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return Result.success("上传成功",imgPath+putRet.key);

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
