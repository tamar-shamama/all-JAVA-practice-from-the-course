package dao_s;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Company;
import connection_pool.ConnectionPool;
import exceptions.CompanyException;

public class CompaniesDAO implements CompaniesDAOinterface<Company> {
	
	ConnectionPool cp = ConnectionPool.getInstance();

	
	/**returns true if a company with given email and password exist in the
	 *database
	 */
	@Override
	public boolean isCompanyExist(Connection con, String email, String password) throws CompanyException {
		
		String sqlOrder = "select * from companies where `email` = ? and `password` = ?";
		
		try  {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new CompanyException("isCompanyExist failed", e);
		}
		
		return false;
	}

	
	
	
	/**
	 *add company (object) to the database. the company's coupon are not added to
	 *the database.
	 */
	@Override
	public void addCompany(Connection con, Company company) throws CompanyException {
		
		String sqlOrder = "insert into companies values (?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setInt(1, company.getCompany_id());
			ps.setString(2, company.getCompany_name());
			ps.setString(3, company.getCompany_email());
			ps.setString(4, company.getCompany_password());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CompanyException("addCompany failed", e);
		}
		
		
		
	}

	/**updates the company (name, email & password) whose id is the id of the company
	 * given. id does not change, and coupons list of the company does not change
	 */
	@Override
	public void updateCompany(Connection con, Company company) throws CompanyException {
		
		String sqlOrder = "update companies set name=?, email=?, password=?"
				+ " where id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setString(1, company.getCompany_name());
			ps.setString(2, company.getCompany_email());
			ps.setString(3, company.getCompany_password());
			ps.setInt(4, company.getCompany_id());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new CompanyException("updateCompany failed", e);
		}
		
		
	}

	/** delete the company whose id is given, from the database.
	 * a company can not be deleted if there are any coupons belong
	 * to it in the database.
	 */
	@Override
	public void deleteCompany(Connection con, int company_id) throws CompanyException {
		
		String sqlOrder = "delete from companies where id = " + company_id;
		
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sqlOrder);
			
		} catch (SQLException e) {
			throw new CompanyException("deleteCompany failed", e);
		}
		
		
	}

	/**creates a list of company objects from all of the companies in the database, <b> without</b> 
	 * the list of coupons those companies had created. this list will be added in the facade.
	 */
	@Override
	public List<Company> getAllCompanies(Connection con) throws CompanyException {
		
		String sqlOrder = "select * from companies";
		List<Company> companies = new ArrayList<>();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			while (rs.next()) {
				
				Company company = new Company();
				company.setCompany_id(rs.getInt("id"));
				company.setCompany_name(rs.getString("name"));
				company.setCompany_email(rs.getString("email"));
				company.setCompany_password(rs.getString("password"));
				companies.add(company);
			}
			
			return companies;
			
			
		} catch (SQLException e) {
			throw new CompanyException("getAllCompanies failed", e);
		}
	}

	
	
	/**creates a company object from the database with the id given, <b> without</b> the list of coupons
	 * the company had created. this list will be added in the facade.
	 */
	@Override
	public Company getCompany(Connection con, int company_id) throws CompanyException {
		
		String sqlOrder = "select * from companies where id = " + company_id;
		Company company = new Company();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			if (rs.next()) {
				company.setCompany_id(rs.getInt("id"));
				company.setCompany_name(rs.getString("name"));
				company.setCompany_email(rs.getString("email"));
				company.setCompany_password(rs.getString("password"));
				return company;
			}
			throw new CompanyException("company id=" + company_id + " does not exist");
			
		} catch (SQLException e) {
			throw new CompanyException("getCompany failed", e);
		}
		
	}
	
	/**creates a company object from the database with the given email, <b> without</b> the list of coupons
	 * the company had created. this list will be added in the facade.
	 */
	public Company getCompany(Connection con, String company_mail) throws CompanyException {
		
		String sqlOrder = "select * from companies where email = '" + company_mail + "'";
		Company company = new Company();
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sqlOrder);
			
			if (rs.next()) {
				company.setCompany_id(rs.getInt("id"));
				company.setCompany_name(rs.getString("name"));
				company.setCompany_email(rs.getString("email"));
				company.setCompany_password(rs.getString("password"));
				return company;
			}
			throw new CompanyException("company name=" + company_mail + " does not exist");
			
		} catch (SQLException e) {
			throw new CompanyException("getCompany failed", e);
		}
		
	}

}
