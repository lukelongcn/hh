package com.h9.api.service;

import com.h9.common.base.Result;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by itservice on 2017/10/30.
 */
@Service
public class FileService {
    private Logger logger = Logger.getLogger(this.getClass());


    public Result upload(MultipartFile file) {
//        String name = file.getName();
//        if (name == null) return Result.fail("请上传图片");
//
//        try {
//            InputStream is = file.getInputStream();
//            byte[] bytes = IOUtils.toByteArray(is);
//
//            com.wedo.server.api.sdk.rest.model.Result<String> result = Service.getInstance().uploadFile2QCloud(bytes);
//
//            if (result != null && result.getStatusCode() == 0) {
//                String url = result.getData();
//                Result myResult = new Result();
//                myResult.setData(url);
//                myResult.setMsg("图片上传成功");
//                myResult.setCode(0);
//                return myResult;
//            } else {
//                return Result.fail("上传文件失败~");
//            }
//        } catch (IOException e) {
//            logger.info(e.getMessage(),e);
//        }

        return Result.fail("上传文件失败~");
    }
}
