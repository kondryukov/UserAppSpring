package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "org.example")
@EntityScan(basePackages = "org.example.domain")
public class UserAppSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAppSpringApplication.class, args);
    }
}
