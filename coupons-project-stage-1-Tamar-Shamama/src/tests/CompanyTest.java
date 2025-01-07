package tests;

import java.time.LocalDate;
import java.util.List;

import beans.Category;
import beans.Coupon;
import exceptions.CouponSystemException;
import facade_s.CompanyFacade;
import login_manager.LoginManager;
import login_manager.LoginManager.ClienType;

public class CompanyTest {
	
	
	public void testCompany(LoginManager login_manager, CompanyFacade companyFacade) {
	
		
		System.out.println("-------------------------");
		System.out.println("company test");
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
			companyFacade = (CompanyFacade) login_manager.login(ClienType.COMPANY, "companyMail1@mail", "companyPassword");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		System.out.println("returned object: " + companyFacade);
		
		
		System.out.println("====================================");
		System.out.println();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		// login with correct password
		// ----------------------------
		
		System.out.println("login with correct password:");
		
		try {
			companyFacade = (CompanyFacade) login_manager.login(ClienType.COMPANY, "companyMail1@mail", "companyPassword1");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		System.out.println("returned object: " + companyFacade);
		
		
		System.out.println("====================================");
		System.out.println();
		

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		
		// add coupons (to the logged company)
		// ----------------------------
		
		int couponId = companyFacade.getCompanyLogged().getCompany_id();
		System.out.println("add coupons (to the logged company):");
		
		for (int i = 0; i < 20; i++) {
			Coupon coupon = createRandomCoupon(couponId);
			try {
				companyFacade.addCoupon(coupon);
				System.out.println("coupon id=" + coupon.getCoupon_id() + " added");
			} catch (Exception e) {
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
		
		
		
		// update coupons
		// ----------------------------
		
		System.out.println("update coupons:");
		
		Coupon c1 = new Coupon(1001, 1, Category.ELECTRICITY, "new1", "new1", LocalDate.of(2025, 2, 2), LocalDate.of(2026, 2, 2), 15, 15, "aaa");
		Coupon c2 = new Coupon(1002, 1, Category.ELECTRICITY, "new2", "new2", LocalDate.of(2025, 2, 2), LocalDate.of(2026, 2, 2), 15, 15, "aaa");
		Coupon c3 = new Coupon(1003, 1, Category.ELECTRICITY, "new3", "new4", LocalDate.of(2025, 2, 2), LocalDate.of(2026, 2, 2), 15, 15, "aaa");
		
		try {
			companyFacade.updateCoupon(c1);
			System.out.println("coupon id=" + c1.getCoupon_id() + " updated");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		try {
			companyFacade.updateCoupon(c2);
			System.out.println("coupon id=" + c2.getCoupon_id() + " updated");
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		try {
			companyFacade.updateCoupon(c3);
			System.out.println("coupon id=" + c3.getCoupon_id() + " updated");
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

		
		// delete coupons
		// ----------------------------
		
		System.out.println("delete coupons:");
		
		Coupon c4 = null,c5 = null,c6 = null;
		
		try {
			c4 = companyFacade.getCouponById(1004);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		try {
			c5 = companyFacade.getCouponById(1005);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		try {
			c6 = companyFacade.getCouponById(1006);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		
		try {
			companyFacade.deleteCoupon(c4);
			System.out.println("coupon id=" + c4.getCoupon_id() + " deleted");
		} catch (Exception e) {
			System.err.println("error - " + e.getMessage());
		}
		try {
			companyFacade.deleteCoupon(c5);
			System.out.println("coupon id=" + c5.getCoupon_id() + " deleted");
		} catch (Exception e) {
			System.err.println("error - " + e.getMessage());
		}
		try {
			companyFacade.deleteCoupon(c6);
			System.out.println("coupon id=" + c6.getCoupon_id() + " deleted");
		} catch (Exception e) {
			System.err.println("error - " + e.getMessage());
		}
		
		System.out.println("====================================");
		System.out.println();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// view all coupons
		// --------------------
		System.out.println("view all coupons:");
		
		try {
			List<Coupon> list =	companyFacade.getCompanyCoupons();
			System.out.println(list);
		} catch (CouponSystemException e) {
			System.err.println("error - " + e.getMessage());
		}
		
		System.out.println("====================================");
		System.out.println();
		
		
		// view company details
		// --------------------
		System.out.println("view company details:");
		
		try {
			System.out.println(companyFacade.getCompanyDetails());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		System.out.println();
		System.out.println();
		
		
		
		
		
		
		
	}
	
	private static int serialCouponId = 1000;
	private static int serialTitleCoupon = 1;
	private static int serialDescriptionCoupon = 1;
	
	private static Category getRandomCat() {
		Category cat = null;
		int n = (int)(Math.random()*3) + 1;
		if (n==1) {
			cat = Category.ELECTRICITY;
		} else if (n==2) {
			cat = Category.FOOD;
		} else if (n==3) {
			cat = Category.RESTAURANT;
		} else if (n==4) {
			cat = Category.VACATION;
		}
		return cat;
	}
	
	private static double getPrice() {
		return Math.random()*501;
	}
	
	private static Coupon createRandomCoupon(int companyId) {
		Coupon coupon = new Coupon(serialCouponId++, companyId,
				getRandomCat(), "title" + serialTitleCoupon++, "description" + serialDescriptionCoupon++,
				LocalDate.of(2024, 5, 2), LocalDate.of(2025, 01, 10), 100, getPrice(), "image");
		return coupon;
		
	}

}
