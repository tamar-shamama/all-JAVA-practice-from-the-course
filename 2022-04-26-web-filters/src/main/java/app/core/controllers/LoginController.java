package app.core.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	private String password = "admin";
	private boolean loggedIn;
	
	/** check password
	 * @param password
	 * @return boolean
	 */
	@PutMapping("/login/{password}")
	public boolean login(@PathVariable String password) {
		return this.loggedIn = password.equals(this.password);
	}
	
	@PutMapping
	public boolean logout() {
		return this.loggedIn = false;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	} 
	
	

}
 
