package app.core.services;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class CustomerService extends ClientService {
	
	
	Customer loggedCustomer = null;
	
	
	public boolean login (String email, String password) throws CouponSystemException {
		
		Customer customer = customerRepository.findByEmail(email);
		
		if (customer == null) {
 			throw new CouponSystemException("login failed - email and/or password incorrect");
		} else if (customer.getPassword().equals(password)) {
			loggedCustomer = customer;
			return true;
		} else {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		}
		
	}
	
	
	
	public void buyCoupon (Coupon coupon) throws CouponSystemException {
		
		// if logged
		if (!isLogged()) {
			throw new CouponSystemException("buyCoupon failed - not logged in");
		}
		
		// if exist
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("buyCoupon failed - not exist");
		}
		
		// if bought already
		if (couponRepository.existsByIdAndCustomersId(coupon.getId(), loggedCustomer.getId())) {
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
		c.buyCoupon(loggedCustomer);
		couponRepository.save(c);
	}
	
	
	public List<Coupon> getAllCouponsFromCustomer() throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCouponsFromCustomer failed - not logged in");
			
		} else {
			return couponRepository.findAllByCustomersId(loggedCustomer.getId());
		}
	}
	
	public List<Coupon> getAllCouponsFromCustomerByCategory(Category category) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCouponsFromCustomerByCategory failed - not logged in");
			
		} else {
			return couponRepository.findAllByCustomersIdAndCategory(loggedCustomer.getId(), category);
		}
	}
	
	public List<Coupon> getAllCouponsFromCustomerUpToPrice(double price) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCouponsFromCustomerByCategoryUpToPrice failed - not logged in");
			
		} else {
			return couponRepository.findAllByCustomersIdAndPriceLessThanEqual(loggedCustomer.getId(), price);
		}
	}
	
	public String getCustomerDetailes() throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getCustomerDetailes failed - not logged in");
		} else {
			return loggedCustomer.toString();
		}
	}

	
	public boolean isLogged() {
		if (loggedCustomer == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
	
	
	
	
	
}
