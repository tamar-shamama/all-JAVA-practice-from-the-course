package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.controllers.LoginController;
import app.core.util.JWT;

public class CustomerLoginFilter implements Filter {
	
	private JWT jwt;
	
	
	public CustomerLoginFilter(JWT jwt, LoginController loginController) {
		super();
		this.jwt = jwt;
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String token = httpServletRequest.getHeader("token");
		
		
		try {
			
			if (token == null) {
				httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in - no token given");
				
			} else if (jwt.isTokenExpired(token)) {
				httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in - token expired");
				
			} else if (!jwt.isTokenCustomer(token)) {
				httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not authorized for this request");
				
			} else {
				chain.doFilter(request, response);
			}
			
		} catch (Exception e) {
			httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in - invalid token");
		}
		
				
		
	}
			
			
	
	
	
	
	

}
