package com.transfer;

import com.transfer.db.entity.CardInfo;
import com.transfer.db.repo.CardInfoRepository;
import com.transfer.service.CallBackService;
import com.transfer.service.CardInfoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
