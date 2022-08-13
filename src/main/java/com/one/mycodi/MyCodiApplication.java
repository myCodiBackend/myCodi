package com.one.mycodi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyCodiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCodiApplication.class, args);
    }

}
