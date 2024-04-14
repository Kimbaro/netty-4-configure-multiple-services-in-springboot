package com.bluechip.bcworkshinhanmessageq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BcZoneCastApplication {
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(BcZoneCastApplication.class, args);
    }
}

