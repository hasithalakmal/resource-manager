package com.smile24es.resource.management;

import com.smile24es.resource.management.conf.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableConfigurationProperties(StorageProperties.class)
public class Smile24esResourceManagemnetApp {

    public static void main(String[] args) {
        SpringApplication.run(Smile24esResourceManagemnetApp.class, args);
    }
}
