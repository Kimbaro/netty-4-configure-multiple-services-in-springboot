package com.template.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootStartUpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartUpApplication.class, args);
    }
}

