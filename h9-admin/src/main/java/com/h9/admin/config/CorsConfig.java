package com.h9.admin.config;

import com.alibaba.fastjson.JSONObject;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


/**
 * Created by lmh on 2017/6/13.
 */
@Configuration
public class CorsConfig {

     Logger logger = Logger.getLogger(CorsConfig.class);

    @SuppressWarnings("Duplicates")
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        List<String> allowedOrigins = corsConfiguration.getAllowedOrigins();
        logger.debugv(JSONObject.toJSONString(allowedOrigins));
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
