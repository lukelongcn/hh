package com.h9.api;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Ln on 2018/4/20.
 */
public class TestNoEnvirement {
    public static void main(String[] args) {
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    RestTemplate restTemplate = new RestTemplate();
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String result = restTemplate.getForObject("http://localhost:6305/h9/api/lock", String.class);
                    if(result.equals("true")){
                        System.out.println(result);
                    }
                }
            });
            t.start();
        }
        start.countDown();
    }
}
