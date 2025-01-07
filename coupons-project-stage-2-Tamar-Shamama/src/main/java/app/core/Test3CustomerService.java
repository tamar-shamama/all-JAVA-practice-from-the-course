package app.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;

@SpringBootApplication
public class Test3CustomerService {

	
	public void CustomerTest(CustomerService customerService) {
		
		Coupon c1 = new Coupon(1, null, null, null, null, 0, 0, null, null, null, null);
		Coupon c2 = new Coupon(2, null, null, null, null, 0, 0, null, null, null, null);
		

		System.out.println();
		System.out.println("============================");
		System.out.println("CustomerService test");
		System.out.println("============================");
		System.out.println();
		
		
		// buy coupon
		
		System.out.println();
		System.out.println("----------------------------");
		System.out.println("buy coupon");
		System.out.println("----------------------------");
		System.out.println();
		
		try {
			customerService.buyCoupon(c1);
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		try {
			customerService.buyCoupon(c2);
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		
		// get methods
		
		System.out.println();
		System.out.println("----------------------------");
		System.out.println("get methods");
		System.out.println("----------------------------");
		System.out.println();
		
		try {
			System.out.println(customerService.getAllCouponsFromCustomer());
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		try {
			System.out.println(customerService.getAllCouponsFromCustomerByCategory(Category.ELECTRONICS));
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		try {
			System.out.println(customerService.getAllCouponsFromCustomerUpToPrice(10));
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		
		try {
			System.out.println(customerService.getCustomerDetailes());
		} catch (CouponSystemException e) {
			System.err.println("EROR - " + e.getMessage());
		}
		
		
		
	}

}
