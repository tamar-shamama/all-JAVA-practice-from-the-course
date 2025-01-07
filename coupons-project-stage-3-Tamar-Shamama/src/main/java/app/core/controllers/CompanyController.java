package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;


@RestController
@RequestMapping("api/company")
@CrossOrigin (origins = "http://localhost:3000")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	
	/** add coupon to the database. a coupon cannot be added if it's title is the same
	 * as another coupon belong to the same company
	 * @param coupon
	 * @return the added coupon id
	 * @throws CouponSystemException
	 */
	
	@PostMapping("/add")
	public int addCoupon (@RequestBody Coupon coupon, @RequestHeader String token) {
		
		try {
			return companyService.createCoupon(coupon, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	/** updates coupon. ownership cannot be change
	 * @param coupon
	 * @throws CouponSystemException
	 */
	@PutMapping("/update")
	public void updateCoupon (@RequestBody Coupon coupon, @RequestHeader String token) {
		
		try {
			this.companyService.updateCoupon(coupon, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	/** delete coupon whose id was given from the database
	 * @param coupon
	 * @throws CouponSystemException
	 */
	
	@DeleteMapping("/delete/{id}")
	public void deleteCoupon(@PathVariable int id, @RequestHeader String token) {
		
		try {
			this.companyService.deleteCoupon(id, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	@GetMapping("/get")
	public List<Coupon> getAllCompanyCoupons(@RequestHeader String token) {
		
		try {
			return this.companyService.getAllCompanyCoupons(token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@GetMapping("/get/category/{category}")
	public List<Coupon> getAllCopmanyCouponsFromCategory(@PathVariable Category category, @RequestHeader String token) {
		
		try {
			return this.companyService.getAllCopmanyCouponsFromCategory(category, token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@GetMapping("/get/{price}")
	public List<Coupon> getAllCopmanyCouponsUpToPrice(@PathVariable double price, @RequestHeader String token) {
		
		try {
			return this.companyService.getAllCopmanyCouponsUpToPrice(price, token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@GetMapping("/get/detailes")
	public String getCompanyDetailes(@RequestHeader String token) {
		try {
			return this.companyService.getCompanyDetailes(token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
		
		
	
	
	
	
	
	
	
	
	
	

}
