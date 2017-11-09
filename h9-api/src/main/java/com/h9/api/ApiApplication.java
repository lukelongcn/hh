package com.h9.api;

import com.h9.common.StartBanner;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.*;

import static org.jboss.logging.Logger.getLogger;

@SpringBootApplication
@ComponentScan(basePackages = {"com.h9.common", "com.h9.api"})
@EnableSwagger2
@EnableJpaRepositories(basePackages = "com.h9.common.db.repo")
@EntityScan(basePackages = "com.h9.common.db.entity")
@ServletComponentScan
@EnableScheduling

public class ApiApplication {

    static Logger logger = getLogger(ApiApplication.class);
    public static String chinaPayKeyPath = null;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        logger.debugv(StartBanner.BANNER);

        chinaPayKeyPath = System.getProperty("user.dir");

        chinaPayKeyPath += "/certs/china-unionpay/MerPrK_808080211881410_20171102154758.key";
        logger.info("证书位置: "+chinaPayKeyPath);
        logger.info("存在: "+new File(chinaPayKeyPath).exists());
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
