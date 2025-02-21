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

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdministratorService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin (origins = "http://localhost:3000")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	
	/** add a company to the database. company with already existing name or email
	 * cannot be added
	 * @param company
	 * @return the id of the added company
	 * @throws CouponSystemException
	 */
	
	@PostMapping("/company/add")
	public int addCompany (@RequestBody Company company, @RequestHeader String token) {
		
		try {
			return this.administratorService.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	/** Updates company in the database. name cannot be change
	 * @param company
	 * @throws CouponSystemException
	 */
	
	@PutMapping ("/company/update")
	public void apdateCompany (@RequestBody Company company, @RequestHeader String token) {
		
		try {
			this.administratorService.updateCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	/** delete the company and all of it's coupons from the database
	 * @param company
	 * @throws CouponSystemException
	 */
	
	@DeleteMapping ("/company/delete")
	public void deletCompany (@RequestBody Company company, @RequestHeader String token) {
		
		 try {
			this.administratorService.deleteCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	
	
	/** delete the company and all of it's coupons from the database
	 * @param id
	 * @throws CouponSystemException
	 */
	
	@DeleteMapping ("/company/delete/{id}")
	public void deletCompany (@PathVariable int id, @RequestHeader String token) {
		try {
			this.administratorService.deleteCompany(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	
	
	/** returns all of the companies registered
	 * @return List
	 * @throws CouponSystemException
	 */
	
	@GetMapping ("/company/get")
	public List<Company> getAllCompanies(@RequestHeader String token) {
		try {
			return this.administratorService.getAllCompanies();
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	/** returns a company whose id was given
	 * @param id
	 * @return Company
	 * @throws CouponSystemException
	 */
	
	@GetMapping ("/company/get/{id}")
	public Company getCompany (@PathVariable int id, @RequestHeader String token) {
		try {
			return this.administratorService.getCompany(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	
	/** add a customer do the database. customer with already existing email
	 * cannot be added
	 * @param customer
	 * @return the id of the added company
	 * @throws CouponSystemException
	 */
	@PostMapping ("/customer/add")
	public int addCustomer (@RequestBody Customer customer, @RequestHeader String token) {
		try {
			return this.administratorService.addCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	/** Updates customer in the database.
	 * @param customer
	 * @throws CouponSystemException
	 */
	@PutMapping ("/customer/update")
	public void updateCustomer (@RequestBody Customer customer, @RequestHeader String token) {
		try {
			this.administratorService.updateCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	
	/** delete the customer and all of it's coupons from the database
	 * @param customer
	 * @throws CouponSystemException
	 */
	@DeleteMapping ("/customer/delete")
	public void deleteCustomer (@RequestBody Customer customer, @RequestHeader String token) {
		try {
			this.administratorService.deleteCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	
	/** delete the customer and all of it's coupons from the database
	 * @param customer
	 * @throws CouponSystemException
	 */
	@DeleteMapping ("/customer/delete/{id}")
	public void deleteCustomer (@PathVariable int id, @RequestHeader String token) {
		try {
			this.administratorService.deleteCustomer(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	/** returns all customers registered
	 * @return List
	 * @throws CouponSystemException
	 */
	@GetMapping ("/customer/get")
	public List<Customer> getAllCustomers(@RequestHeader String token) {
		try {
			return this.administratorService.getAllCustomer();
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	/** returns the customer whose id was given
	 * @param id
	 * @return Customer
	 * @throws CouponSystemException
	 */
	@GetMapping ("/customer/get/{id}")
	public Customer getCustomer(@PathVariable int id, @RequestHeader String token) {
		try {
			return this.administratorService.getCustomer(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	

}
