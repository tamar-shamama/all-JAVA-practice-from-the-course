package tests;

import facade_s.AdminFacade;
import facade_s.CompanyFacade;
import facade_s.CustomerFacade;
import login_manager.LoginManager;
import system.AppSystem;

public class Test {
	
	AppSystem appSystem = new AppSystem();
	
	AdministratorTest administratorTest = new AdministratorTest();
	CompanyTest companyTest = new CompanyTest();
	CustomerTest customerTest = new CustomerTest();
	
	AdminFacade adminFacade = null;
	CompanyFacade companyFacade = null;
	CustomerFacade customerFacade = null;
	
	
	public void testAll() {
		
		
		
		LoginManager login_manager = appSystem.startApp();
		
		administratorTest.testAdministrator(login_manager, adminFacade);
		companyTest.testCompany(login_manager, companyFacade);
		customerTest.testCustomer(login_manager, customerFacade);
		
		
		// close:
		appSystem.closeApp();
		System.out.println("bye");
		
	}

}
