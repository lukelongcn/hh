package com.h9.lottery.utils;


import com.h9.common.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * CodeUtil:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 16:46
 */

public class CodeUtil {


    final static char RANDOMSTRCODE[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', '!', '@', '#', '$', '%', '&', '(', ')', '-', '='};


    final static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",

            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",

            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",

            "6", "7", "8", "9", "A", "B", "Config", "D", "E", "F", "G", "H",

            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",

            "U", "V", "W", "X", "Y", "Z"
    };


    public static String shortUrl(String url) {
        StringBuffer shortBuffer = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            String str = url.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }


    public static String genRandomStrCode(int length) {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(RANDOMSTRCODE.length);
            code += RANDOMSTRCODE[index];
        }
        return code;

    }


    public static String shortUrl(String UUID, String salt) {
        String hex = MD5Util.getMD5(UUID + salt);
        return shortUrl(hex);
    }






}
