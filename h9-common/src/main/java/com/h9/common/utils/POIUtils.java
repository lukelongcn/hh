package com.h9.common.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2018/1/11.
 */
public class POIUtils {

    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Users\\itservice\\Desktop\\test.xlsx";
        List<RechargeBatchObject> rechargeBatchObjects = readExcel(new FileInputStream(path));
        System.out.println(rechargeBatchObjects);
    }

    private static Workbook getReadWorkBookType(String filePath) {
        //xls-2003, xlsx-2007
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                //  抛出自定义的业务异常
                throw new RuntimeException("excel格式文件错误");
            }
        } catch (IOException e) {
            //  抛出自定义的业务异常
            throw new RuntimeException("excel格式文件错误");
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private static Workbook getReadWorkBookType(InputStream is) {
        //xls-2003, xlsx-2007
        try {
            return new XSSFWorkbook(is);

        } catch (IOException e) {
            //  抛出自定义的业务异常
            throw new RuntimeException("excel格式文件错误");
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    @Data
    @Accessors(chain = true)
    public static class RechargeBatchObject{
        private String phone;
        private BigDecimal money;
        private String remark;
        private String index;
    }

    private static Logger logger = Logger.getLogger(POIUtils.class);

    public static List<RechargeBatchObject> readExcel(InputStream inputStream) {
        Workbook workbook = null;

        List<RechargeBatchObject> list = new ArrayList<>();
        try {
            workbook = getReadWorkBookType(inputStream);

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                RechargeBatchObject rechargeBatchObject = new RechargeBatchObject();

                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(0);
                String phone = ((XSSFCell) cell).getRawValue();
                rechargeBatchObject.setPhone(phone);
                Cell cell2 = row.getCell(1);
                String money = ((XSSFCell) cell2).getRawValue();
                BigDecimal bigDecimal = new BigDecimal(0);
                try {
                     bigDecimal = new BigDecimal(money);
                } catch (Exception e) {
                    logger.info(e.getMessage(),e);
                    return null;
                }
                rechargeBatchObject.setMoney(bigDecimal);
                Cell cell3 = row.getCell(2);
                if(cell3 != null){
                    String remark = cell3.getStringCellValue();
                    rechargeBatchObject.setRemark(remark);
                }
                rechargeBatchObject.setIndex(rowNum + "");
                list.add(rechargeBatchObject);
            }
            return list;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }
}
