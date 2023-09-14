package com.hzn.reactive.framework.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
	@GetMapping ("/")
	public String index () {
		return "index";
	}
}
