package app.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.util.JwtUtil;
import app.core.util.JwtUtil.Client;
import app.core.util.JwtUtil.Client.ClientType;

@RestController
public class LoginController {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;

	
	/** returns a token when given the right email & password
	 * 
	 * @param email
	 * @param password
	 * @return String (admin token)
	 */
	@PutMapping("/{email}/{password}")
	public String adminLogin(@PathVariable String email, @PathVariable String password) {
		if (email.equals(this.adminEmail) && password.equals(this.adminPassword)) {
			return this.jwtUtil.generateToken(new Client(adminEmail, ClientType.ADMIN, 0));
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong admin credentials");
		}
	}

	
	
	/** register as a different client and get a unique token
	 * 
	 * @param client
	 * @return String
	 */
	@PostMapping("/register")
	public String register(@RequestBody Client client) {
		return this.jwtUtil.generateToken(client);
	}

}
