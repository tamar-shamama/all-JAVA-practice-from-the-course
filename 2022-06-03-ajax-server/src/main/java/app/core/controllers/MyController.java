package app.core.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/my-controller")
@CrossOrigin (origins = "http://127.0.0.1:5500")
public class MyController {
	
	// the default of the browser is to block http responses
	// that do not come from the same server the html page 
	// came from. so, a html page can not send a request to
	// a different server - only to the server in which the 
	// html page itself written on. that called CORS policy.
	
	// in this program it creates a problem, because the html page
	// is found on the server "http://127.0.0.1:5500" (the live server
	// the vs code created), but is sends requests to the server
	// "http://localhost:8080" (tomcat server that this eclipse
	// creates). so according to CORS all responses to those requests
	// will be blocked.
	
	// to resolves this, the @CrossOrigin is used to allow responses
	// from different servers.
	
	@GetMapping("/greet")
	public String greet() {
		
		// wait a bit before response
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// rundom error
		if (Math.random() <= 0.8) {
			return "hello from my controller";
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad luck");
		}
		
	}

}
