package com.h9.api;

import com.h9.common.StartBanner;
import org.apache.commons.io.IOUtils;
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
import java.util.Date;

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
    public static String chinaPayKey = null;
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        logger.debugv(StartBanner.BANNER);

        File keyPath = new File("/", "MerPrK_808080211881410_20171102154758.key");
        keyPath.setWritable(true, false);
        logger.info("证书位置:"+keyPath.getAbsolutePath());
        chinaPayKey = keyPath.getAbsolutePath();
        if (!keyPath.exists()) {
            try {

                InputStream is = ApiApplication.class.getClassLoader().getResourceAsStream("MerPrK_808080211881410_20171102154758.key");
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(keyPath));
                int len = 0;
                byte[] bytes = new byte[1024];

                while ((len = is.read(bytes)) != -1) {

                    bos.write(bytes);
                    bos.flush();

                }
                IOUtils.closeQuietly(bos);

            } catch (IOException e) {
                logger.info("存放证书出错", e);
            }
        }


    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
