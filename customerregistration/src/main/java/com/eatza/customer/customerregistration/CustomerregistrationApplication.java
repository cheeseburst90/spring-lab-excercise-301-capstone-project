package com.eatza.customer.customerregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerregistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerregistrationApplication.class, args);
	}
	
	@Bean("restTemplate")
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}
