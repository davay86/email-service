package com.babcock.email.application;

import com.babcock.email.Application;
import com.babcock.email.config.CloudConfiguration;
import com.babcock.email.config.SecurityConfiguration;
import com.babcock.email.configuration.ConfigurationForTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ConfigurationForTest.class)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
