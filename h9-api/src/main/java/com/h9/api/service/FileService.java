package com.h9.api.service;

import com.google.gson.Gson;
import com.h9.common.base.Result;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by itservice on 2017/10/30.
 */
@SuppressWarnings("Duplicates")
@Service
public class FileService {
    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    String secretKey = "3wKtwQGZfA7xFIsAt3I1LDaOR_kF6UVvRojLdi9k";
    @Value("${qiniu.servlet.bucket}")
    private String bucket;
    @Value("${qiniu.img.path}")
    private String imgPath;
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * description: 验证图片的格式和文件大小
     */
    public Result verifyFileTypeAndSize(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int start = fileName.indexOf(".");
        logger.info("文件名： "+fileName);
        String fileType = fileName.substring(start + 1, fileName.length());
        List<String> expectType = Arrays.asList("JPEG", "TIFF", "RAW", "BMP", "GIF", "PNG","JPG");
        boolean containsResult = expectType.contains(fileType.toLowerCase());
        containsResult |= expectType.contains(fileType.toUpperCase());
        if (!containsResult) return Result.fail("请传入正确的图片类型,当前文件类型: "+fileType);

        long fileSize = file.getSize() / (1024 * 1024);
        if (fileSize > 5) {
            return Result.fail("不支持上传5M以上的文件");
        }
        return Result.success();
    }

    public Result fileUpload(MultipartFile file) {

        if (file == null) return Result.fail("请选择图片");

        Result verityResult = verifyFileTypeAndSize(file);
        if (verityResult.getCode() == 1) return verityResult;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
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
            return Result.success("上传成功", imgPath + putRet.key);

        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.info("response : "+r.getInfo());
            logger.info(ex.getMessage(),ex);
        } catch (IOException e) {
            logger.info(e.getMessage(),e);
        }

        return Result.fail("上传失败");
    }

    public Result fileUploadImg(MultipartFile file) {
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
            return Result.success("上传成功", imgPath + putRet.key);

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
