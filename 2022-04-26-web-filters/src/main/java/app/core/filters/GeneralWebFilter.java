package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

// can be a component because it catches every single http request.
// a selective filter cannot be a class component.

@Component
public class GeneralWebFilter implements Filter {
	
	private int hitCounter;

	
	
	/** Catches every http request, does something (prints),
	 * than pass the request
	 */
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("from general filter: " + ++this.hitCounter);
		
		chain.doFilter(request, response); // pass the request

	}

}
