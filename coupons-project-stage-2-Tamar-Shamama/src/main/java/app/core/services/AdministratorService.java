package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class AdministratorService extends ClientService {
	
	boolean logged = false;
	
	public boolean login (String email, String password) throws CouponSystemException {
		
		if (email.equals("admin@admin") && password.equals("admin")) {
			logged = true;
			return true;
			
		} else {
			throw new CouponSystemException("login failed - email and/or password incorrect");
		}
	}
	
	
	/** add a company do the database. company with already existing name or email
	 * cannot be added
	 * @param company
	 * @return the id of the added company
	 * @throws CouponSystemException
	 */
	public int addCompany (Company company) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("addCompany - not logged in");
		}
		
		if (companyRepository.existsByName(company.getName())) {
			throw new CouponSystemException("addCompany failed - company " + company.getName() + " exists allready");
		}
		if (companyRepository.existsByEmail(company.getEmail())) {
			throw new CouponSystemException("addCompany failed - company " + company.getEmail() + " exists allready");
		}
		return companyRepository.save(company).getId();
	}
	
	
	
	/** Updates company in the database. name cannot be change
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompany (Company company) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("updateCompany - not logged in");
		}
		
		if (!companyRepository.existsById(company.getId())) {
			throw new CouponSystemException("updateCompany failed - company " + company.getId() + " not exists");
		}
		if (companyRepository.existsByName(company.getName())) {
			throw new CouponSystemException("updateCompany failed - name cannot be change");
		}
		companyRepository.save(company);
	}
	
	
	/** delete the company and all of it's coupons from the database
	 * @param company
	 * @throws CouponSystemException
	 */
	public void deleteCompany (Company company) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("deleteCompany - not logged in");
		}
		
		if (!companyRepository.existsById(company.getId())) {
			throw new CouponSystemException("deleteCompany failed - company " + company.getId() + " not exists");
		}
		companyRepository.delete(company);
	}
	
	
	public List<Company> getAllCompanies() throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCompanies failed - not logged in");
		}
		return companyRepository.findAll();
	}
	
	public Company getCompanyById (int id) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getCompanyById failed - not logged in");
		}
		
		Optional<Company> c = companyRepository.findById(id);
		if (c.isPresent()) {
			return c.get();
		} else {
			throw new CouponSystemException("getCompanyById failed - not found");
		}
	}
	
	

	/** add a customer do the database. customer with already existing email
	 * cannot be added
	 * @param customer
	 * @return the id of the added company
	 * @throws CouponSystemException
	 */
	public int addCustomer (Customer customer) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("addCustomer failed - not logged in");
		}
		
		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new CouponSystemException("addCustomer failed - customer " + customer.getEmail() + " exists allready");
		}
		return customerRepository.save(customer).getId();
	}
	
	
	/** Updates customer in the database.
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer (Customer customer) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("updateCustomer failed - not logged in");
		}
		customerRepository.save(customer);
	}
	
	
	/** delete the customer and all of it's coupons from the database
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void deleteCustomer (Customer customer) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("deleteCustomer failed - not logged in");
		}
		
		if (!customerRepository.existsById(customer.getId())) {
			throw new CouponSystemException("deleteCustomer failed - customer " + customer.getId() + " not exists");
		}
		customerRepository.delete(customer);
	}
	
	
	public List<Customer> getAllCustomer() throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getAllCustomer failed - not logged in");
		}
		return customerRepository.findAll();
	}
	
	
	public Customer getCustomerById (int id) throws CouponSystemException {
		
		if (!isLogged()) {
			throw new CouponSystemException("getCustomerById failed - not logged in");
		}
		
		Optional<Customer> c = customerRepository.findById(id);
		if (c.isPresent()) {
			return c.get();
		} else {
			throw new CouponSystemException("getCustomerById failed - not found");
		}
	}
	
	
	public boolean isLogged() {
		if (logged) {
			return true;
		} else {
			return false;
		}
	}


	public boolean removeExpiredCoupons() {
		
		List<Coupon> list = couponRepository.findAllByExpirationDateBefore(LocalDate.now());
		
		if (!list.isEmpty()) {
			couponRepository.deleteAll(list);
			return true;
		}
		return false;
	}
	
	
	
	
	

}
