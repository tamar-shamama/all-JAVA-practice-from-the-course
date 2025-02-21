package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import app.core.util.JwtUtil;
import app.core.util.JwtUtil.Client;
import app.core.util.JwtUtil.Client.ClientType;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		
		// test
		
//		JwtUtil jwtUtil = ctx.getBean(JwtUtil.class);
//
//		// generate token
//		Client client = new Client("admin@mail", ClientType.ADMIN, 101);
//		String token = jwtUtil.generateToken(client);
//		System.out.println(token);
//
//		// extract data
//		Client clientFromToken = jwtUtil.extractClient(token);
//		System.out.println(clientFromToken);
//
//		System.out.println("issued at: " + jwtUtil.getTokenIssuedAt(token));
//		System.out.println("expiration: " + jwtUtil.getTokenExp(token));

	}

}
