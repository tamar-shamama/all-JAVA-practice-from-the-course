package app.core;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import app.core.controllers.LoginController;
import app.core.filters.AdminLoginFilter;
import app.core.filters.CompanyLoginFilter;
import app.core.filters.CustomerLoginFilter;
import app.core.util.JWT;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}
	

	
		@Bean
		public FilterRegistrationBean<AdminLoginFilter> adminLoginFilter(LoginController loginController, JWT jwt) {
			
			FilterRegistrationBean<AdminLoginFilter> adminFilterRegistrationBean = new FilterRegistrationBean<>();
			
			adminFilterRegistrationBean.setFilter(new AdminLoginFilter(jwt, loginController));
			
			adminFilterRegistrationBean.addUrlPatterns("/api/admin/*");
			
			return adminFilterRegistrationBean;
		}
		
		@Bean
		public FilterRegistrationBean<CompanyLoginFilter> companyLoginFilter(LoginController loginController, JWT jwt) {
			
			FilterRegistrationBean<CompanyLoginFilter> companyFilterRegistrationBean = new FilterRegistrationBean<>();
			
			companyFilterRegistrationBean.setFilter(new CompanyLoginFilter(jwt, loginController));
			
			companyFilterRegistrationBean.addUrlPatterns("/api/company/*");
			
			return companyFilterRegistrationBean;
		}
		
		
		@Bean
		public FilterRegistrationBean<CustomerLoginFilter> customerLoginFilter(LoginController loginController, JWT jwt) {
			
			FilterRegistrationBean<CustomerLoginFilter> customerFilterRegistrationBean = new FilterRegistrationBean<>();
			
			customerFilterRegistrationBean.setFilter(new CustomerLoginFilter(jwt, loginController));
			
			customerFilterRegistrationBean.addUrlPatterns("/api/customer/*");
			
			return customerFilterRegistrationBean;
		}
		
			
		

		
	
		
	

}
