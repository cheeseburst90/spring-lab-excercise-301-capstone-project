package com.eatza.restaurantsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantsearchserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsearchserviceApplication.class, args);
    }
}
