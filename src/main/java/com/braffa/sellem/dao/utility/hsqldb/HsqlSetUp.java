package com.braffa.sellem.dao.utility.hsqldb;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Register;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUsers;
import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;
import com.braffa.sellem.model.xml.webserviceobjects.product.Products;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalogs;

public class HsqlSetUp {
	
	private static final Logger logger = Logger
			.getLogger(HsqlSetUp.class);

	public void recreateDatabase () {
		if (logger.isDebugEnabled()) {
			logger.debug("recreateDatabase");
		}

		try {
			CreateDatabase.instance.setUpLogin();
			List<Login> lOfLogins = LoginDAOHsqldb.instance.getAllLogins();
			for (Login login : lOfLogins) {
				System.out.println(login.toString());
			}

			CreateDatabase.instance.setUpRegister();
			Register register = RegisteredUsersDAOHsqldb.instance
					.getAllRegisteredUsers();
			RegisteredUsers registeredUsers = register.getRegisteredUsers();
			List<RegisteredUser> lOfRegisteredUser = registeredUsers
					.getlOfRegisteredUser();
			for (RegisteredUser registeredUser : lOfRegisteredUser) {
				System.out.println(registeredUser.toString());
			}

			CreateDatabase.instance.setUpCatalog();
			Catalog catalog = CatalogDAOHsqldb.instance.getCatalog();
			Products producsts = catalog.getProducts();
			List<Product> lOfProducts = producsts.getlOfProducts();
			for (Product product : lOfProducts) {
				System.out.println(product.toString());
			}

			CreateDatabase.instance.setUpUserToCatalog();
			UserToCatalogs userToCatalogs = UserToCatalogDAOHsqldb.instance
					.getUserToCatalog();
			List<UserToCatalog> lOfUserToCatalog = userToCatalogs
					.getlOfUserToCatalog();
			for (UserToCatalog userToCatalog : lOfUserToCatalog) {
				System.out.println(userToCatalog.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void viewLogin () {
		if (logger.isDebugEnabled()) {
			logger.debug("viewlogin");
		}
		try {
			List<Login> lOfLogins = LoginDAOHsqldb.instance.getAllLogins();
			for (Login login : lOfLogins) {
				System.out.println(login.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewRegisteredUser() {
		if (logger.isDebugEnabled()) {
			logger.debug("viewRegisteredUser");
		}
		try {
			Register register = RegisteredUsersDAOHsqldb.instance
					.getAllRegisteredUsers();
			RegisteredUsers registeredUsers = register.getRegisteredUsers();
			List<RegisteredUser> lOfRegisteredUser = registeredUsers
					.getlOfRegisteredUser();
			for (RegisteredUser registeredUser : lOfRegisteredUser) {
				System.out.println(registeredUser.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void viewCatalog() {
		if (logger.isDebugEnabled()) {
			logger.debug("viewcatalog");
		}
		try {
			Catalog catalog = CatalogDAOHsqldb.instance.getCatalog();
			Products producsts = catalog.getProducts();
			List<Product> lOfProducts = producsts.getlOfProducts();
			for (Product product : lOfProducts) {
				System.out.println(product.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewUserToCatalog(Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("viewUserToCatalog");
		}
		try {
			UserToCatalogs userToCatalogs = UserToCatalogDAOHsqldb.instance
					.getUserToCatalog();
			List<UserToCatalog> lOfUserToCatalog = userToCatalogs
					.getlOfUserToCatalog();
			for (UserToCatalog userToCatalog : lOfUserToCatalog) {
				System.out.println(userToCatalog.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		HsqlSetUp hsqlSetUp = new HsqlSetUp();
		hsqlSetUp.recreateDatabase();
		
		hsqlSetUp.viewLogin(); 
		hsqlSetUp.viewRegisteredUser();
		hsqlSetUp.viewCatalog();
	}

}
