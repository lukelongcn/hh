package com.transfer;

import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.io.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * SqlUtils:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/20
 * Time: 17:09
 */
public class SqlUtils {
    static Logger logger = Logger.getLogger(SqlUtils.class);

    public static BufferedWriter getBuffer(String path){
        try {
            File file =new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            BufferedWriter csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            return csvWtriter;
        } catch (Exception e) {
            logger.debugv(e.getMessage(),e);
        }
        return null;
    }

    public static void close(BufferedWriter bufferedWriter){
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String concatSql(String sql){
       return concatSql(sql, false);
    }

    public static String concatDate(){
        return "'" + DateUtil.formatDate(new Date(), DateUtil.FormatType.SECOND) + "',";
    }



    public static String concatSql(String sql,boolean isLast){
        String result = "";
        if(!StringUtils.isEmpty(sql)){
            result = "'" + fomart(sql) +"'";
        }else{
            result =  "null";
        }
        if(isLast){
            return result;
        }else{
            return result + ",";
        }
    }

    public static String fomart(String str){
        if(str.contains("'")){
            str = str.replace("'", "\\'");
        }
        return str;
    }
}