package com.hzn.framework.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@Value ("${test}")
	private String test;

	@GetMapping("/test")
	public String index (Model model) {
		model.addAttribute ("profile", test);
		return "index";
	}
}
