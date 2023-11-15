package com.icebear2n2.techheaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechHeavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechHeavenApplication.class, args);
    }

}
