package app.core;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import app.core.services.AdministratorService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.services.LoginManager;
import app.core.services.LoginManager.ClientType;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);) {
			
			
			LoginManager loginManager = context.getBean(LoginManager.class);
			
			AdministratorService administratorService = null;
			CompanyService companyService = null;
			CustomerService customerService = null;
			
			Test1AdminSrevice t1 = new Test1AdminSrevice();
			Test2CompanyService t2 = new Test2CompanyService();
			Test3CustomerService t3 = new Test3CustomerService();
			
			// login as administrator
			
			try {
				administratorService = (AdministratorService) loginManager.login(ClientType.ADMINISTRATOR, "admin@admin", "admin");
				t1.administratorTest(administratorService);
			} catch (Exception e) {
				System.err.println("EROR - " + e.getMessage());
			}
			
			
			// login as company id=1
			
			try {
				companyService = (CompanyService) loginManager.login(ClientType.COMPANY, "aaaa@a", "aaa");
				t2.companyTest(companyService);
			} catch (Exception e) {
				System.err.println("EROR - " + e.getMessage());
			}
			
			
			// login as customer id=1
			
			try {
				customerService = (CustomerService) loginManager.login(ClientType.CUSTOMER, "aa@a", "aa");
				t3.CustomerTest(customerService);
			} catch (Exception e) {
				System.err.println("EROR - " + e.getMessage());
			}
			
			
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			
			
		} catch (Exception e) {
			System.err.println("EROR - " + e.getMessage());
		} 
			
		

		
	
		
	}

}
