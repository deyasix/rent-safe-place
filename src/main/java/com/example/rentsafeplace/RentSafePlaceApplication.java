package com.example.rentsafeplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RentSafePlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentSafePlaceApplication.class, args);
    }

}
