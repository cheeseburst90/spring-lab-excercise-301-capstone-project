package com.eatza.notify.communique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CommuniqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommuniqueApplication.class, args);
    }

}
