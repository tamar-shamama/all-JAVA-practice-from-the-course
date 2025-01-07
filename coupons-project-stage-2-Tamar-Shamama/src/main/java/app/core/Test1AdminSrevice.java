package app.core;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdministratorService;


public class Test1AdminSrevice {

	public void administratorTest(AdministratorService administratorService) {
		
		Company comp1 = new Company(0, "aaaa", "aaaa@a", "aaa", null);
		Company comp2 = new Company(0, "bbbb", "bbbb@b", "bbb", null);
		Company comp3 = new Company(0, "cccc", "cccc@c", "ccc", null);
		Company comp4 = new Company(0, "dddd", "dddd@d", "ddd", null);
		
		Customer cust1 = new Customer(0, "aa", "aa", "aa@a", "aa", null);
		Customer cust2 = new Customer(0, "bb", "bb", "bb@b", "bb", null);
		Customer cust3 = new Customer(0, "cc", "cc", "cc@c", "cc", null);
		Customer cust4 = new Customer(0, "dd", "dd", "dd@d", "dd", null);
		
		
		System.out.println();
		System.out.println("============================");
		System.out.println("AdministratorService test");
		System.out.println("============================");
		System.out.println();
		
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("add company");
		System.out.println("---------------------------");
		System.out.println();
		
		
		// add company
		
		try {
			administratorService.addCompany(comp1);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		try {
			administratorService.addCompany(comp2);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		try {
			administratorService.addCompany(comp3);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		try {
			administratorService.addCompany(comp4);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		
		// update company
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("update company");
		System.out.println("---------------------------");
		System.out.println();
		
		comp2 = new Company(2, "new", "new@new", "new", null);
		
		try {
			administratorService.updateCompany(comp2);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// get company
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("get company");
		System.out.println("---------------------------");
		System.out.println();
		
		
		Company comp = new Company(3, null, null, null, null);
		
		try {
			comp = administratorService.getCompanyById(3);
			System.out.println(comp);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// delete company
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("delete company");
		System.out.println("---------------------------");
		System.out.println();
		
		try {
			administratorService.deleteCompany(comp);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// get all companies
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("get all companies");
		System.out.println("---------------------------");
		System.out.println();
		
		try {
			System.out.println(administratorService.getAllCompanies());
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// add customers
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("add customers");
		System.out.println("---------------------------");
		System.out.println();
		
		try {
			administratorService.addCustomer(cust1);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		try {
			administratorService.addCustomer(cust2);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		try {
			administratorService.addCustomer(cust3);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		try {
			administratorService.addCustomer(cust4);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// update customer
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("update customer");
		System.out.println("---------------------------");
		System.out.println();
		
		cust3 = new Customer(3, "new", "new", "new@new", "new", null);
		
		try {
			administratorService.updateCustomer(cust3);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// get customer
		

		System.out.println();
		System.out.println("---------------------------");
		System.out.println("get customer");
		System.out.println("---------------------------");
		System.out.println();
		
		Customer cust = new Customer(4, null, null, null, null, null);
		
		try {
			cust = administratorService.getCustomerById(4);
			System.out.println(cust);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// delete customer
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("delete customer");
		System.out.println("---------------------------");
		System.out.println();
		
		try {
			administratorService.deleteCustomer(cust);
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}
		
		
		// get all customers
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("get all customers");
		System.out.println("---------------------------");
		System.out.println();
		
		try {
			System.out.println(administratorService.getAllCustomer());
		} catch (CouponSystemException e) {
			System.err.println("ERROR - " + e.getMessage());
		}

	}

}
