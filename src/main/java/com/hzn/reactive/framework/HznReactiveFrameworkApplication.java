package com.hzn.reactive.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class HznReactiveFrameworkApplication {

	public static void main (String[] args) {
		SpringApplication.run (HznReactiveFrameworkApplication.class, args);
	}
}

