package com.h9.lottery;

import com.h9.common.StartBanner;
import com.h9.common.utils.MyMappingJackson2HttpMessageConverter;
import com.h9.lottery.config.ConstantConfig;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * LotteryApplication:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/2
 * Time: 14:26
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.h9.common","com.h9.lottery"})
@EnableSwagger2
@EnableJpaRepositories(basePackages="com.h9.common.db.repo")
@EntityScan(basePackages = "com.h9.common.db.entity")
@ServletComponentScan
@EnableScheduling
public class LotteryApplication  {

    static Logger logger = Logger.getLogger(LotteryApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LotteryApplication.class, args);
        Environment environment = applicationContext.getBean(Environment.class);
        ConstantConfig.init(environment);
        logger.debugv(StartBanner.BANNER);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new MyMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

}
