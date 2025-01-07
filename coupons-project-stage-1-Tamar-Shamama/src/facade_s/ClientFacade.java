package facade_s;

import connection_pool.ConnectionPool;
import dao_s.CompaniesDAO;
import dao_s.CouponsDAO;
import dao_s.CustomersDAO;
import exceptions.CouponSystemException;

/**a client is everyone who use this system, meaning administrator (controls the system),
 * companies (use the system to create and cell coupons to their clients); and customers (who
 * buy coupons from the companies).
 * 
 * @author Tamar
 */
public abstract class ClientFacade {
	
	CompaniesDAO companiesDAO = new CompaniesDAO();
	CouponsDAO couponsDAO = new CouponsDAO();
	CustomersDAO customersDAO = new CustomersDAO();
	ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	
	
	public ClientFacade() {
		super();
		
	}



	boolean login(String email, String password) throws CouponSystemException {
		return false;
	}
	

}
