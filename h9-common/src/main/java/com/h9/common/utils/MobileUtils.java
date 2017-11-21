package com.h9.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by itservice on 2017/11/21.
 */
public class MobileUtils {

    /**
     * 验证手机号
     * @author lipw
     * @date   2017年4月5日上午11:34:07
     * @param mobiles
     * 手机号码
     * @return
     * 有效返回true,否则返回false
     */
    public static boolean isMobileNO(String mobiles) {

        if(StringUtils.isBlank(mobiles)) return false;
        // Pattern p =
        // Pattern.compile("^((147)|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static void main(String[] args) {

        boolean mobileTel = isMobileNO("14725836914");
        System.out.println(mobileTel);
    }
}
