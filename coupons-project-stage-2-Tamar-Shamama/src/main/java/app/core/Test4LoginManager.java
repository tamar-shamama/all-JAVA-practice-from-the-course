package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdministratorService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.services.LoginManager;
import app.core.services.LoginManager.ClientType;

@SpringBootApplication
public class Test4LoginManager {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Test4LoginManager.class, args);
		
		
		LoginManager loginManager = context.getBean(LoginManager.class);
		
		AdministratorService administratorService = null;
		CompanyService companyService = null;
		CustomerService customerService = null;
		
		// login as admin
		
		try {
			administratorService = (AdministratorService) loginManager.login(ClientType.ADMINISTRATOR, "adminn@admin", "admin");
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		try {
			System.out.println(administratorService.getAllCompanies());
		} catch (Exception e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		
		// login as company
		
		try {
			companyService = (CompanyService) loginManager.login(ClientType.COMPANY, "aaaa@a", "aaa");
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		try {
			System.out.println(companyService.getAllCompanyCoupons());
		} catch (Exception e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		
		// login as customer
		
		try {
			customerService = (CustomerService) loginManager.login(ClientType.CUSTOMER, "aa", "aa");
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		try {
			System.out.println(customerService.getAllCouponsFromCustomer());
		} catch (Exception e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		
		
	}

}
