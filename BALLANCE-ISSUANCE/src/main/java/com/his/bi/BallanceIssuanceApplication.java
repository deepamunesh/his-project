package com.his.bi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BallanceIssuanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BallanceIssuanceApplication.class, args);
    }

}
