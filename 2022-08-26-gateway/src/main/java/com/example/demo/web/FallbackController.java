package com.example.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback/")
public class FallbackController {
	
	@GetMapping("serviceA")
	public String callbackA() {
		return "gateway callback message: service A down";
	}
	
	@GetMapping("serviceB")
	public String callbackB() {
		return "gateway callback message: service B down";
	}

}
