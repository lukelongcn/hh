package com.h9.api;


import lombok.Data;

/**
 * Created by itservice on 2017/11/2.
 */
public class Test {

    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(p);
    }

    @Data
    public static class Person{
        private int age;
    }
}
