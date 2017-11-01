package com.h9.api.service;

import com.h9.common.base.Result;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by itservice on 2017/10/30.
 */
@Service
public class FileService {
    private Logger logger = Logger.getLogger(this.getClass());

    public Result upload(MultipartFile file) {
        String name = file.getOriginalFilename();
        logger.info("文件名：" + name);
        List<String> imgExtends = Arrays.asList("jpeg", "jpg", "png");
        if (name == null) return Result.fail("请上传图片");
        boolean find = false;

        int start = name.indexOf(".")+1;
        String extend = name.substring(start, name.length());

        for (String img : imgExtends) {
            if (img.equalsIgnoreCase(extend)) {
                find = true;
                break;
            }
        }
        if (!find) return Result.fail("不支持的文件格式");

        try {
            File rootFile = new File("d://img/");
            if (!rootFile.exists()) rootFile.mkdirs();
            String imgName = UUID.randomUUID().toString() + ".jpg";
            File saveFile = new File(rootFile, imgName);

            InputStream is = file.getInputStream();
            io2os(is, new FileOutputStream(saveFile));

            String imgUrl = "http://localhost:6305/h9/api/img/" + imgName;
            return Result.success("上传图片成功", imgUrl);
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
        }

        return Result.fail("上传文件失败~");
    }

    public Result showImg(String imgName, HttpServletResponse response) {

        File file = new File("d://img/" + imgName + ".jpg");
        if (file == null) return Result.fail("图片不存在");

        try {
            ServletOutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(file);
            io2os(is, os);
        } catch (IOException e) {
            logger.info("获取响应流失败" + e.getMessage(), e);
        }
        return null;
    }

    public boolean io2os(InputStream is, OutputStream os) {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        int len = 0;
        byte bytes[] = new byte[1024];
        try {
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            logger.info("写入流失败" + e.getMessage(), e);
            return false;
        }

        IOUtils.closeQuietly(bis);
        IOUtils.closeQuietly(bos);

        return true;
    }
}
