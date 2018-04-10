package com.h9.admin.service;

import com.h9.common.utils.POIUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ln on 2018/4/9.
 */
@Service
public class POIService {

    private Logger logger = Logger.getLogger(this.getClass());

    private Workbook getReadWorkBookType(InputStream is) {
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

    public List<Object> excel2Object(InputStream inputStream, List<Object> resultList, Class clazz) {

        if (resultList == null) return resultList;

        Workbook workbook = null;
        try {
            workbook = getReadWorkBookType(inputStream);

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                POIUtils.RechargeBatchObject rechargeBatchObject = new POIUtils.RechargeBatchObject();
                Row row = sheet.getRow(rowNum);
                int columNumber = row.getPhysicalNumberOfCells();

                Object obj = null;
                try {
                    obj = clazz.newInstance();
                } catch (Exception e) {
                    logger.info(e);
                }

                for (int c = 0; c < columNumber; c++) {
                    Cell cell = row.getCell(c);
                    XSSFCell xssfCell = (XSSFCell) cell;
                    String rawValue = xssfCell.getRawValue();
                    try {
                        Field[] declaredFields = clazz.getDeclaredFields();
                        Field field = declaredFields[c];
                        field.setAccessible(true);
                        field.set(obj, rawValue);

                    } catch (Exception e) {
                        logger.info(e);
                    }
                }
                resultList.add(obj);
            }
            return resultList;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    @SuppressWarnings("Duplicates")
    private String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
