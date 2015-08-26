package com.braffa.sellem.hbn.dao.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.dao.utility.mysql.MySQLSetUp;
import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.xml.authentication.XmlLogin;
import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;
import com.braffa.sellem.model.xml.formatter.XmlFormatter;

public class LoginDaoTest {

	private static final Logger logger = Logger.getLogger(LoginDaoTest.class);

	@BeforeClass
	public static void setUp() {
		if (logger.isDebugEnabled()) {
			logger.debug("setUp");
		}
		try {
			MySQLSetUp mySQLSetUp = new MySQLSetUp();
			mySQLSetUp.setUpLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void shutDown () {
		//Dao.shutdown();
	}
	
	@Test
	public void loginDaoTest() {
		
		XmlFormatter xmlFormatter = new XmlFormatter ();
		
		// test getCount
		LoginDao loginDao = new LoginDao(new XmlLoginMsg());
		int count = loginDao.getCount();
		assertEquals("getCount failed Incorrect number of rows ", 4, count);
		
		// test read
		XmlLogin xmlLogin = new XmlLogin();
		xmlLogin.setUserId("Braffa");
		XmlLoginMsg xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("test read start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		loginDao = new LoginDao(xmlLoginMsg);
		XmlLoginMsg actualXmlLogins = (XmlLoginMsg)loginDao.read();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		XmlLogin actualXmlLogin = actualXmlLogins.getLOfLogins().get(0);
		assertEquals("getLogin failed Incorrect UserId returned ", "Braffa",
				actualXmlLogin.getUserId());
		assertEquals("getLogin failed Incorrect authorityLevel returned ", "99",
				actualXmlLogin.getAuthorityLevel());
		assertEquals("getLogin failed Incorrect password returned ", "amanda33",
				actualXmlLogin.getPassword());
		assertTrue("getLogin failed crDate is null", null != actualXmlLogin.getCrDate());
		assertTrue("getLogin failed UpdDate is null", null != actualXmlLogin.getUpdDate());
		System.out.println("test read result");
		xmlFormatter.formatXMLLogins(actualXmlLogins);
		
		// test read all
		xmlLogin = new XmlLogin();
		xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		loginDao = new LoginDao(xmlLoginMsg);
		System.out.println("test readAll start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		xmlLoginMsg = (XmlLoginMsg)loginDao.readAll();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		ArrayList<XmlLogin> lOfLogins = xmlLoginMsg.getLOfLogins();
		assertEquals("getAllLogins failedIncorrect number of login objects returned ", 4,
				lOfLogins.size());
		System.out.println("test readAll result");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);

		// test createLogin
		xmlLogin = setUpXmlLogin ("77777", "scott", "daniel");
		xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("test create start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		loginDao = new LoginDao(xmlLoginMsg);
		loginDao.create();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		count = loginDao.getCount();
		assertEquals("createLogin failed Incorrect number of rows ", 5, count);
		
		// test deleteLogin
		xmlLogin = setUpXmlLogin (null, null, "daniel");
		xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("test delete start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		loginDao = new LoginDao(xmlLoginMsg);
		loginDao.delete();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		count = loginDao.getCount();
		assertEquals("deleteLogin failed Incorrect number of rows ", 4, count);

		// test updateLogin - authority level
		
		xmlLogin = setUpXmlLogin("33333", null, "georgie");
		xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("test update start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		loginDao = new LoginDao(xmlLoginMsg);
		XmlLoginMsg expectedXmlLogins = (XmlLoginMsg) loginDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		XmlLogin expectedXmlLogin = expectedXmlLogins.getLOfLogins().get(0);
		assertEquals("Update of authority level failed ", "33333",
				expectedXmlLogin.getAuthorityLevel());
		System.out.println("test readAll result");
		xmlFormatter.formatXMLLogins(expectedXmlLogins);
		
		// test updateLogin - password
		
		xmlLogin = setUpXmlLogin(null, "password", "georgie");
		xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("test update start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		loginDao = new LoginDao(xmlLoginMsg);
		expectedXmlLogins = (XmlLoginMsg) loginDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		expectedXmlLogin = expectedXmlLogins.getLOfLogins().get(0);
		assertEquals("Update of password failed ", "password",
				expectedXmlLogin.getPassword());
		System.out.println("test update result");
		xmlFormatter.formatXMLLogins(expectedXmlLogins);

		// test updateLogin - authority level and  password

		xmlLogin = setUpXmlLogin("22222", "blessthem", "georgie");
		xmlLoginMsg = new XmlLoginMsg(xmlLogin);
		System.out.println("test update start");
		xmlFormatter.formatXMLLogins(xmlLoginMsg);
		loginDao = new LoginDao(xmlLoginMsg);
		expectedXmlLogins = (XmlLoginMsg) loginDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlLogins.getSuccess());
		expectedXmlLogin = expectedXmlLogins.getLOfLogins().get(0);
		assertEquals("Update of password failed ", "blessthem",
				expectedXmlLogin.getPassword());
		assertEquals("Update of authority level failed ", "22222",
				expectedXmlLogin.getAuthorityLevel());
		System.out.println("test update result");
		xmlFormatter.formatXMLLogins(expectedXmlLogins);

	}
	
	private XmlLogin setUpXmlLogin (String authorityLevel, String password, String userId) {
		XmlLogin xmlLogin = new XmlLogin(authorityLevel, password, userId);
		return xmlLogin;
	}
	
}
