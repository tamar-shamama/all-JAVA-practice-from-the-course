package facade_s;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponException;
import exceptions.CouponSystemException;
import exceptions.CustomerException;

public class CustomerFacade extends ClientFacade {


	Connection con;
	Customer customerLogged;
	
	
	public Customer getCustomerLogged() {
		return customerLogged;
	}


	public void setCustomerLogged(Customer customerLogged) {
		this.customerLogged = customerLogged;
	}


	/** returns true if mail & password are correct, and if so creates a customer object
	 * called "customerLogged" and add to it a list of the coupon said customer
	 * had purchased, according to database
	 */
	public boolean login(String email, String password) throws CouponSystemException {

		
		try {
			con = connectionPool.getConnection();
			List<Customer> list = customersDAO.getAllCustomers(con);
			
			for (Customer customer : list) {
				if (customer.getCustomer_email().equals(email) && customer.getCustomer_password().equals(password)) {
					System.out.println("login succesfully");
					customerLogged = customer;
					customer.setCoupons(getAllCoupons());
					return true;
				} else {
					throw new CouponSystemException("login failed - email and/or password incorrect") ;
				}
			}
			
		} catch (CustomerException e) {
			e.printStackTrace();
		} finally {
			connectionPool.returnConnection(con);
		}
		return false;
	}
	
	
	public boolean buyCoupon(Coupon coupon) throws CouponSystemException {
		
		try {
			con = connectionPool.getConnection();
			con.setAutoCommit(false);
			
			
			// does the coupon exist
			if (!couponsDAO.isCouponExist(con, coupon)) {
				throw new CouponException("coupon does not exist");
			}
			
			// does the customer bought this coupon already;
			List<Coupon> list = customerLogged.getCoupons();
			for (Coupon c : list) {
				if (c.getCoupon_id()==coupon.getCoupon_id()) {
					throw new CouponException("already bought");
				}
			}
			
			Coupon checkCoupon = couponsDAO.getCoupon(con, coupon.getCoupon_id());
			
			// are there coupons left
			if (checkCoupon.getAmount()==0) {
				throw new CouponException("no coupons left");
			// does coupon not expired
			} else if (checkCoupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponException("coupon expire");
				
			} else {
				// add to database
				couponsDAO.addCouponPurchase(con, customerLogged.getCustomer_id(), checkCoupon.getCoupon_id());
				// reduce amount of coupon units
				checkCoupon.setAmount(checkCoupon.getAmount()-1);
				couponsDAO.updateCoupon(con, checkCoupon);
				// add coupon to customer object coupons list
				list.add(checkCoupon);
				customerLogged.setCoupons(list);
				con.commit();
				return true;
			}
			
		} catch (SQLException | CouponSystemException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CouponSystemException("buyCoupon failed - " + e.getMessage());
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	public boolean buyCouponById(int coupon_id) throws CouponSystemException {
		
		try {
			con = connectionPool.getConnection();
			con.setAutoCommit(false);
			
			
			// does the coupon exist
			if (!couponsDAO.isCouponExistById(con, coupon_id)) {
				throw new CouponException("coupon does not exist");
			}
			
			// does the customer bought this coupon already;
			List<Coupon> list = customerLogged.getCoupons();
			for (Coupon c : list) {
				if (c.getCoupon_id()==coupon_id) {
					throw new CouponException("already bought");
				}
			}
			
			// create coupon object
			Coupon checkCoupon = couponsDAO.getCoupon(con, coupon_id);
			
			// are there coupons left
			if (checkCoupon.getAmount()==0) {
				throw new CouponException("no coupons left");
				// does coupon not expired
			} else if (checkCoupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponException("coupon expire");
				
			} else {
				// add to database
				couponsDAO.addCouponPurchase(con, customerLogged.getCustomer_id(), checkCoupon.getCoupon_id());
				// reduce amount of coupon units
				checkCoupon.setAmount(checkCoupon.getAmount()-1);
				couponsDAO.updateCoupon(con, checkCoupon);
				// add coupon to customer object coupons list
				list.add(checkCoupon);
				customerLogged.setCoupons(list);
				con.commit();
				return true;
			}
			
		} catch (SQLException | CouponSystemException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CouponSystemException("buyCoupon failed - " + e.getMessage());
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	
	/** returns all coupons purchased by the logged in customer
	 * @return List
	 * @throws CouponException
	 */
	public List<Coupon> getAllCoupons() throws CouponException {
		Connection con = connectionPool.getConnection();
		List<Coupon> list = couponsDAO.getAllCouponsPurchasedBy(con, customerLogged.getCustomer_id());
		connectionPool.returnConnection(con);
		return list;
	}
	
	
	public List<Coupon> getAllCouponsFromCategory (Category category) throws CouponSystemException {
		
		con = connectionPool.getConnection();
		List<Coupon> list = getAllCoupons();
		List<Coupon> newList = new ArrayList<>();
		
		for (Coupon coupon : list) {
			if (coupon.getCategory().equals(category)) {
				newList.add(coupon);
			}
		}
		connectionPool.returnConnection(con);
		return newList;
		
	}
	
	
	public List<Coupon> getAllCouponsUpToPrice (double maxPrice) throws CouponSystemException {
		
		con = connectionPool.getConnection();
		List<Coupon> list = getAllCoupons();
		List<Coupon> newList = new ArrayList<>();
				
		for (Coupon coupon : list) {
			if (coupon.getPrice()<=maxPrice) {
				newList.add(coupon);
			}
		}
		connectionPool.returnConnection(con);
		return newList;
		
	}
	
	

	public String getCustomerDetails() throws CouponException {
		
		StringBuilder sb = new StringBuilder("\n");
		sb.append("id: ").append(customerLogged.getCustomer_id()).append("\n");
		sb.append("first name: ").append(customerLogged.getCustomer_first_name()).append("\n");
		sb.append("last name: ").append(customerLogged.getCustomer_last_name()).append("\n");
		sb.append("email: ").append(customerLogged.getCustomer_email()).append("\n");
		sb.append("coupons purchased:").append("\n");
		sb.append(getAllCoupons());
		
		return sb.toString();
	}
	
	
	
	
	
	
	
			
	
	
	
	
}
