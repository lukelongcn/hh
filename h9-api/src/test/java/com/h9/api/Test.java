package com.h9.api;


import lombok.Data;

/**
 * Created by itservice on 2017/11/2.
 */
public class Test {

    public static void main(String[] args) {
        int time = 60;

        try {
            Thread.sleep(time *1000);
            System.out.println(time +"时间到了。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Data
    public static class Person{
        private int age;
    }
}
