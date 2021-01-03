package com.smile24es.resource.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class Smile24esResourceManagemnetApp {

    public static void main(String[] args) {
        SpringApplication.run(Smile24esResourceManagemnetApp.class, args);
    }
}
