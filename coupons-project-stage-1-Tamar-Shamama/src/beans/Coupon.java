package beans;

import java.time.LocalDate;


public class Coupon {
	
	private int coupon_id;
	private int company_id;
	private Category category;
	private String coupon_title;
	private String Description;
	private LocalDate startDate;
	private LocalDate endDate;
	
	/**how many units of this coupons are left
	 */
	private int amount;
	
	private double price;
	private String image;
	
	public Coupon() {
		super();
	}

	public Coupon(int coupon_id, int company_id, Category category, String title, String description, LocalDate startDate,
			LocalDate endDate, int amount, double price, String image) {
		super();
		this.coupon_id = coupon_id;
		this.company_id = company_id;
		this.category = category;
		this.coupon_title = title;
		Description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public int getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return coupon_title;
	}

	public void setTitle(String coupon_title) {
		this.coupon_title = coupon_title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "\ncoupon_id=" + coupon_id + ", company_id=" + company_id + ", category=" + category
				+ ", coupon_title=" + coupon_title + ", Description=" + Description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", amount=" + amount + ", price=" + price + ", image=" + image;
	}
	
	
	
	
	
	
	
	
	
	

}
