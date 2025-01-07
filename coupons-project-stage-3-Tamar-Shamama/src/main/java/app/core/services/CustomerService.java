package app.core.services;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.util.JWT;

@Service
@Transactional
public class CustomerService extends ClientService {
	
	@Autowired
	JWT jwt;
	
	/** returns true if the login was successful
	 * @param email
	 * @param password
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean login (String email, String password) throws CouponSystemException {
		
		Customer customer = customerRepository.findByEmail(email);
		
		if (customer == null) {
 			throw new CouponSystemException("login failed - email and/or password incorrect");
		} else if (customer.getPassword().equals(password)) {
			return true;
		} else {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		}
	}
	
	
	public int getCustomerId (String email) {
		Customer customer = customerRepository.findByEmail(email);
		return customer.getId();
	}
	
	
	
	public void buyCoupon (Coupon coupon, String token) throws CouponSystemException {
		
		// if exist
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("buyCoupon failed - not exist");
		}
		
		// if bought already
		if (couponRepository.existsByIdAndCustomersId(coupon.getId(), jwt.getClientId(token))) {
			throw new CouponSystemException("buyCoupon failed - bought already");
		}
		
		// if left
		if (!couponRepository.existsByIdAndAmountGreaterThan(coupon.getId(), 0)) {
			throw new CouponSystemException("buyCoupon failed - there isn't any left");
		}
		
		// if not expired
		if (couponRepository.existsByIdAndExpirationDateBefore(coupon.getId(), LocalDate.now())) {
			throw new CouponSystemException("buyCoupon failed - coupon expired");
		}
		
		// reduce amount and match customer
		Coupon c = couponRepository.getById(coupon.getId());
		c.buyCoupon(customerRepository.getById(jwt.getClientId(token)));
		couponRepository.save(c);
	}
	
	
	public void buyCoupon (int id, String token) throws CouponSystemException {
		
		// if exist
		if (!couponRepository.existsById(id)) {
			throw new CouponSystemException("buyCoupon failed - not exist");
		}
		
		Coupon coupon = couponRepository.getById(id);
		
		// if bought already
		if (couponRepository.existsByIdAndCustomersId(coupon.getId(), jwt.getClientId(token))) {
			throw new CouponSystemException("buyCoupon failed - bought already");
		}
		
		// if left
		if (!couponRepository.existsByIdAndAmountGreaterThan(coupon.getId(), 0)) {
			throw new CouponSystemException("buyCoupon failed - there isn't any left");
		}
		
		// if not expired
		if (couponRepository.existsByIdAndExpirationDateBefore(coupon.getId(), LocalDate.now())) {
			throw new CouponSystemException("buyCoupon failed - coupon expired");
		}
		
		// reduce amount and match customer
		coupon.buyCoupon(customerRepository.getById(jwt.getClientId(token)));
		couponRepository.save(coupon);
	}
	
	
	
	/** returns all the coupons belonging to logged customer
	 * @return List
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCoupons(String token) throws CouponSystemException {
		return couponRepository.findAllByCustomersId(jwt.getClientId(token));
	}
	
	
	
	/** returns all the coupons from given category belonging to logged customer
	 * @return List
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCouponsByCategory(Category category, String token) throws CouponSystemException {
			return couponRepository.findAllByCustomersIdAndCategory(jwt.getClientId(token), category);
	}
	
	
	public List<Coupon> getAllCouponsUpToPrice(double price, String token) throws CouponSystemException {
			return couponRepository.findAllByCustomersIdAndPriceLessThanEqual(jwt.getClientId(token), price);
		}
	
	
	public String getCustomerDetailes(String token) throws CouponSystemException {
			return customerRepository.getById(jwt.getClientId(token)).toString();
	}

	
	
	
	
	
	
	
	
	
	
}
