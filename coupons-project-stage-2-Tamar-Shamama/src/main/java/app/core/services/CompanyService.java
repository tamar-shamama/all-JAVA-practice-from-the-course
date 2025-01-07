package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class CompanyService extends ClientService {
	
	Company loggedCompany = null;
	
	
	public boolean login (String email, String password) throws CouponSystemException {
		
		Company company = companyRepository.findByEmail(email);
		
		if (company == null) {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		} else if (company.getPassword().equals(password)) { 
			loggedCompany = company;
			return true;
		} else {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		}
		
	}
		
	
	/** add coupon to the database. a coupon cannot be added if it's title is the same
	 * as another coupon belong to the same company
	 * @param coupon
	 * @return the added coupon id
	 * @throws CouponSystemException
	 */
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("addCoupon failed - not logged in");
		}
		
		matchCouponToCompany(coupon);
		
		// if exist
		if (couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("addCoupon failed - allready exist");
		}
		
		// if have same title
		if (couponRepository.existsByCompanyIdAndTitle(loggedCompany.getId(), coupon.getTitle())) {
			throw new CouponSystemException("addCoupon failed - cannot add coupon with same title");
		} else {
			return couponRepository.save(coupon).getId();
		}
	}
		
		
		
	
	/** updates coupon. ownership cannot be change
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon (Coupon coupon) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("updateCoupon failed - not logged in");
		}
		
		matchCouponToCompany(coupon);
		Coupon oldCoupon;
		Optional<Coupon> OptOldCoupon = couponRepository.findById(coupon.getId());
		
		if (OptOldCoupon.isEmpty()) {
			throw new CouponSystemException("updateCoupon failed - not exist");
		} else {
			oldCoupon = OptOldCoupon.get();
		}
		
		if (oldCoupon.getCompany().getId() != loggedCompany.getId()) {
			throw new CouponSystemException("updateCoupon failed - cannot change owner company");
		}
		couponRepository.save(coupon);
	}
	
	
	public void deleteCoupon(Coupon coupon) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("deleteCoupon failed - not logged in");
		}
		
		Coupon c;
		
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("deleteCoupon failed - not exist");
		} else {
			c = couponRepository.getById(coupon.getId());
		}
		
		if (c.getCompany().getId() != loggedCompany.getId()) {
			throw new CouponSystemException("updateCoupon failed - cannot change owner company");
		}
		couponRepository.delete(c);
		
	}
	
	
	public List<Coupon> getAllCompanyCoupons() throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCompanyCoupons failed - not logged in");
		}
		
		if (loggedCompany == null) {
			throw new CouponSystemException("not logged in");
			
		} else {
			return couponRepository.findAllByCompanyId(loggedCompany.getId());
		}
	}
	
	
	public List<Coupon> getAllCopmanyCouponsFromCategory(Category category) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCopmanyCouponsFromCategory failed - not logged in");
		}
		
		if (loggedCompany == null) {
			throw new CouponSystemException("not logged in");
			
		} else {
			return couponRepository.findAllByCompanyIdAndCategory(loggedCompany.getId(), category);
		}
	}
	
	public List<Coupon> getAllCopmanyCouponsUpToPrice(double price) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCopmanyCouponsUpToPrice failed - not logged in");
		}
		
		return couponRepository.findAllByCompanyIdAndPriceLessThanEqual(loggedCompany.getId(), price);
		
	}
	
	
	public String getCompanyDetailes() throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getCompanyDetailes failed - not logged in");
		}
		
		return loggedCompany.toString();
		
	}
	
	
	public void matchCouponToCompany(Coupon coupon) throws CouponSystemException {
		coupon.setCompany(loggedCompany);
	}
	
	public boolean isLogged() {
		if (loggedCompany == null) {
			return false;
		} else {
			return true;
		}
	}
	

	
	
	
}
