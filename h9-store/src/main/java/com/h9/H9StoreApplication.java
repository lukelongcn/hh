package com.h9;

import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class H9StoreApplication {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(H9StoreApplication.class);
        ConfigurableApplicationContext context = SpringApplication.run(H9StoreApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        String enviroment = environment.getProperty("h9.current.envir");
        logger.info("当前环境：" + enviroment);
    }
}
