package com.h9.admin;

import com.h9.common.StartBanner;
import com.h9.common.utils.MyMappingJackson2HttpMessageConverter;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.h9.common", "com.h9.admin"})
@EnableJpaRepositories(basePackages={"com.h9.common.db.repo"})
@EntityScan(basePackages = {"com.h9.common.db.entity","com.h9.admin.model.po"})
@AutoConfigurationPackage
@EnableScheduling
public class AdminApplication {



    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);
        Logger logger = Logger.getLogger(AdminApplication.class);
        logger.debugv(StartBanner.BANNER);


        Environment environment = run.getBean(Environment.class);
        String enviroment = environment.getProperty("h9.current.envir");
        logger.info("当前环境："+enviroment);
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new MyMappingJackson2HttpMessageConverter());
        return restTemplate;


    }

}
