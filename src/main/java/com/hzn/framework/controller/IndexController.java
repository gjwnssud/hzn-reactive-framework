package com.hzn.framework.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Controller
public class IndexController {
	@Value ("${test}")
	private String test;

	@GetMapping("/")
	public String index (Model model) {
//		Flux<String> data = Flux.fromIterable (List.of (test)).delayElements (Duration.ofSeconds (2));
//		model.addAttribute ("profiles", new ReactiveDataDriverContextVariable (data));
		model.addAttribute ("profile", test);
		return "index";
	}
}
