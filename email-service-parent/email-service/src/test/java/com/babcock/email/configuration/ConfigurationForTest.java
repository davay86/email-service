package com.babcock.email.configuration;

import com.babcock.email.Application;
import com.babcock.email.config.CloudConfiguration;
import com.babcock.email.config.SecurityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.context.annotation.FilterType.REGEX;

@TestConfiguration
@Profile("test")
@ComponentScan(
        basePackages="com.babcock.email",
        excludeFilters = {@ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = {
                Application.class,CloudConfiguration.class,SecurityConfiguration.class})
        })
public class ConfigurationForTest {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}