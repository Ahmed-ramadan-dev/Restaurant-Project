package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
@EnableCaching
public class RestaurantProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantProjectApplication.class, args);
    }

}
