package com.h9.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SwaggerConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/26
 * Time: 16:12
 */
@Configuration
@EnableSwagger2
//@ComponentScan(basePackages ={"com.h9.admin","com.h9.common"})
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.h9.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("徽酒api文档")
                .description("版权所有翻版必究")
                .termsOfServiceUrl("https://api-dev-h9.thy360.com/")
                .version("1.0")
                .build();
    }
}
