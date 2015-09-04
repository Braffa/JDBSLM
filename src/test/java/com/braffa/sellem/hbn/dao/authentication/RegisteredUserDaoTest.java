package com.braffa.sellem.hbn.dao.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.dao.utility.mysql.MySQLSetUp;
import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.hbn.entity.Login;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUser;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.formatter.XmlFormatter;

public class RegisteredUserDaoTest {

	private static final Logger logger = Logger
			.getLogger(RegisteredUserDaoTest.class);

	private RegisteredUserDao registeredUserDao;

	@BeforeClass
	public static void setUp() {
		if (logger.isDebugEnabled()) {
			logger.debug("setUp");
		}
		try {
			MySQLSetUp mySQLSetUp = new MySQLSetUp();
			mySQLSetUp.setUpRegisteredUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void shutDown () {
		//Dao.shutdown();
	}

	@Test
	public void registeredUserDaoTest() {
		
		XmlFormatter xmlFormatter = new XmlFormatter ();

		// test count
		
		XmlRegisteredUser registeredUser = new XmlRegisteredUser();
		XmlRegisteredUserMsg registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		int count = registeredUserDao.getCount();
		assertEquals("getCountTest failedIncorrect number of rows ", 4, count);

		// test getRegisteredUser
		
		registeredUser = new XmlRegisteredUser();
		Login login = new Login();
		registeredUser.setLogin(login);
		registeredUser.getLogin().setUserId("Braffa");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test read start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		
		XmlRegisteredUserMsg actualXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.read();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());
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
		assertTrue("getRegisteredUser failed crDate is null",
				null != actualXmlRegisteredUser.getCrDate());

		System.out.println("test read result");
		xmlFormatter.formatXmlRegisteredUsers(actualXmlRegisteredUsers);

		// test getAllRegisteredUser

		registeredUser = new XmlRegisteredUser();
		login = new Login();
		registeredUser.setLogin(login);
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test read start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		
		actualXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.readAll();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());
		assertEquals(
				"getAllRegisteredUsers failed Incorrect number of registered user objects returned ",
				4, actualXmlRegisteredUsers.getLOfRegisteredUsers().size());
		System.out.println("test readAll result");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		
		// read by list of keys
		registeredUserMsg = new XmlRegisteredUserMsg();
		
		ArrayList<XmlRegisteredUser> lOfRegisteredUsers = new ArrayList<XmlRegisteredUser>();
		Login login1 = new Login(); 
		login1.setUserId("Braffa");
		XmlRegisteredUser registeredUser1 = new XmlRegisteredUser();
		registeredUser1.setLogin(login1);
		lOfRegisteredUsers.add(registeredUser1);
		Login login2 = new Login(); 
		login2.setUserId("georgie");
		XmlRegisteredUser registeredUser2 = new XmlRegisteredUser();
		registeredUser2.setLogin(login2);
		lOfRegisteredUsers.add(registeredUser2);
		Login login3 = new Login(); 
		login3.setUserId("SUE123");
		XmlRegisteredUser registeredUser3 = new XmlRegisteredUser();
		registeredUser3.setLogin(login3);
		lOfRegisteredUsers.add(registeredUser3);
		registeredUserMsg.setLOfRegisteredUsers(lOfRegisteredUsers);
		System.out.println("test read by list of keys start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		actualXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.readListOfKeys();
		assertEquals("readListOfKeys failed Incorrect number of rows ", 3, actualXmlRegisteredUsers.getLOfRegisteredUsers().size());

		// test create registered user

		registeredUser = new XmlRegisteredUser();
		registeredUser.setCrDate(new Date());
		registeredUser.setEmail("dave_allen476@hotmail.com");
		registeredUser.setFirstname("Alan");
		registeredUser.setLastname("Mills");
		registeredUser.setTelephone("01388 445561");
		registeredUser.setUpdDate(new Date());
		registeredUser.setLogin(new Login());
		registeredUser.getLogin().setUserId("gordon");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test create start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);		
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		registeredUserDao.create();
		count = registeredUserDao.getCount();
		assertEquals("createRegisteredUser failed Incorrect number of rows ",
				5, count);

		// test deleteRegisteredUser
		
		registeredUser = new XmlRegisteredUser();
		login = new Login();
		registeredUser.setLogin(login);
		registeredUser.getLogin().setUserId("gordon");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test delete start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		registeredUserDao.delete();
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());

		count = registeredUserDao.getCount();
		assertEquals("deleteRegisteredUser failed Incorrect number of rows ",
				4, count);

		// test updateRegisteredUser - email

		registeredUser = new XmlRegisteredUser();
		registeredUser.setLogin(new Login());
		registeredUser.getLogin().setUserId("Braffa");
		registeredUser.setEmail("dave.brayfield@gmail.com");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test update email start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());
		
		XmlRegisteredUserMsg expectedXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.update();
		
		ArrayList<XmlRegisteredUser> expectedLOfRegisteredUsers = expectedXmlRegisteredUsers
				.getLOfRegisteredUsers();
		assertEquals("Update of email failed ", "dave.brayfield@gmail.com",
				expectedLOfRegisteredUsers.get(0).getEmail());
		System.out.println("test update email result");
		xmlFormatter.formatXmlRegisteredUsers(expectedXmlRegisteredUsers);
		
		// test updateRegisteredUser - first name
		
		registeredUser = new XmlRegisteredUser();
		registeredUser.setLogin(new Login());
		registeredUser.getLogin().setUserId("Braffa");
		registeredUser.setFirstname("Paul");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test update first name start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		expectedXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.update();
		
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());
		
		expectedLOfRegisteredUsers = expectedXmlRegisteredUsers.getLOfRegisteredUsers();
		assertEquals("Update of first name failed ", "Paul",
				expectedLOfRegisteredUsers.get(0).getFirstname());
		System.out.println("test update fist name result");
		xmlFormatter.formatXmlRegisteredUsers(expectedXmlRegisteredUsers);
		

		// test updateRegisteredUser - last name
		
		registeredUser = new XmlRegisteredUser();
		registeredUser.setLogin(new Login());
		registeredUser.getLogin().setUserId("Braffa");
		registeredUser.setLastname("Davison");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test update last name start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		expectedXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.update();
		
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());
		
		expectedLOfRegisteredUsers = expectedXmlRegisteredUsers.getLOfRegisteredUsers();
		assertEquals("Update of last name failed ", "Davison",
				expectedLOfRegisteredUsers.get(0).getLastname());
		System.out.println("test update last name result");
		xmlFormatter.formatXmlRegisteredUsers(expectedXmlRegisteredUsers);

		// test updateRegisteredUser - telephone
		
		registeredUser = new XmlRegisteredUser();
		registeredUser.setLogin(new Login());
		registeredUser.getLogin().setUserId("Braffa");
		registeredUser.setTelephone("07525 641834");
		registeredUserMsg = new XmlRegisteredUserMsg(registeredUser);
		System.out.println("test update telephone start");
		xmlFormatter.formatXmlRegisteredUsers(registeredUserMsg);
		registeredUserDao = new RegisteredUserDao(registeredUserMsg);
		expectedXmlRegisteredUsers = (XmlRegisteredUserMsg)registeredUserDao.update();
		
		assertEquals("getLogin failed success flag not set correctly", "true", actualXmlRegisteredUsers.getSuccess());
		
		expectedLOfRegisteredUsers = expectedXmlRegisteredUsers.getLOfRegisteredUsers();
		assertEquals("Update of telephone failed ", "07525 641834",
				expectedLOfRegisteredUsers.get(0).getTelephone());
		System.out.println("test update telephone result");
		xmlFormatter.formatXmlRegisteredUsers(expectedXmlRegisteredUsers);
		
		
		
		
		
	}

}
