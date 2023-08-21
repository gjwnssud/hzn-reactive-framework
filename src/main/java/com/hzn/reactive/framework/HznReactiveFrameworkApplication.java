package com.hzn.reactive.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HznReactiveFrameworkApplication {

	public static void main (String[] args) {
		SpringApplication.run (HznReactiveFrameworkApplication.class, args);
	}

}

