package com.braffa.sellem.hbn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.dao.utility.mysql.MySQLSetUp;
import com.braffa.sellem.hbn.DaoFactory.daoType;
import com.braffa.sellem.model.hbn.entity.Login;
import com.braffa.sellem.model.xml.authentication.XmlLogin;
import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUser;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.formatter.XmlFormatter;

public class DaoFactoryTest {
	
	private static final Logger logger = Logger.getLogger(DaoFactoryTest.class);


	@BeforeClass
	public static void setUp() {
		if (logger.isDebugEnabled()) {
			logger.debug("setUp");
		}
		try {
			MySQLSetUp mySQLSetUp = new MySQLSetUp();
			mySQLSetUp.setUpLogin();
			mySQLSetUp.setUpRegisteredUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void daoFactoryTest() {
		XmlFormatter xmlFormatter = new XmlFormatter();
		XmlLogin xmlLogin = new XmlLogin();
		xmlLogin.setUserId("Braffa");
		XmlLoginMsg xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("DaoFactoryTest read start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);

		Dao loginDao = DaoFactory.getDAO(daoType.LOGIN_DAO, xmlLoginMsg);
		
		XmlLoginMsg actualXmlLogins = (XmlLoginMsg)loginDao.read();
		
		XmlLogin actualXmlLogin = actualXmlLogins.getLOfLogins().get(0);
		assertEquals("getLogin failed Incorrect UserId returned ", "Braffa",
				actualXmlLogin.getUserId());
		assertEquals("getLogin failed Incorrect authorityLevel returned ", "99",
				actualXmlLogin.getAuthorityLevel());
		assertEquals("getLogin failed Incorrect password returned ", "amanda33",
				actualXmlLogin.getPassword());
		assertTrue("getLogin failed crDate is null", null != actualXmlLogin.getCrDate());
		assertTrue("getLogin failed UpdDate is null", null != actualXmlLogin.getUpdDate());

		
		xmlFormatter.formatXMLLogins(actualXmlLogins);
		
		XmlRegisteredUser registeredUser = new XmlRegisteredUser();
		XmlLogin login = new XmlLogin();
		registeredUser.setLogin(login);
		registeredUser.getLogin().setUserId("Braffa");
		XmlRegisteredUserMsg xmlRegisteredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("DaoFactoryTest read start");
		xmlFormatter.formatXmlRegisteredUsers(xmlRegisteredUserMsg);
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, xmlRegisteredUserMsg);
		XmlRegisteredUserMsg actualXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.read();
		XmlRegisteredUser actualXmlRegisteredUser = actualXmlRegisteredUsers
				.getLOfRegisteredUsers().get(0);

		assertEquals("getRegisteredUser failed Incorrect UserId returned ",
				"Braffa", actualXmlRegisteredUser.getLogin().getUserId());
		assertEquals("getRegisteredUser failed Incorrect email returned ",
				"dave.rogers@yahoo.co.uk", actualXmlRegisteredUser.getEmail());
		assertEquals("getRegisteredUser failed Incorrect first name returned ",
				"Dave", actualXmlRegisteredUser.getFirstname());
		assertEquals("getRegisteredUser failed Incorrect last name returned ",
				"Rogers", actualXmlRegisteredUser.getLastname());
		assertEquals(
				"getRegisteredUser failed Incorrect ltelephone number returned ",
				"01388 445566", actualXmlRegisteredUser.getTelephone());
		assertTrue("getRegisteredUser failed crDate is null",
				null != actualXmlRegisteredUser.getCrDate());
		assertTrue("getRegisteredUser failed updDate is null",
				null != actualXmlRegisteredUser.getUpdDate());

		System.out.println("test read result");
		xmlFormatter.formatXmlRegisteredUsers(actualXmlRegisteredUsers);
		
	}

}
