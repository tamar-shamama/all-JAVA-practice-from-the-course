package app.core.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class LoginManager {
	
	@Autowired
	AdministratorService administratorService;
	@Autowired
	CompanyService companyService;
	@Autowired
	CustomerService customerService;
	
	
	public ClientService login (ClientType clientType, String email, String password) throws CouponSystemException {
		
		boolean logged = false;
		
		switch (clientType) {
		
		case ADMINISTRATOR:
			logged = administratorService.login(email, password);
			if (logged) {
				return administratorService;
			} else {
				throw new CouponSystemException("login failed - email and/or password incorrect");
			}
			
			
		case COMPANY:
			logged = companyService.login(email, password);
			if (logged) {
				return companyService;
			} else {
				throw new CouponSystemException("login failed - email and/or password incorrect");
			}
			
		case CUSTOMER:
			logged = customerService.login(email, password);
			if (logged) {
				return customerService;
			} else {
				throw new CouponSystemException("login failed - email and/or password incorrect");
			}
			
		default:
			throw new CouponSystemException("login failed - try again");

				
		}
	}
	
	
	
	
	
	public enum ClientType {
		ADMINISTRATOR, COMPANY, CUSTOMER;
	}

}
