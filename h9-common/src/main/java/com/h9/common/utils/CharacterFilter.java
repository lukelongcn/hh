package com.h9.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by itservice on 2017/11/16.
 */
public class CharacterFilter {

    /**
     * description: 是否包含中文
     */
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

    /**
     * description: 隐藏掉信息卡号部分数字
     */
    public static String hiddenBankCardInfo(String no){
        StringBuilder sbNo = new StringBuilder();
        sbNo.append(no.substring(0, 4));
        sbNo.append("**** **** ****");
        sbNo.append(no.substring(no.length() - 4,no.length()));
        return sbNo.toString();
    }
}
