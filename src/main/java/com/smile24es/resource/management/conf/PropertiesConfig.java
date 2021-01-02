package com.smile24es.resource.management.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * The configuration file to get application configs
 */
@Configuration
@PropertySource({"classpath:ApplicationResources.properties",
        "classpath:resource-api/resource-api.properties"})
public class PropertiesConfig {

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource setMessageSourceBean() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("ApplicationResources");
        return resourceBundleMessageSource;
    }

}
