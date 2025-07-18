package com.serinautoupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class SerinAutoUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerinAutoUploadApplication.class, args);
    }

}
