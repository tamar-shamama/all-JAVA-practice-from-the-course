package login_manager;

import exceptions.CouponSystemException;
import facade_s.AdminFacade;
import facade_s.ClientFacade;
import facade_s.CompanyFacade;
import facade_s.CustomerFacade;

public class LoginManager {
	
	private static LoginManager instance = new LoginManager();

	private LoginManager() {
	}

	public static LoginManager getInstance() {
		return instance;
	}
	
	
	public ClientFacade login(ClienType clienType, String email, String password) throws CouponSystemException {
		
		boolean logged;
		String client = clienType.toString();
		
		switch (client) {
		
		case "ADMINISTRATOR":
			AdminFacade adminFacade = new AdminFacade();
			logged = adminFacade.login(email, password);
			if (logged) {
				return adminFacade;
			} else {
				return null;
			}
			
		case "COMPANY":
			CompanyFacade companyFacade = new CompanyFacade();
			logged = companyFacade.login(email, password);
			if (logged) {
				return companyFacade;
			} else {
				return null;
			}
		
		case "CUSTOMER":
			CustomerFacade customerFacade = new CustomerFacade();
			logged = customerFacade.login(email, password);
			if (logged) {
				return customerFacade;
			} else {
				return null;
			}
			
		default:
			return null;
		
		}
	}


	public enum ClienType {
		ADMINISTRATOR, COMPANY, CUSTOMER;
	}
	
	
}
