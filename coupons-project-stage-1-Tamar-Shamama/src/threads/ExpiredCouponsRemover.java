package threads;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import beans.Coupon;
import connection_pool.ConnectionPool;
import dao_s.CouponsDAO;
import exceptions.CouponSystemException;

public class ExpiredCouponsRemover extends Thread {
	
	boolean removerActive;
	CouponsDAO couponsDAO;

	public ExpiredCouponsRemover(boolean removerActive, CouponsDAO couponsDAO) {
		super();
		this.removerActive = removerActive;
		this.couponsDAO = couponsDAO;
	}
	
	
	@Override
	public void run() {
		
		System.out.println("daily Expired Coupons Remover active");
		
		while (removerActive) {
			
			try {
				
				if (deleteExpiredCoupons()) {
					System.out.println(" - - - - - - - - - - - - - - - - - - -");
					System.out.println("- - - expired coupon/s deleted - - -");
					System.out.println(" - - - - - - - - - - - - - - - - - - -");
				} else {
					System.out.println(" - - - - - - - - - - - - - - - - - - -");
					System.out.println("- - no coupon was expire today - -");
					System.out.println(" - - - - - - - - - - - - - - - - - - -");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			try {
				TimeUnit.DAYS.sleep(1);
			} catch (InterruptedException e) {
				this.removerActive = false;
				System.out.println("closing Expired Coupons Remover...");
			}
		}
	}
	
	
	
	public void close() {
		interrupt();
	}
	
	
	
	/** delete all expired coupons and purchase history of said coupons.
	 * if there are no expired coupons, returns false.
	 * @return {@code true} if any coupon got deleted
	 * @throws CouponSystemException
	 */
	public synchronized boolean deleteExpiredCoupons() throws CouponSystemException {
		
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection con = connectionPool.getConnection();
		
		try {
			
			con.setAutoCommit(false);
			
			// get all expired coupons
			List<Coupon> expiredCoupons = couponsDAO.getExpiredCoupons(con);
			
			if (expiredCoupons.isEmpty()) {
				return false;
			}
		
		
			// delete purchase history of expired coupons
			for (Coupon coupon : expiredCoupons) {
				int id = coupon.getCoupon_id();
				couponsDAO.deleteCouponPurchaseByCouponId(con, id);
			}
		
			// delete expired coupons
			couponsDAO.deleteExpiredCoupons(con);
			
			con.commit();
			return true;
			
		} catch (CouponSystemException | SQLException e) {
			throw new CouponSystemException("deleteExpiredCoupons failed" + e.getMessage());
		} finally {
			connectionPool.returnConnection(con);
		}
			
		
}

}
