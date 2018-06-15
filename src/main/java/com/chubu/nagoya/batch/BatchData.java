package com.chubu.nagoya.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
//@Component
//@EnableAutoConfiguration
public class BatchData  {
	
    public static void main(String[] args) {
        SpringApplication.run(MessagePop3.class);
     }
	
}
