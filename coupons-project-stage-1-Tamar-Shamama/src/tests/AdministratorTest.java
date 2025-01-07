package tests;

import java.util.List;

import beans.Company;
import beans.Customer;
import exceptions.CompanyException;
import exceptions.CouponSystemException;
import exceptions.CustomerException;
import facade_s.AdminFacade;
import login_manager.LoginManager;
import login_manager.LoginManager.ClienType;

public class AdministratorTest {

	
	public void testAdministrator(LoginManager login_manager, AdminFacade adminFacade) {
		
		
		System.out.println();
		System.out.println("-------------------------");
		System.out.println("administrator test");
		System.out.println("-------------------------");
		System.out.println();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// login with wrong password
		// ----------------------------
		
		System.out.println("login with wrong password:");
		
		try {
			adminFacade = (AdminFacade) login_manager.login(ClienType.ADMINISTRATOR, "admim@admin", "aaa");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		System.out.println("returned object: " + adminFacade);
		
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// login with correct password
		// -------------------------------
		
		System.out.println("login with correct password:");
		
		try {
			adminFacade = (AdminFacade) login_manager.login(ClienType.ADMINISTRATOR, "admim@admin", "admin");
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		System.out.println("returned object: " + adminFacade);
		
		
		System.out.println("====================================");
		System.out.println();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		// add companies
		// ---------------
		
		System.out.println("add companies:");
		
			
			for (int i = 1; i <= 5; i++) {
				
				try {
					Company company = createCompany();
					adminFacade.addCompany(company);
					System.out.println("company id = " + company.getCompany_id() + " added");
					
				} catch (CouponSystemException e) {
					System.err.println("error - " + e.getMessage());
				}
			}
			
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		// view all companies
		// -----------------------
		
		System.out.println("view all companies");
		
		try {
			List<Company> list = adminFacade.getAllCompanies();
			System.out.println(list);
		} catch (CompanyException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// delete companies
		// --------------------
		System.out.println("delete companies");
		
		Company company4 = null;
		try {
			company4 = adminFacade.getCompanyById(4);
			adminFacade.deletCompany(company4);
			System.out.println("company id = " + company4.getCompany_id() + " deleted.");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		Company company5 = null;
		try {
			company5 = adminFacade.getCompanyById(5);
			adminFacade.deletCompany(company5);
			System.out.println("company id = " + company5.getCompany_id() + " deleted.");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// update companies
		// ------------------
		
		System.out.println("update companies:");
		
		Company company2 = new Company(2, "companyName2", "newMail2", "newPass2", null);
		Company company3 = new Company(3, "companyName3", "newMail3", "newPass3", null);
		
		
		try {
			adminFacade.updateCompany(company2);
			System.out.println("company id = " + company2.getCompany_id() + " updated");
		} catch (CompanyException e) {
			System.err.println("error - " + e.getMessage());
		}
		try {
			adminFacade.updateCompany(company3);
			System.out.println("company id = " + company3.getCompany_id() + " updated");
		} catch (CompanyException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// view all companies
		// ------------------------
		System.out.println("view all companies");
		
		try {
			List<Company> list = adminFacade.getAllCompanies();
			System.out.println(list);
		} catch (CompanyException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// add customers
		// ---------------
		System.out.println("add customers:");
		System.out.println();
		
		
		
		for (int i = 1; i <= 5; i++) {
			
			try {
				Customer customer = createCustomer();
				adminFacade.addCustomer(customer);
				System.out.println("customer id = " + customer.getCustomer_id() + " added");
				
			} catch (CouponSystemException e) {
				System.err.println("error - " + e.getMessage());
			}
		}
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// view all customers
		// ---------------------
		System.out.println("view all customers:");
		
		try {
			List<Customer> list = adminFacade.getAllCustomers();
			System.out.println(list);
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		// update customers
		// ---------------------
		System.out.println("update customers:");
		System.out.println();
		
		Customer customer1 = new Customer(101, "newName1", "newSecName1", "newMail1", "newPass1", null);
		Customer customer2 = new Customer(102, "newName2", "newSecMane2", "newMail2", "newPass2", null);
		
		try {
			adminFacade.updateCustomer(customer1);
			System.out.println("customer id=" + customer1.getCustomer_id() + " updated");
		} catch (CustomerException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		try {
			adminFacade.updateCustomer(customer2);
			System.out.println("customer id=" + customer2.getCustomer_id() + " updated");
		} catch (CustomerException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		

		
		
		// delete customers
		// ---------------------
		System.out.println("delete customers:");
		
		
		Customer customer4;
		try {
			customer4 = adminFacade.getCustomerById(103);
			adminFacade.deleteCustomer(customer4);
			System.out.println("customer id=" + customer4.getCustomer_id() + " deleted");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		Customer customer5;
		try {
			customer5 = adminFacade.getCustomerById(104);
			adminFacade.deleteCustomer(customer5);
			System.out.println("customer id=" + customer5.getCustomer_id() + " deleted");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		// view all customers
		// ---------------------
		System.out.println("view all customers:");
		try {
			List<Customer> list = adminFacade.getAllCustomers();
			System.out.println(list);
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		System.out.println();
		System.out.println();
	}
	
	
	
	
	private static int compSerialId = 1;
	private static int compSerialName = 1;
	private static int compSerialMail = 1;
	private static int compSerialPassword = 1;
	
	private static int customerSerialId = 100;
	private static int customerSerial1Name = 1;
	private static int customerSerial2Name = 1;
	private static int customerSerialMail = 1;
	private static int customerSerialPassword = 1;
	
	
	
	private static Company createCompany() {
		Company company = new Company(compSerialId++, "companyName" + compSerialName++, "companyMail" + (compSerialMail++) +"@mail", "companyPassword" + compSerialPassword++, null);
		return company;
	}
	
	private static Customer createCustomer() {
		Customer customer = new Customer(customerSerialId++,"cusName" + customerSerial1Name++ ,"cusLastName" + customerSerial2Name++, "cusMail" + (customerSerialMail++) + "@mail", "cusPassword" + customerSerialPassword++, null);
		return customer;
	}

	
	
	
	
	
}
