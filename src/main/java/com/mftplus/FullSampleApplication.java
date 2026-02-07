package com.mftplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FullSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullSampleApplication.class, args);
    }

}
