package facade_s;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CompanyException;
import exceptions.CouponException;
import exceptions.CouponSystemException;
import exceptions.CustomerException;

public class AdminFacade extends ClientFacade {
	
	public AdminFacade() {
		super();
	}

	Connection con = null;

	/** returns true if the email and password are correct.
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		
		String adminMail = "admim@admin";
		String adminPassword = "admin";
		
		if (email == adminMail && password == adminPassword) {
			return true;
		} else {
			throw new CouponException("login failed - email and/or password incorrect") ;
		}
	}
	
	
	/** add a company to the database using the DAO class, if the company doesn't exist
	 * already, or if a company with same email/name doesn't exists.
	 * @param company
	 * @return
	 * @throws CompanyException
	 */
	public boolean addCompany(Company company) throws CompanyException {
		
		con = connectionPool.getConnection();
	
		List<Company> List = companiesDAO.getAllCompanies(con);
		
		try {
			con.setAutoCommit(false);
			
			for (Company comp : List) {
			
				if (comp.getCompany_id()==company.getCompany_id()) {
					throw new CompanyException("id= " + company.getCompany_id() + " already exist");
				} else if (comp.getCompany_name().equals(company.getCompany_name())) {
					throw new CompanyException("name already exist");
				} else if (comp.getCompany_email().equals(company.getCompany_email())) {
					throw new CompanyException("email already exist");
				}
			
			}
				companiesDAO.addCompany(con, company);
				con.commit();
				return true;
				
		} catch (SQLException | CompanyException e) {
			
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CompanyException("addCompany failed - " + e.getMessage());
			
		} finally { 
			connectionPool.returnConnection(con);
		}
	}
	
	
	
	/** Replace the company in the database with the new one using DAO class. id and 
	 * name can not be changed.
	 * @param company
	 * @return
	 * @throws CompanyException
	 */
	public boolean updateCompany(Company company) throws CompanyException {
		
		con = connectionPool.getConnection();
		
		try {
			
			con.setAutoCommit(false);
			Company c = companiesDAO.getCompany(con, company.getCompany_id());
			
			if (!c.getCompany_name().equals(company.getCompany_name())) {
				throw new CompanyException("name can not be changed");
			} else {
				companiesDAO.updateCompany(con, company);
			}
			con.commit();
			return true;
			
		} catch (SQLException | CompanyException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CompanyException("updateCompany failed - " + e);
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	
	
	/** delete a company and all of it's coupons and purchase history from
	 * the database.
	 * @param company
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean deletCompany(Company company) throws CouponSystemException {
		
		if (!companiesDAO.isCompanyExist(con, company.getCompany_email(), company.getCompany_password())) {
			throw new CompanyException("company " + company.getCompany_name() + " does not exist");
		}
		
		con = connectionPool.getConnection();
		List<Coupon> CoupList = couponsDAO.getCouponsByCompany(con, company.getCompany_id());
		
		// delete all coupons the company created
		for (Coupon coupon : CoupList) {
			
			// delete purchase history
			couponsDAO.deleteCouponPurchaseByCouponId(con, coupon.getCoupon_id());
			// delete coupon
			couponsDAO.deleteCoupon(con, coupon.getCoupon_id());
			
		}
		// delete company
		companiesDAO.deleteCompany(con, company.getCompany_id());
		connectionPool.returnConnection(con);
		return true;
	}
	
	
	/** return a list of all the companies in the database.
	 * @return
	 * @throws CompanyException
	 */
	public List<Company> getAllCompanies() throws CompanyException {
		con = connectionPool.getConnection();
		List<Company> list = companiesDAO.getAllCompanies(con);
		connectionPool.returnConnection(con);
		return list;
	}
	
	
	
	/**
	 * @param id
	 * @return list of companies
	 * @throws CompanyException
	 */
	public Company getCompanyById(int id) throws CompanyException {
		con = connectionPool.getConnection();
		Company company = companiesDAO.getCompany(con, id);
		connectionPool.returnConnection(con);
		return company;
	}
	
	
	public boolean addCustomer (Customer customer) throws CouponSystemException {
		
		con = connectionPool.getConnection();
		
		try {
			
			con.setAutoCommit(false);
			
			if (customersDAO.isCustomerIdExist(con, customer.getCustomer_id())) {
				throw new CustomerException("customer allready exist");
			} else {
				customersDAO.addCustomer(con, customer);
				con.commit();
				return true;
			}
			
		} catch (CustomerException | SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CouponSystemException("addCustomer failed - " + e.getMessage());
			
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	
	public boolean updateCustomer(Customer customer) throws CustomerException {
		
		con = connectionPool.getConnection();
		
		try {
			con.setAutoCommit(false);
			
			if (!customersDAO.isCustomerIdExist(con, customer.getCustomer_id())) {
				throw new CustomerException("customer id=" + customer.getCustomer_id() + " does not exist");
			} else {
				customersDAO.updateCustomer(con, customer);
				con.commit();
				return true;
			}
			
		} catch (CustomerException | SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CustomerException("updateCustomer failed - " + e.getMessage());
		} finally {
			connectionPool.returnConnection(con);
		}
	}
	
	
	public boolean deleteCustomer(Customer customer) throws CouponSystemException {
		
		con = connectionPool.getConnection();
		
		if (!customersDAO.isCustomerIdExist(con, customer.getCustomer_id())) {
			throw new CustomerException("customer id=" + customer.getCustomer_id() + " does not exist");
		}
		
		try {
			
			con.setAutoCommit(false);
			
			// delete purchase history of customer
			couponsDAO.deleteCouponPurchaseByCustomerId(con, customer.getCustomer_id());
			// delete customer
			customersDAO.deleteCustomer(con, customer.getCustomer_id());
			
			con.commit();
		
			return true;
		
		} catch (CouponException | CustomerException | SQLException e) {
			throw new CouponSystemException("deleteCustomer failed - " + e.getMessage());
		} finally {
			connectionPool.returnConnection(con);
		}
	
	}
	
	
	public List<Customer> getAllCustomers() throws CouponSystemException {
		con = connectionPool.getConnection();
		List<Customer> list = customersDAO.getAllCustomers(con);
		connectionPool.returnConnection(con);
		return list;
	}
	
	
	public Customer getCustomerById(int id) throws CouponSystemException {
		con = connectionPool.getConnection();
		Customer customer = customersDAO.getCustomer(con, id);
		connectionPool.returnConnection(con);
		return customer;
	}
	
	
		
		
	
	
	
	


}
