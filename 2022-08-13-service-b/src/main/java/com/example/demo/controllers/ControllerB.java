package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerB {
	
	@Value("${spring.cloud.consul.discovery.instanceId}")
	String mofaId;
	
	@GetMapping("/service/b")
	public String handleA() {
		return "from Service B: " + mofaId;
	}

}
