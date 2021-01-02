package com.smile24es.resource.management.conf;

import com.smile24es.resource.management.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInitializer {

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> storageService.init();
    }
}
