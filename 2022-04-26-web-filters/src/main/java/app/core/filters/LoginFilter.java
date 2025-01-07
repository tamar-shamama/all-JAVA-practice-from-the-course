package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.controllers.LoginController;

public class LoginFilter implements Filter {
	
	
	private LoginController loginController;
	private int hitCounter;

	// because the filter cannot be component, it needs 
	public LoginFilter(LoginController loginController) {
		super();
		this.loginController = loginController;
	}




	/**  a selective filter, check if user logged in  */
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("from login filter: " + ++this.hitCounter);
		
		if (this.loginController.isLoggedIn()) {
			chain.doFilter(request, response); // pass the request
			
		} else {   // block the request
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;  // casting
			httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "not logged in");
			  
		}
		
	}

}
