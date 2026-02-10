package com.unburden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class UnburdenApplication {

    static void main(String[] args) {
        SpringApplication.run(UnburdenApplication.class, args);
    }

}