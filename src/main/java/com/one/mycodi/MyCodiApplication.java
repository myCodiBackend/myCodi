package com.one.mycodi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class MyCodiApplication {


//test - 추후 삭제 요망


    public static void main(String[] args) {
        SpringApplication.run(MyCodiApplication.class, args);
    }

}
