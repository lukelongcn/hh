package com.transfer;

import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.io.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SqlUtils:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/20
 * Time: 17:09
 */
public class SqlUtils {
    static Logger logger = Logger.getLogger(SqlUtils.class);

    public static BufferedWriter getBuffer(String path){
        try {
            File file =new File(path);
            File parentFile = file.getParentFile();
            if (parentFile!=null&&!parentFile.exists()) {
                file.mkdirs();
            }
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

    public static String concatDate(Date date){
        return concatDate(date, new Date());
    }

    public static String concatDate(Date date, Date defaultDate){
        if(date==null){
            if(defaultDate!=null){
                date = defaultDate;
            }else{
                return "null,";
            }
        }  return "'" + DateUtil.formatDate(date, DateUtil.FormatType.SECOND) + "',";

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
            logger.debugv("'="+str);
            str = str.replaceAll("'", "\\\'");
            logger.debugv("'="+str);
        }
        if(str.contains("\'")){
            logger.debugv("'="+str);
            str = str.replaceAll("\'", "\\\'");
            logger.debugv("'="+str);
        }
        if(str.contains("\\")){

            logger.debugv("\\="+str);
            str = str.replaceAll("\\\\","\\\\\\\\");
            logger.debugv("\\="+str);
        }

        return str;
    }
}
