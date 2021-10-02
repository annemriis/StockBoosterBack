package com.example.back.ito03022021backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@SpringBootApplication
public class Ito03022021BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ito03022021BackEndApplication.class, args);
    }

}
