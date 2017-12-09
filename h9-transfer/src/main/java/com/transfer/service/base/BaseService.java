package com.transfer.service.base;

import com.h9.common.base.PageResult;
import com.transfer.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * BaseService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/27
 * Time: 14:37
 */
public abstract class BaseService<T> {
    
     Logger logger = Logger.getLogger(BaseService.class);

    public void trants(){
        trants(null,null,null);
    }

    public void trants(Integer startPage,Integer endPage,String path){
        Integer page = startPage;
        if(page == null){
            page = 1;
            startPage = 1;
        }
        int limit = 1000;
        Integer totalPage = endPage;

        if(StringUtils.isEmpty(path)) path = getPath();
        logger.debugv("path:{0}",path);
        BufferedWriter userWtriter = null;
        if(!StringUtils.isEmpty(path)) userWtriter = SqlUtils.getBuffer(path);

        PageResult<T> userInfoPageResult;
        do {
            userInfoPageResult =get(page, limit);
            if(endPage == null){
                totalPage = (int) userInfoPageResult.getTotalPage();
                endPage = totalPage;
            }
            List<T> userInfos = userInfoPageResult.getData();
            for (T userInfo : userInfos) {
                try {
                   getSql(userInfo,userWtriter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) (page-startPage+1) * 100 / (float) (totalPage-startPage+1);
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv(getTitle()+ rate + "% " + page + "/" + totalPage);
            page = page + 1;
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
        if(!StringUtils.isEmpty(path))   SqlUtils.close(userWtriter);
    }

    public abstract String getPath();

    public abstract PageResult get(int page,int limit);

    public abstract void getSql(T t,BufferedWriter writer) throws IOException;


    public abstract String getTitle();


    public void insertFieldInMid(StringBuilder sql, Object field){
        sql.append("'");
        if(field != null){
            sql.append(field);
        }
        sql.append("',");
    }

    public void insertFieldEnd(StringBuilder sql, Object field){
        sql.append("'");
        if(field != null){
            sql.append(field);
        }
        sql.append("'");

    }



}
