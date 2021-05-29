package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCircuitBreaker
@EnableAutoConfiguration
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}
