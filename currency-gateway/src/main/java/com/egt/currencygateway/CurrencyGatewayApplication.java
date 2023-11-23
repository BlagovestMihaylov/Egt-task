package com.egt.currencygateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CurrencyGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyGatewayApplication.class, args);
	}

}
