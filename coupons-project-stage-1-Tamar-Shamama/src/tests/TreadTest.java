package tests;

import java.time.LocalDate;

import beans.Category;
import beans.Company;
import beans.Coupon;
import dao_s.CouponsDAO;
import exceptions.CompanyException;
import exceptions.CouponSystemException;
import facade_s.AdminFacade;
import facade_s.CompanyFacade;
import login_manager.LoginManager;
import login_manager.LoginManager.ClienType;
import system.AppSystem;
import threads.ExpiredCouponsRemover;

public class TreadTest {
	
	private static int serial = 1;

	public static void main(String[] args) {
		
		AppSystem appSystem = new AppSystem();
		LoginManager loginManager = appSystem.startApp();  // the regular thread starts here already
		AdminFacade adminFacade = null;
		CompanyFacade companyFacade = null;
		CouponsDAO c = new CouponsDAO();
		
		
		// time to the regular thread to delete coupons and enter sleep
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		// add company
		
		try {
			adminFacade = (AdminFacade) loginManager.login(ClienType.ADMINISTRATOR, "admim@admin", "admin");
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
		
		Company testComp = new Company(20, "test", "test", "test", null);
		
		try {
			adminFacade.addCompany(testComp);
		} catch (CompanyException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("=====================");
		
		
		
		// add expired coupons (the thread is asleep so they will not be deleted immediately)
		
		try {
			companyFacade = (CompanyFacade) loginManager.login(ClienType.COMPANY, "test", "test");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		for (int i = 0; i < 5; i++) {
			Coupon coupon = new Coupon(2000 + i, 20, Category.RESTAURANT, "test" + (serial++), "test", LocalDate.of(2000, 1, 1),
					LocalDate.of(2000, 2, 2), 10, 10, "test");
			try {
				companyFacade.addCoupon(coupon);
				System.out.println("coupon " + coupon.getCoupon_id() + " added");
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}
		}
		
	
		
		System.out.println("=====================");
		
		
		
		// View coupons
		try {
			System.out.println(companyFacade.getCompanyCoupons());
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		
		System.out.println("=====================");
		
		
		
		// create another thread (that will delete them immediately)
		
		ExpiredCouponsRemover ecr = new ExpiredCouponsRemover(true, c);
		ecr.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		System.out.println("=====================");
		
		// view coupons again
		try {
			System.out.println(companyFacade.getCompanyCoupons());
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		
		System.out.println("=====================");
		
		
		
		
		
		ecr.interrupt();
		appSystem.closeApp();
		
		
		
	
		

	}

}
