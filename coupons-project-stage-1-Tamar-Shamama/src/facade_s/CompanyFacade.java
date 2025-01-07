package facade_s;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Category;
import beans.Company;
import beans.Coupon;
import exceptions.CompanyException;
import exceptions.CouponException;
import exceptions.CouponSystemException;

public class CompanyFacade extends ClientFacade {
	
	public CompanyFacade() {
		super();
	}
	
	Connection con;
	Company companyLogged = null;
	
	


	/** returns true if the email and the password of a company are correct. in that case, a company
	 * object named "companyLogged" will be created, and the rest of the facade methods will
	 * refer to it. this object will have a list of coupons the company created, taken from
	 * the database.
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		
		con = connectionPool.getConnection();
		
		try {
			
			if (companiesDAO.isCompanyExist(con, email, password)) {
				companyLogged = companiesDAO.getCompany(con, email);
				List<Coupon> list = couponsDAO.getCouponsByCompany(con, companyLogged.getCompany_id());
				companyLogged.setCoupons(list);
				return true;
			} else {
				throw new CouponSystemException("login failed - email and/or password incorrect") ;
			}
			
		} catch (CompanyException e) {
			e.printStackTrace();
		} finally {
			connectionPool.returnConnection(con);
		}
		return false;
	}
	
	/**returns a list of the coupons created by the logged company, and also updates
	 * the company object to have a list of those coupons (before the activation of this
	 * method, the list of the logged company was empty).
	 * @param companyLogged
	 * @return List<coupon>
	 * @throws CouponSystemException  
	 */
	public List<Coupon> getCompanyCoupons () throws CouponSystemException {
		
		con = connectionPool.getConnection();
		List<Coupon> list = couponsDAO.getCouponsByCompany(con, companyLogged.getCompany_id());
		connectionPool.returnConnection(con);
		companyLogged.setCoupons(list);
		return list;
	}
	
	
	public synchronized boolean addCoupon (Coupon coupon) throws CouponSystemException {
		
		// check if the company logged in
		if (companyLogged == null) {
			throw new CouponSystemException("login to continue");
		}
		
		// check if coupon title already exist
		List<Coupon> companyCoupons = getCompanyCoupons();
		
		if(!companyCoupons.isEmpty()) {
			for (Coupon c : companyCoupons) {
				if (c.getTitle().equals(coupon.getTitle())) {
					throw new CouponException("coupon name (" + coupon.getTitle() + ") allready exist");
				} else if (c.getCoupon_id() == coupon.getCoupon_id()) {
					throw new CouponException("coupon id (" + coupon.getCoupon_id() + ") allready exist");
				}
			}
		}
		
		con = connectionPool.getConnection();
		
		try {
			con.setAutoCommit(false);
			couponsDAO.addCoupon(con, coupon); // add to database
			companyLogged.getCoupons().add(coupon); // add to company object
			con.commit();
			return true;
			
		} catch (SQLException | CouponSystemException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CouponException("addCoupon failed - " + e.getMessage());
			
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	
	public boolean updateCoupon (Coupon coupon) throws CouponSystemException {
		
		if (companyLogged == null) {
			throw new CouponSystemException("login to continue");
		}
		
		try {
			con = connectionPool.getConnection();
			con.setAutoCommit(false);
			
			if (coupon.getCompany_id() != companyLogged.getCompany_id()) {
				throw new CouponException("can not change company id");
			} else {
				couponsDAO.updateCoupon(con, coupon);
				con.commit();
				return true;
			}
			
		} catch (SQLException | CouponException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CouponSystemException("updateCoupon failed - " + e.getMessage());
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	
	public boolean deleteCoupon(Coupon coupon) throws Exception {
		
		if (companyLogged == null) {
			throw new Exception("login to continue");
		}
		
		con = connectionPool.getConnection();
		
		try {
			con.setAutoCommit(false);
			couponsDAO.deleteCouponPurchaseByCouponId(con, 0);
			couponsDAO.deleteCoupon(con, coupon.getCoupon_id());
			con.commit();
			return true;
			
		} catch (SQLException e) {
			con.rollback();
			return false;
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	public List<Coupon> getAllCouponsFromCat(Category cat) throws Exception {
		
		List<Coupon> list = getCompanyCoupons();
		List<Coupon> finaList = new ArrayList<Coupon>();
		
		for (Coupon coupon : list) {
			if (coupon.getCategory().equals(cat)) {
				finaList.add(coupon);
			}
		}
		return finaList;
	}
		
	public List<Coupon> getAllCouponsUpToPrice(double price) throws Exception {
		
		List<Coupon> list = getCompanyCoupons();
		List<Coupon> finaList = new ArrayList<Coupon>();
		
		for (Coupon coupon : list) {
			if (coupon.getPrice() <= price) {
				finaList.add(coupon);
			}
		}
		return finaList;
	}
	
	
	public String getCompanyDetails() throws Exception {
		
		StringBuilder sb = new StringBuilder("\n");
		sb.append("id: ").append(companyLogged.getCompany_id()).append("\n");
		sb.append("name: ").append(companyLogged.getCompany_name()).append("\n");
		sb.append("email: ").append(companyLogged.getCompany_email()).append("\n");
		sb.append("list of coupons:").append("\n");
		sb.append(getCompanyCoupons());
		
		return sb.toString();
	}

	public Company getCompanyLogged() {
		return companyLogged;
	}

	public void setCompanyLogged(Company companyLogged) {
		this.companyLogged = companyLogged;
	}
	
	public Coupon getCouponById(int id) throws CouponSystemException {
		con = connectionPool.getConnection();
		Coupon c = couponsDAO.getCoupon(con, id);
		connectionPool.returnConnection(con);
		return c;
	}
	
	
	
	
	

}
