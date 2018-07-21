package com.quandoanh.priceformatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceFormatterApplication {

	/**
	 * This project is written in MVC model
	 * For the logic of the price formation, it will be covered in the PriceFormatterImpl class
	 */
	public static void main(String[] args) {
		SpringApplication.run(PriceFormatterApplication.class, args);
	}
}
