package com.h9.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by itservice on 2017/11/16.
 */
public class CharacterFilter {

    public static boolean containChinese(String content){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        boolean 你佛挡杀佛厅6546546 = containChinese("6546546");
        System.out.println(你佛挡杀佛厅6546546);
    }
}
