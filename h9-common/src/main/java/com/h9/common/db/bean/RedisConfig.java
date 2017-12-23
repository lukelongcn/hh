package com.h9.common.db.bean;

import org.jboss.logging.Logger;
import org.redisson.Config;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * RedisConfig:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/23
 * Time: 13:45
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    private static Logger logger = Logger.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public  Redisson getRedisson(){
        Config config = new Config();
        config.useSingleServer().setAddress(hostName + ":" + port).setPassword(password);
        return Redisson.create(config);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
