package com.h9.api;

import com.h9.common.StartBanner;
import com.h9.common.common.ConstantConfig;
import com.h9.common.utils.MyMappingJackson2HttpMessageConverter;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.jboss.logging.Logger.getLogger;

@SpringBootApplication
//@Configuration //22s
//@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.h9.common", "com.h9.api"})
@EnableSwagger2
@EnableJpaRepositories(basePackages = "com.h9.common.db.repo")
@EntityScan(basePackages = "com.h9.common.db.entity")
@ServletComponentScan
@EnableScheduling
public class ApiApplication {

    static Logger logger = getLogger(ApiApplication.class);
    public static String chinaPayKeyPath = null;
    public static String productEvirPayKeyPath = null;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);
        logger.debugv(StartBanner.BANNER);

        chinaPayKeyPath = System.getProperty("user.dir");
        productEvirPayKeyPath = chinaPayKeyPath + "/certs/china-unionpay/MerPrK_808080211303539_20160518170403.key";
        //测试证书
        chinaPayKeyPath += "/certs/china-unionpay/MerPrK_808080211881410_20171102154758.key";

        logger.info("私钥位置: " + chinaPayKeyPath);
        boolean exists = new File(chinaPayKeyPath).exists();
        boolean productPayKeyExist = new File(productEvirPayKeyPath).exists();
        logger.info("for test 存在: " + exists);
        logger.info("for product 存在: " + productPayKeyExist);

        Environment environment = context.getBean(Environment.class);
        String enviroment = environment.getProperty("h9.current.envir");
        ConstantConfig.init(context,environment);
        logger.info("当前环境：" + enviroment);

    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new MyMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

}
