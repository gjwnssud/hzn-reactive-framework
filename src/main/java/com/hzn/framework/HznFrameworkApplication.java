package com.hzn.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HznFrameworkApplication {

	public static void main (String[] args) {
		SpringApplication.run (HznFrameworkApplication.class, args);
	}

}

