package app.core.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	
	List<Coupon> findAllByCompanyId (int id);
	List<Coupon> findAllByCompanyIdAndCategory(int id, Category category);
	List<Coupon> findAllByCompanyIdAndPriceLessThanEqual(int id, double price);
	
	boolean existsByCompanyId (int id);
	boolean existsByCompanyIdAndTitle (int id, String title);
	
	Coupon findByCompanyIdAndTitle (int id, String title);
	
	List<Coupon> findAllByCustomersId (int id);
	List<Coupon> findAllByCustomersIdAndCategory (int id, Category category);
	List<Coupon> findAllByCustomersIdAndPriceLessThanEqual (int id, double price);
	
	boolean existsByIdAndCustomersId(int id, int customerId);
	
	List<Coupon> findAllByExpirationDateBefore(LocalDate now);
	
	boolean existsByIdAndAmountGreaterThan(int id, int amount);
	boolean existsByIdAndExpirationDateBefore(int id, LocalDate localDate);
	
	boolean existsByTitle (String title);
	
	
	
	
	

}
