package com.braffa.sellem.hbn;

import org.apache.log4j.Logger;

import com.braffa.sellem.hbn.dao.authentication.LoginDao;
import com.braffa.sellem.hbn.dao.authentication.RegisteredUserDao;
import com.braffa.sellem.hbn.dao.product.ProductDao;
import com.braffa.sellem.hbn.dao.product.UserToProductDao;
import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.product.XmlProductMsg;
import com.braffa.sellem.model.xml.product.XmlUserToProductMsg;

public class DaoFactory {
	
	private static final Logger logger = Logger.getLogger(DaoFactory.class);

	public enum daoType {LOGIN_DAO, REGISTERED_USER_DAO, PRODUCT_DAO, USER_TO_PRODUCT_DAO}

	public static Dao getDAO (daoType aDaoType, Object xml) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDAO aDaoType " + aDaoType + " xml "  + xml);
		}
		
		switch (aDaoType) {
		case LOGIN_DAO:
			XmlLoginMsg xmlLoginMsg = (XmlLoginMsg)xml;
			return new LoginDao(xmlLoginMsg);
		case REGISTERED_USER_DAO:
			XmlRegisteredUserMsg xmlRegisteredUserMsg = (XmlRegisteredUserMsg)xml;
			return new RegisteredUserDao(xmlRegisteredUserMsg);
		case PRODUCT_DAO:
			XmlProductMsg xmlProductMsg = (XmlProductMsg)xml;
			return new ProductDao(xmlProductMsg);
		case USER_TO_PRODUCT_DAO:
			XmlUserToProductMsg xmlUserToProductMsg = (XmlUserToProductMsg)xml;
			return new UserToProductDao(xmlUserToProductMsg);
		default:
			break;
		}
		
	
		return null;
	}
}
