package dao_s;

import java.sql.Connection;
import java.util.List;

import beans.Coupon;
import exceptions.CouponException;
import exceptions.CouponSystemException;

public interface CouponsDAOinterface<C> {
	
	
	void addCoupon(Connection con, C coupon) throws CouponException;
	
	void updateCoupon(Connection con, C coupon) throws CouponException;
	
	void deleteCoupon(Connection con, int coupon_id) throws CouponException;
	
	List<Coupon> getAllCoupons(Connection con) throws CouponSystemException;
	
	C getCoupon(Connection con, int coupon_id) throws CouponSystemException;
	
	void addCouponPurchase (Connection con, int customer_id, int coupon_id) throws CouponException;
	
	void deleteCouponPurchase (Connection con, int customer_id, int coupon_id) throws CouponException;
	

}
