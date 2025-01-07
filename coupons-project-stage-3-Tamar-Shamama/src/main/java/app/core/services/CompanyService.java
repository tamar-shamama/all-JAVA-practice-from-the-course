package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.util.JWT;

@Service
@Transactional
public class CompanyService extends ClientService {
	
	@Autowired
	JWT jwt;
	
	
	/** returns true if login was successful.
	 * @param email
	 * @param password
	 * @return boolean
	 * @throws CouponSystemException
	 */
	public boolean login (String email, String password) throws CouponSystemException {
		
		Company company = companyRepository.findByEmail(email);
		
		if (company == null) {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		} else if (company.getPassword().equals(password)) { 
			return true;
		} else {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		}
	}
	
	
	
	
	private Company getCompany(String token) {
		int companyId = jwt.getClientId(token);
		return this.companyRepository.findById(companyId).orElseThrow();
	}
	
	
	/** add coupon to the database. a coupon cannot be added if it's title is the same
	 * as another coupon belong to the same company
	 * @param coupon
	 * @return the added coupon id
	 * @throws CouponSystemException
	 */
	public int createCoupon(Coupon coupon, String token) throws CouponSystemException {
		
		
		if (couponRepository.existsByCompanyIdAndTitle(jwt.getClientId(token), coupon.getTitle())) {
			throw new CouponSystemException("addCoupon failed - cannot add coupon with same title");
		}
		
		coupon.setCompany(getCompany(token));
		coupon = this.couponRepository.save(coupon);
		return coupon.getId();
	}
		
		
		
	
	/** updates coupon. ownership cannot be change
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon (Coupon coupon, String token) throws CouponSystemException {
		
		matchCouponToCompany(coupon, token);
		
		Coupon oldCoupon;
		
		if (!couponRepository.existsById(coupon.getId())) {
			throw new CouponSystemException("updateCoupon failed - company " + coupon.getId() + " not exist");
		}
			
		oldCoupon = couponRepository.findById(coupon.getId()).get();
			
		if (oldCoupon.getCompany().getId() != jwt.getClientId(token)) {
			throw new CouponSystemException("updateCoupon failed - cannot change owner company");
		}
		
		couponRepository.save(coupon);
	}
	
	
	
	
	/** delete coupon whose id was given from the database
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void deleteCoupon(int id, String token) throws CouponSystemException {
		
		Coupon c;
		
		if (!couponRepository.existsById(id)) {
			throw new CouponSystemException("deleteCoupon failed - not exist");
		} else {
			c = couponRepository.getById(id);
		}
		
		if (c.getCompany().getId() != jwt.getClientId(token)) {
			throw new CouponSystemException("deleteCoupon failed - cannot delete from different company!");
		}
		couponRepository.delete(c);
	}
	
	
	public List<Coupon> getAllCompanyCoupons(String token) throws CouponSystemException {
		return couponRepository.findAllByCompanyId(jwt.getClientId(token));
	}
	
	
	
	public List<Coupon> getAllCopmanyCouponsFromCategory(Category category, String token) throws CouponSystemException {
		return couponRepository.findAllByCompanyIdAndCategory(jwt.getClientId(token), category);
	}
	
	
	
	public List<Coupon> getAllCopmanyCouponsUpToPrice(double price, String token) throws CouponSystemException {
		return couponRepository.findAllByCompanyIdAndPriceLessThanEqual(jwt.getClientId(token), price);
		
	}
	
	
	public String getCompanyDetailes(String token) throws CouponSystemException {
		int companyId = jwt.getClientId(token);
		return companyRepository.getById(companyId).toString();
	}
	

	
	/** set the logged company as owner to the given coupon
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void matchCouponToCompany(Coupon coupon, String token) throws CouponSystemException {
		int companyId = jwt.getClientId(token);
		Company loggedCompany = companyRepository.getById(companyId);
		coupon.setCompany(loggedCompany);
	}
	
	
	public int getCompanyId (String email) {
		Company company = companyRepository.findByEmail(email);
		return company.getId();
	}

	
	
	
}
