package app.core.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jb")
public class MyController {
	
	@GetMapping("/hi")
	public String hello() {
		return "Hello Docker: " + Math.random();
	}

}
