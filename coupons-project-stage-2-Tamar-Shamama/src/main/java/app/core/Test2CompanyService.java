package app.core;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;

@SpringBootApplication
public class Test2CompanyService {

	
	public void companyTest(CompanyService companyService) {
	
	Coupon c1 = new Coupon(0, "aaa", "aaa", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 10, 9.99, "aaa", Category.ELECTRONICS, null, null);
	Coupon c2 = new Coupon(0, "bbb", "bbb", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 10, 9.99, "bbb", Category.FOOD, null, null);
	Coupon c3 = new Coupon(0, "ccc", "ccc", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 10, 9.99, "ccc", Category.VACATION, null, null);
	Coupon c4 = new Coupon(0, "ddd", "ddd", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 10, 9.99, "ddd", Category.RESTAURANT, null, null);
	Coupon c5 = new Coupon(0, "eee", "eee", LocalDate.of(2022, 1, 1), LocalDate.of(2020, 1, 1), 10, 9.99, "eee", Category.ELECTRONICS, null, null);
	
	System.out.println();
	System.out.println("============================");
	System.out.println("CompanyService test");
	System.out.println("============================");
	System.out.println();
	
	
	// add coupon
	
	System.out.println();
	System.out.println("----------------------------");
	System.out.println("add coupon");
	System.out.println("----------------------------");
	System.out.println();
	
	try {
		companyService.addCoupon(c1);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		companyService.addCoupon(c2);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		companyService.addCoupon(c3);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		companyService.addCoupon(c4);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		companyService.addCoupon(c5);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	
	// update coupon
	
	System.out.println();
	System.out.println("----------------------------");
	System.out.println("update coupon");
	System.out.println("----------------------------");
	System.out.println();
	
	c2 = new Coupon(2, "new", "new", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 10, 999.99, "new", Category.FOOD, null, null);

	
	try {
		companyService.updateCoupon(c2);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	
	// delete coupon
	
	System.out.println();
	System.out.println("----------------------------");
	System.out.println("delete coupon");
	System.out.println("----------------------------");
	System.out.println();
	
	c3 = new Coupon(3, "ccc", "ccc", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), 10, 9.99, "ccc", Category.VACATION, null, null);
	
	try {
		companyService.deleteCoupon(c3);
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	
	// get methods
	
	System.out.println();
	System.out.println("----------------------------");
	System.out.println("get coupons methods");
	System.out.println("----------------------------");
	System.out.println();
	
	try {
		System.out.println(companyService.getAllCompanyCoupons());
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		System.out.println(companyService.getAllCopmanyCouponsFromCategory(Category.ELECTRONICS));
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		System.out.println(companyService.getAllCopmanyCouponsUpToPrice(11));
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	try {
		System.out.println(companyService.getCompanyDetailes());
	} catch (CouponSystemException e) {
		System.err.println("ERROR - " + e.getMessage());
	}
	
	
	
	}

}
