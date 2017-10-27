package com.h9.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * SwaggerConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/26
 * Time: 16:12
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.h9.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("徽酒api文档")
                .description("版权所有翻版必究")
                .termsOfServiceUrl("http://http://localhost:6305/")
//                .termsOfServiceUrl("https://api-dev-h9.thy360.com/")
                .version("1.0")
                .build();
    }
}
