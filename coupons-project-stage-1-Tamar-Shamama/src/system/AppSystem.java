package system;

import connection_pool.ConnectionPool;
import dao_s.CouponsDAO;
import login_manager.LoginManager;
import threads.ExpiredCouponsRemover;

public class AppSystem {
	

	private ConnectionPool connectionPool = ConnectionPool.getInstance();
	private CouponsDAO couponsDAO = new CouponsDAO();
	private LoginManager login_manager = LoginManager.getInstance();
	private ExpiredCouponsRemover expiredCouponsRemover = new ExpiredCouponsRemover(true, couponsDAO);

	
	public AppSystem() {
	}
	
	public LoginManager startApp() {
		
		expiredCouponsRemover.start();
		System.out.println("CouponSystem Active");
		return login_manager;
	}
	
	
	public void closeApp() {
		expiredCouponsRemover.close();
		connectionPool.closeAllConnections();
		System.out.println("CouponSystem Closed");
	}

	
	
	
	
	
	

}
