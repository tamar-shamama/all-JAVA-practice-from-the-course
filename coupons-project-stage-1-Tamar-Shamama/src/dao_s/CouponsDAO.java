package dao_s;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import beans.Category;
import beans.Coupon;
import exceptions.CouponException;
import exceptions.CouponSystemException;

/**
 * @author User
 *
 */
public class CouponsDAO implements CouponsDAOinterface<Coupon> {

	/**add a coupon to the database. the coupon must belong to an existing company
	 * already found in the database, meaning "company_id" must be the id of
	 * an existing company.
	 */
	@Override
	public void addCoupon(Connection con, Coupon coupon) throws CouponException {
		
		String sqlOrder = "insert into coupons values (?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setInt(1, coupon.getCoupon_id());
			ps.setInt(2, coupon.getCompany_id());
			ps.setInt(3, coupon.getCategory().getCategoryId());
			ps.setString(4, coupon.getTitle());
			ps.setString(5, coupon.getDescription());
			ps.setDate(6, Date.valueOf(coupon.getStartDate()));
			ps.setDate(7, Date.valueOf(coupon.getEndDate()));
			ps.setInt(8, coupon.getAmount());
			ps.setDouble(9, coupon.getPrice());
			ps.setString(10, coupon.getImage());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CouponException("addCoupon failed", e);
		}
		
	}

	/** updates a coupon on the database, meaning replace the coupon in the database
	 * with the given coupon. the id of the coupon can not be change.
	 */
	@Override
	public void updateCoupon(Connection con, Coupon coupon) throws CouponException {
		
		String sqlOrder = "update coupons set company_id=?, category_id=?,"
				+ "title=?, Description=?, start_Date=?, end_Date=?, amount=?, "
				+ "price=?, image=? where id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setInt(1, coupon.getCompany_id());
			ps.setInt(2, coupon.getCategory().getCategoryId());
			ps.setString(3, coupon.getTitle());
			ps.setString(4, coupon.getDescription());
			ps.setDate(5, Date.valueOf(coupon.getStartDate()));
			ps.setDate(6, Date.valueOf(coupon.getEndDate()));
			ps.setInt(7, coupon.getAmount());
			ps.setDouble(8, coupon.getPrice());
			ps.setString(9, coupon.getImage());
			ps.setInt(10, coupon.getCoupon_id());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CouponException("updateCoupon failed", e);
		}
		
		
	}

	/** delete a coupon from the database.
	 */
	@Override
	public void deleteCoupon(Connection con, int coupon_id) throws CouponException {
		
		String sqlOrder = "delete from coupons where id = " + coupon_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CouponException("deleteCoupon failed", e);
		}
	}
	
	
	/** deletes all of the coupons created by the company whose id is given from
	 * the database. 
	 * @param con
	 * @param company_id
	 * @throws CouponException
	 */
	public void deleteCouponsByCompanyId(Connection con, int company_id) throws CouponException {
		
		String sqlOrder = "delete from coupons where company_id = " + company_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CouponException("deleteCouponsByCompanyId", e);
		}
	}
	
	
	
	/** returns a list of all of the coupons that their end date passed (is before now).
	 * @param con
	 * @return
	 * @throws CouponSystemException
	 */
	public synchronized List<Coupon> getExpiredCoupons(Connection con) throws CouponSystemException {
		
		String sqlOrder = "select * from coupons where end_date < now()";
		List<Coupon> coupons = new ArrayList<>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				
				Coupon coupon = createCouponFromDB(rs);
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			throw new CouponException("getExpiredCoupons failed");
		}
		return coupons;
	}
	
	
	/** delete from the database all of the coupons whose end date passed (is before now).
	 * @param con
	 * @throws CouponException
	 */
	public synchronized void deleteExpiredCoupons(Connection con) throws CouponException {
		
		String sqlOrder = "delete from coupons where end_date < now()";
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CouponException("deleteExpiredCoupons failed", e);
		}
	}

	/** returns a list of all the coupons found in the database.
	 *
	 */
	@Override
	public List<Coupon> getAllCoupons(Connection con) throws CouponSystemException {
		
		String sqlOrder = "select * from coupons";
		List<Coupon> coupons = new ArrayList<>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				
				Coupon coupon = createCouponFromDB(rs);
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			throw new CouponException("getAllCoupons failed");
		}
		
		return coupons;
	}
	
	
	/**returns a list of all the coupons found in the database that belonged to the
	 * category whose id is given.
	 * @param con
	 * @param cat_id
	 * @return
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponsByCategory(Connection con, int cat_id) throws CouponSystemException {
		
		String sqlOrder = "select * from coupons where category_id =" + cat_id;
		List<Coupon> coupons = new ArrayList<>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				
				Coupon coupon = createCouponFromDB(rs);
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			throw new CouponException("getAllCoupons failed");
		}
		
		return coupons;
		
	}
	
	
	
	
	/**returns a list of all the coupons found in the database that belonged to the
	 * company whose id is given.
	 * @param con
	 * @param comp_id
	 * @return
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponsByCompany(Connection con, int comp_id) throws CouponSystemException {
		
		String sqlOrder = "select * from coupons where company_id =" + comp_id;
		List<Coupon> coupons = new ArrayList<>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				
				Coupon coupon = createCouponFromDB(rs);
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			throw new CouponException("getCouponsByCompany failed");
		}
		
		return coupons;
		
		
	}
	
	
	/** returns a coupon object from the database.
	 *
	 */
	@Override
	public Coupon getCoupon(Connection con, int coupon_id) throws CouponSystemException {
		
		String sqlOrder = "select * from coupons where id =" + coupon_id;
		Coupon coupon;
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			
			if (rs.next()) {
				
				coupon = createCouponFromDB(rs);
				return coupon;
				
			}
			throw new CouponException("coupon id=" + coupon_id + " does not exist");
			
		} catch (SQLException e) {
			throw new CouponException("getAllCoupons failed", e);
		}
	}

	
	
	
	
	/** add to the purchase history in the database a log to tell a specific client
	 * bought a specific coupon.
	 */
	@Override
	public void addCouponPurchase(Connection con, int customer_id, int coupon_id) throws CouponException {
		
		String sqlOrder = "insert into customers_vs_coupons values (?, ?);";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setInt(1, customer_id);
			ps.setInt(2, coupon_id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CouponException("addCouponPurchase failed - customer or coupon does not exist");
		}
		
	}
	
	
	/** returns a list of all the coupons purchased and the customers who purchased
	 * them from the database.
	 * @param con
	 * @param customer_id
	 * @return
	 * @throws CouponException
	 */
	public List<Coupon> getAllCouponsPurchasedBy(Connection con, int customer_id) throws CouponException {
		
		String sqlOrder = "select * from customers_vs_coupons where customer_id =" + customer_id;
		List<Coupon> list = new ArrayList<Coupon>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				Coupon tmpCoupon = new Coupon();
				tmpCoupon.setCoupon_id(rs.getInt("coupon_id"));
				int couponId = tmpCoupon.getCoupon_id();
				Coupon coupon = getCoupon(con, couponId);
				list.add(coupon);
			}
			return list;
			
		} catch (SQLException | CouponSystemException e) {
			throw new CouponException("addCouponPurchase failed - customer or coupon does not exist");
		}
	}

	
	
	/** deletes from the database the log of a specific coupon bought by a specific customer.
	 *
	 */
	@Override
	public void deleteCouponPurchase(Connection con, int customer_id, int coupon_id) throws CouponException {
		
		String sqlOrder = "delete from customers_vs_coupons where customer_id=" + customer_id +
				" and coupon_id=" + coupon_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CouponException("deleteCouponPurchase failed", e);
		}
		
	}
	
	/**deletes from the purchase history in the database all of the coupons 
	 * with the given coupon id;
	 * @param con
	 * @param coupon_id
	 * @throws CouponException
	 */
	public synchronized void deleteCouponPurchaseByCouponId(Connection con, int coupon_id) throws CouponException {
		
		String sqlOrder = "delete from customers_vs_coupons where coupon_id=" + coupon_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CouponException("deleteCouponPurchase failed", e);
		}
		
	}
	
	
	/**deletes from the purchase history in the database all of the coupons 
	 * bought by the customer with the given id
	 * @param con
	 * @param customer_id
	 * @throws CouponException
	 */
	public void deleteCouponPurchaseByCustomerId(Connection con, int customer_id) throws CouponException {
		
		String sqlOrder = "delete from customers_vs_coupons where customer_id=" + customer_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CouponException("deleteCouponPurchase failed", e);
		}
		
	}
	
	
	
	/** return true if the coupon given exist in the database
	 * @param con
	 * @param coupon
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean isCouponExist(Connection con, Coupon coupon) throws CouponSystemException {
		
		List<Coupon> couponList = getAllCoupons(con);
		for (Coupon c : couponList) {
			if (c.equals(coupon)) {
				return true;
			}
		}
		return false;
	}
	
	
	/** return true if the coupon given exist in the database
	 * @param con
	 * @param coupon_id
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean isCouponExistById(Connection con, int coupon_id) throws CouponSystemException {
		
		String sqlOrder = "select * from coupons where id =" + coupon_id;
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			
			if (rs.next()) {
				return true;
			} else {
			throw new CouponException("coupon id=" + coupon_id + " does not exist");
			}
			
		} catch (SQLException e) {
			throw new CouponException("isCouponExistById failed", e);
		}
	}
	
	
	
	/** creates a coupon object with the data taken from the database and found in a ResultSet
	 * object.
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws CouponSystemException
	 */
	public Coupon createCouponFromDB(ResultSet rs) throws SQLException, CouponSystemException {
		
		Coupon coupon = new Coupon();
		
		coupon.setCoupon_id(rs.getInt("id"));
		coupon.setCompany_id(rs.getInt("company_id"));
		
		int catId = rs.getInt("category_id");
		coupon.setCategory(Category.getCategoryById(catId));
		
		coupon.setTitle(rs.getString("title"));
		coupon.setDescription(rs.getString("description"));
		
		Date d1 = rs.getDate("start_date");
		LocalDate ld1 = d1.toLocalDate();
		coupon.setStartDate(ld1);
		
		Date d2 = rs.getDate("end_date");
		LocalDate ld2 = d2.toLocalDate();
		coupon.setEndDate(ld2);
		
		coupon.setAmount(rs.getInt("amount"));
		coupon.setPrice(rs.getDouble("price"));
		coupon.setImage(rs.getString("image"));
		
		return coupon;
		
	}

	
	

}
