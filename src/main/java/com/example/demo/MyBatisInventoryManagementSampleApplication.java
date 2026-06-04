package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class MyBatisInventoryManagementSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBatisInventoryManagementSampleApplication.class, args);
	}
	// ここで RestTemplate を作る！
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
