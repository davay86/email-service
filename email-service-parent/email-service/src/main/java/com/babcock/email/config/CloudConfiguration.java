package com.babcock.email.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@EnableCircuitBreaker
@ComponentScan("com.babcock.email")
@Import({SecurityConfiguration.class})
public class CloudConfiguration {
}
