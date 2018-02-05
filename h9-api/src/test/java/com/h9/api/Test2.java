package com.h9.api;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test2 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode("https%3A%2F%2Fweixin-dev-h9.thy360.com%2Fuser%2Ftemp%2Fredirect%3Fid%3D49ccadb1776c44f681cbf4350c967035", "UTF-8");
        System.out.println(decode);

    }
}
