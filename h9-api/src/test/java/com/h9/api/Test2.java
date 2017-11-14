package com.h9.api;

import org.springframework.beans.BeanUtils;

/**
 * Created by itservice on 2017/11/14.
 */
class Person {
    private String name;
    private String age;

    public static class PersonBuilder {

        private String name;
        private String age;

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setAge(String age) {
            this.age = age;
            return this;
        }

        public Person builder() {
            Person person = new Person();
            BeanUtils.copyProperties(PersonBuilder.this, person);
            return person;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

public class Test2 {

    public static void main(String[] args) {
        Person person = new Person.PersonBuilder().setAge("1").setName("ldh").builder();
        System.out.println(person.getName());
        System.out.println(person.getAge());
    }
}
