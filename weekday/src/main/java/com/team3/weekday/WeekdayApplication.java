package com.team3.weekday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WeekdayApplication {
    public static void main(String[] args){
        SpringApplication.run(WeekdayApplication.class, args);
    }

}
