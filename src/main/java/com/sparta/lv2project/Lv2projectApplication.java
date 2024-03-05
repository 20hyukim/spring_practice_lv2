package com.sparta.lv2project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Lv2projectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lv2projectApplication.class, args);
    }

}
