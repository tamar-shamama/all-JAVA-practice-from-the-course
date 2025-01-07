package beans;

import exceptions.CouponSystemException;

/** those are the categories the coupons
 * are sorted into
 * 
 * @author Tamar
 */
public enum Category {
	
	/* in the database, the categories exist only as an
	 * id number the coupons are sorted into. the coupon DAO
	 * sorts those id into the categories in this ENUM
	 */
	
	
	FOOD(1), ELECTRICITY(2), RESTAURANT(3), VACATION(4);
	
	public final int id;
	
	private Category(int id) {
		this.id = id;
	}
	
	public int getCategoryId() {
		return id;
	}
	
	public static Category getCategoryById(int id) throws CouponSystemException  {
		
		Category cat = null;
		Category[] categories = Category.values();
		
		for (int i = 0; i < categories.length; i++) {
			if (categories[i].getCategoryId() == id) {
				cat = categories[i];
				return cat;
			}
		}
		throw new CouponSystemException("catagory id=" + id + "does not exist");
	}
	
	

}
