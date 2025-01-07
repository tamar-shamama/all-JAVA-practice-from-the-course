package dao_s;

import java.sql.Connection;
import java.util.List;

import beans.Customer;
import exceptions.CouponSystemException;
import exceptions.CustomerException;

public interface CustomersDAOinterface<C> {
	
	boolean isCustomerExist(Connection con, String email, String password) throws CustomerException;
	
	void addCustomer(Connection con, C company) throws CustomerException;
	
	void updateCustomer(Connection con, C company) throws CustomerException;
	
	void deleteCustomer(Connection con, int company_id) throws CustomerException;
	
	List<Customer> getAllCustomers(Connection con) throws CouponSystemException;
	
	C getCustomer(Connection con, int company_id) throws CouponSystemException;
	
	

}
