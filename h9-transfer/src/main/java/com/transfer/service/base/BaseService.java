package com.transfer.service.base;

import com.h9.common.base.PageResult;
import com.transfer.SqlUtils;
import org.jboss.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * BaseService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/27
 * Time: 14:37
 */
public abstract class BaseService<T> {
    
     Logger logger = Logger.getLogger(BaseService.class);

    public void trants(){
        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<T> userInfoPageResult;
        BufferedWriter userWtriter = SqlUtils.getBuffer(getPath());
        do {
            page = page + 1;
            userInfoPageResult =get(page, limit);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<T> userInfos = userInfoPageResult.getData();
            for (T userInfo : userInfos) {
                try {
                   getSql(userInfo,userWtriter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv(getTitle()+ rate + "% " + page + "/" + totalPage);
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
        SqlUtils.close(userWtriter);
    }

    public abstract String getPath();

    public abstract PageResult get(int page,int limit);

    public abstract void getSql(T t,BufferedWriter userWtriter) throws IOException;


    public abstract String getTitle();




}
