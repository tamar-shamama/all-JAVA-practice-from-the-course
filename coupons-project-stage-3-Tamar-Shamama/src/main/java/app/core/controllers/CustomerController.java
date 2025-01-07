package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin (origins = "http://localhost:3000")
public class CustomerController {
	
	
	@Autowired
	CustomerService customerService;
	
	
	@PostMapping("/buy")
	public void buyCoupon (@RequestBody Coupon coupon, @RequestHeader String token) {
		
		try {
			this.customerService.buyCoupon(coupon, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@PostMapping("/buy/{id}")
	public void buyCoupon (@PathVariable int id, @RequestHeader String token) {
		
		try {
			this.customerService.buyCoupon(id, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@GetMapping("/get")
	public List<Coupon> getAllCoupons(@RequestHeader String token) {
		try {
			return this.customerService.getAllCoupons(token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	@GetMapping("/get/category/{category}")
	public List<Coupon> getAllCouponsByCategory(@PathVariable Category category, @RequestHeader String token) {
		try {
			return this.customerService.getAllCouponsByCategory(category, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@GetMapping ("/get/{price}")
	public List<Coupon> getAllCouponsUpToPrice(@PathVariable double price, @RequestHeader String token) {
		try {
			return this.customerService.getAllCouponsUpToPrice(price, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@GetMapping("/get/deteiles")
	public String getCustomerDetailes(@RequestHeader String token) {
		try {
			return this.customerService.getCustomerDetailes(token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	

}
