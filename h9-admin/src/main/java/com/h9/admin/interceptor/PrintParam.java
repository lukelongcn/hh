package com.h9.admin.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: George
 * @date: 2017/11/2 17:20
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintParam {
    String[] print() default {};
    String[] notPrint() default {};
    PrintType printType() default PrintType.ALL_NOT;
}
