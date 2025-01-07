package tests;


import beans.Category;
import exceptions.CouponException;
import exceptions.CouponSystemException;
import facade_s.CustomerFacade;
import login_manager.LoginManager;
import login_manager.LoginManager.ClienType;

public class CustomerTest {

	public void testCustomer(LoginManager loginManager, CustomerFacade customerFacade) {
		
		
		System.out.println("-------------------------");
		System.out.println("customer test");
		System.out.println("-------------------------");
		System.out.println();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		// login as customer
		// ----------------------------
		
		System.out.println("login as customer:");
		
		
		try {
			customerFacade = (CustomerFacade) loginManager.login(ClienType.CUSTOMER, "cusMail1@mail", "cusPassword1");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		System.out.println("====================================");
		System.out.println();
		

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		// buy coupons
		// ----------------------------
		
		System.out.println("buy coupons:");
		
		try {
			customerFacade.buyCouponById(1000);
			System.out.println("coupon id=" + 1000 + " purchased.");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		try {
			customerFacade.buyCouponById(1010);
			System.out.println("coupon id=" + 1010 + " purchased.");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		try {
			customerFacade.buyCouponById(1015);
			System.out.println("coupon id=" + 1015 + " purchased.");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		System.out.println("====================================");
		System.out.println();
		

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		// view all of customer coupons
		// ----------------------------
		
		System.out.println("view all of customer coupons:");
		
		try {
			System.out.println(customerFacade.getAllCoupons());
		} catch (CouponException e) {
			System.err.println("error - " + e.getMessage());
		}
		

		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		// view customer coupons up to price
		// ----------------------------
		
		System.out.println("view customer coupons up to price (200):");
		
		try {
			System.out.println(customerFacade.getAllCouponsUpToPrice(200));
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
		
		
		// view customer coupons from a category
		// ----------------------------
		
		System.out.println("view customer coupons from a category (ELECTRICITY):");
		
		try {
			System.out.println(customerFacade.getAllCouponsFromCategory(Category.ELECTRICITY));
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
		
		
		// view customer details
		// ----------------------------
		
		System.out.println("view customer details");
		
		try {
			System.out.println(customerFacade.getCustomerDetails());
		} catch (CouponException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		System.out.println();
		System.out.println();
		
		

	}

}
