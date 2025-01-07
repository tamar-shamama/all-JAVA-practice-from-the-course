package dao_s;

import java.sql.Connection;
import java.util.List;

import beans.Company;
import exceptions.CompanyException;

public interface CompaniesDAOinterface<C> {
	
	boolean isCompanyExist(Connection con, String email, String password) throws CompanyException;
	
	void addCompany(Connection con, C company) throws CompanyException;
	
	void updateCompany(Connection con, C company) throws CompanyException;
	
	void deleteCompany(Connection con, int company_id) throws CompanyException;
	
	List<Company> getAllCompanies(Connection con) throws CompanyException;
	
	C getCompany(Connection con, int company_id) throws CompanyException;
	
	

}
