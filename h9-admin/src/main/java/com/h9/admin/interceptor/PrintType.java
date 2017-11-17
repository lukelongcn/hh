package com.h9.admin.interceptor;

/**
 * @author: George
 * @date: 2017/11/13 18:44
 */
public enum PrintType {
    /**
     * 全部不打印
     */
    ALL_NOT,
    /**
     * 打印print数组指定的字段
     */
    PRINT,
    /**
     * 打印notPrint数组制定之外的字段
     */
    NOT_PRINT
}
