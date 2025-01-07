package beans;

import java.util.List;

/**a company can create coupons and cell units of those
 * coupon to it's customers. the company is <b>not</b>
 * allowed to cell more than a single unit of a coupon
 * to the same customer
 * 
 * @author Tamar
 */
public class Company {
	
	private int company_id;
	private String company_name;
	private String company_email;
	private String company_password;
	private List<Coupon> coupons;
	
	public Company() {
		super();
	}

	public Company(int company_id, String company_name, String company_email, String company_password,
			List<Coupon> coupons) {
		super();
		this.company_id = company_id;
		this.company_name = company_name;
		this.company_email = company_email;
		this.company_password = company_password;
		this.coupons = coupons;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_email() {
		return company_email;
	}

	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}

	public String getCompany_password() {
		return company_password;
	}

	public void setCompany_password(String company_password) {
		this.company_password = company_password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "\n" + "name:" + company_name + ", id: " + company_id + ", email: " + company_email;
	}
	
	
	
	
	
	

}
