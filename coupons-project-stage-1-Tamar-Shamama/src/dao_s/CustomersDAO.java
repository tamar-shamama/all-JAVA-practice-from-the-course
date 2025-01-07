package dao_s;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Customer;
import connection_pool.ConnectionPool;
import exceptions.CouponSystemException;
import exceptions.CustomerException;

public class CustomersDAO implements CustomersDAOinterface<Customer> {
	
	ConnectionPool cp = ConnectionPool.getInstance();

	
	/** returns true if a customer with given email and password exist in the
	 *database
	 */
	@Override
	public boolean isCustomerExist(Connection con, String email, String password) throws CustomerException {
		
		String sqlOrder = "select * from customers where `email` = ? and `password` = ?";
		
		try  {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new CustomerException("isCustomerExist faild", e);
		}
		
		return false;
	}
	
	
	
	/** returns true if a customer with given id exist in the database
	 * @param con
	 * @param id
	 * @return
	 * @throws CustomerException
	 */
	public boolean isCustomerIdExist(Connection con, int id) throws CustomerException {
		
		String sqlOrder = "select * from customers where `id` = " + id;
		
		try  {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new CustomerException("isCustomerExist faild", e);
		}
		
		return false;
	}

	
	
	
	/** add customer (object) to the database. the customer's coupon are not added to
	 *  the database.
	 */
	@Override
	public void addCustomer(Connection con, Customer customer) throws CustomerException {
		
		String sqlOrder = "insert into customers values (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setInt(1, customer.getCustomer_id());
			ps.setString(2, customer.getCustomer_first_name());
			ps.setString(3, customer.getCustomer_last_name());
			ps.setString(4, customer.getCustomer_email());
			ps.setString(5, customer.getCustomer_password());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CustomerException("addCustomer failed", e);
		}
	}

	
	
	/** updates the customer (names, email & password), meaning replace it in the database
	 * with the given customer. the id can not be change.
	 */
	@Override
	public void updateCustomer(Connection con, Customer customer) throws CustomerException {
		
		String sqlOrder = "update customers set first_name=?, last_name=?, email=?, password=?"
				+ " where id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setString(1, customer.getCustomer_first_name());
			ps.setString(2, customer.getCustomer_last_name());
			ps.setString(3, customer.getCustomer_email());
			ps.setString(4, customer.getCustomer_password());
			ps.setInt(5, customer.getCustomer_id());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CustomerException("updateCustomer failed", e);
		}
		
		
	}

	/** delete the customer whose id is given, from the database.
	 * a customer can not be deleted if there are any coupons belong
	 * to it in the database.
	 *
	 */
	@Override
	public void deleteCustomer(Connection con, int customer_id) throws CustomerException {
		
		String sqlOrder = "delete from customers where id = " + customer_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CustomerException("deleteCustomer failed", e);
		}
		
		
	}
	
	
	

	/**creates a list of the all the customers, without the lists of coupons each customer
	 * purchased
	 */
	@Override
	public List<Customer> getAllCustomers(Connection con) throws CouponSystemException {
		
		
		String sqlOrder = "select * from customers";
		List<Customer> customers = new ArrayList<>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomer_id(rs.getInt("id"));
				customer.setCustomer_first_name(rs.getString("first_name"));
				customer.setCustomer_last_name(rs.getString("last_name"));
				customer.setCustomer_email(rs.getString("email"));
				customer.setCustomer_password(rs.getString("password"));
				customers.add(customer);
			}
			
			return customers;
			
			
		} catch (SQLException e) {
			throw new CustomerException("getAllCustomers failed", e);
		}
	}

	
	
	/**
	 *returns a customer object, without the list of purchased coupons.
	 */
	@Override
	public Customer getCustomer(Connection con, int customer_id) throws CouponSystemException {
		
		String sqlOrder = "select * from customers where id = " + customer_id;
		Customer customer = new Customer();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			if (rs.next()) {
				customer.setCustomer_id(rs.getInt("id"));
				customer.setCustomer_first_name(rs.getString("first_name"));
				customer.setCustomer_last_name(rs.getString("last_name"));
				customer.setCustomer_email(rs.getString("email"));
				customer.setCustomer_password(rs.getString("password"));
				return customer;
			}
			throw new CustomerException("customer id=" + customer_id + " does not exist");
			
		} catch (SQLException e) {
			throw new CustomerException("getCustomer failed", e);
		}
		
	}

}
