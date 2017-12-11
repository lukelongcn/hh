package com.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.h9.common", "com.transfer"})
@EnableSwagger2
@ServletComponentScan
public class TransferApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TransferApplication.class, args);
    }
}
