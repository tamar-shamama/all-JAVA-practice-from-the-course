package beans;

import java.util.List;

/** a customer is a private person, a client of a company.
 * a customer is allowed to buy a single unit of each coupon
 * the company cells.
 * 
 * @author Tamar
 *
 */
public class Customer {
	
	private int customer_id;
	private String customer_first_name;
	private String customer_last_name;
	private String customer_email;
	private String customer_password;
	private List<Coupon> coupons;
	
	public Customer() {
		super();
	}

	public Customer(int customer_id, String customer_first_name, String customer_last_name, String customer_email,
			String customer_password, List<Coupon> coupons) {
		super();
		this.customer_id = customer_id;
		this.customer_first_name = customer_first_name;
		this.customer_last_name = customer_last_name;
		this.customer_email = customer_email;
		this.customer_password = customer_password;
		this.coupons = coupons;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_first_name() {
		return customer_first_name;
	}

	public void setCustomer_first_name(String customer_first_name) {
		this.customer_first_name = customer_first_name;
	}

	public String getCustomer_last_name() {
		return customer_last_name;
	}

	public void setCustomer_last_name(String customer_last_name) {
		this.customer_last_name = customer_last_name;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getCustomer_password() {
		return customer_password;
	}

	public void setCustomer_password(String customer_password) {
		this.customer_password = customer_password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "\n" + "id=" + customer_id + ", first name=" + customer_first_name
				+ ", last name=" + customer_last_name + ", email=" + customer_email
				+  "]";
	}
	
	
	
	
	
	
	

}
