package com.babcock.email.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableCircuitBreaker
@ComponentScan("com.babcock.email")
@Import({SecurityConfiguration.class,})
public class CloudConfiguration {
}
