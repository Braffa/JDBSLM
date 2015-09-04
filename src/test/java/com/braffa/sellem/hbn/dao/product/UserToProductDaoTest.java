package com.braffa.sellem.hbn.dao.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.dao.utility.mysql.MySQLSetUp;
import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.xml.formatter.XmlFormatter;
import com.braffa.sellem.model.xml.product.XmlUserToProduct;
import com.braffa.sellem.model.xml.product.XmlUserToProductMsg;

public class UserToProductDaoTest {

	private static final Logger logger = Logger
			.getLogger(UserToProductDaoTest.class);

	@BeforeClass
	public static void setUp() {
		if (logger.isDebugEnabled()) {
			logger.debug("setUp");
		}
		try {
			MySQLSetUp mySQLSetUp = new MySQLSetUp();
			mySQLSetUp.setUpUserToProductTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void shutDown() {
		// Dao.shutdown();
	}

	@Test
	public void userToProductDaoTest() {

		XmlFormatter xmlFormatter = new XmlFormatter();

		XmlUserToProduct xmlUserToProduct = new XmlUserToProduct();
		XmlUserToProductMsg xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);
		UserToProductDao userToProductDao = new UserToProductDao(
				xmlUserToProductMsg);

		// test getCount
		int count = userToProductDao.getCount();
		assertEquals("getCount failed Incorrect number of rows ", 9, count);

		// test read

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProduct.setUserId("Braffa");
		xmlUserToProduct.setProductId("9780789724410");
		xmlUserToProduct.setProductIndex(0);
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);
		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		System.out.println("userToProductDaoTest read start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		XmlUserToProductMsg actualUserToProductMsg = (XmlUserToProductMsg) userToProductDao
				.read();
		assertEquals("success flag not set correctly", "true",
				actualUserToProductMsg.getSuccess());

		ArrayList<XmlUserToProduct> lOfXmlUserToProduct = actualUserToProductMsg
				.getLOfXmlUserToProduct();
		assertEquals(
				"read failed Incorrect only expected 1 row to be returned ", 1,
				lOfXmlUserToProduct.size());
		XmlUserToProduct actualUserToProduct = lOfXmlUserToProduct.get(0);
		assertEquals("read failed Incorrect product id returned ",
				"9780789724410", actualUserToProduct.getProductId());
		assertEquals("read failed Incorrect product index returned ", 0,
				actualUserToProduct.getProductIndex());
		assertEquals("read failed Incorrect user id returned ", "Braffa",
				actualUserToProduct.getUserId());
		assertTrue("getUserToProductByUserId failed updDate is null",
				null != actualUserToProduct.getUpdDate());
		assertTrue("getUserToProductByUserId failed updDate is null",
				null != actualUserToProduct.getCrDate());

		System.out.println("userToProductDaoTest read result");
		xmlFormatter.formatXmlUserToProduct(actualUserToProductMsg);

		// test read all

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);
		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		System.out.println("userToProductDaoTest readAll start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		actualUserToProductMsg = (XmlUserToProductMsg) userToProductDao
				.readAll();
		assertEquals("success flag not set correctly", "true",
				actualUserToProductMsg.getSuccess());

		lOfXmlUserToProduct = actualUserToProductMsg.getLOfXmlUserToProduct();
		assertEquals(
				"read failed Incorrect only expected 7 row to be returned ", 9,
				lOfXmlUserToProduct.size());

		System.out.println("userToProductDaoTest readAll result");
		xmlFormatter.formatXmlUserToProduct(actualUserToProductMsg);

		// test search by user id

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProduct.setUserId("Braffa");
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);
		xmlUserToProductMsg.setSearchField("USERID");
		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		System.out.println("userToProductDaoTest search by user id start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		actualUserToProductMsg = (XmlUserToProductMsg) userToProductDao
				.search();

		assertEquals("success flag not set correctly", "true",
				actualUserToProductMsg.getSuccess());

		lOfXmlUserToProduct = actualUserToProductMsg.getLOfXmlUserToProduct();
		assertEquals(
				"search by user id  failed Incorrect only expected 5 row to be returned ",
				5, lOfXmlUserToProduct.size());

		System.out.println("userToProductDaoTest search by user id result");
		xmlFormatter.formatXmlUserToProduct(actualUserToProductMsg);

		// test search by product id

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProduct.setProductId("9780789724410");
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);
		xmlUserToProductMsg.setSearchField("PRODUCTID");
		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		System.out.println("userToProductDaoTest search by product id start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		actualUserToProductMsg = (XmlUserToProductMsg) userToProductDao
				.search();
		assertEquals("success flag not set correctly", "true",
				actualUserToProductMsg.getSuccess());

		lOfXmlUserToProduct = actualUserToProductMsg.getLOfXmlUserToProduct();
		assertEquals(
				"search by user id  failed Incorrect only expected 2 row to be returned ",
				2, lOfXmlUserToProduct.size());

		System.out.println("userToProductDaoTest search by product id result");
		xmlFormatter.formatXmlUserToProduct(actualUserToProductMsg);

		// test create

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProduct.setProductId("123456789123");
		xmlUserToProduct.setProductIndex(0);
		xmlUserToProduct.setUserId("wellington");
		xmlUserToProduct.setCrDate(new Date());
		xmlUserToProduct.setUpdDate(new Date());
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);

		System.out.println("userToProductDaoTest create start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		actualUserToProductMsg = userToProductDao.create();
		assertEquals("success flag not set correctly", "true",
				actualUserToProductMsg.getSuccess());

		count = userToProductDao.getCount();
		assertEquals("getCount failed Incorrect number of rows ", 10, count);

		// test create aUserToProduct when product id and userid already exists

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProduct.setProductId("123456789123");
		xmlUserToProduct.setProductIndex(0);
		xmlUserToProduct.setUserId("wellington");
		xmlUserToProduct.setCrDate(new Date());
		xmlUserToProduct.setUpdDate(new Date());
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);

		System.out.println("userToProductDaoTest create start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		actualUserToProductMsg = userToProductDao.create();
		assertEquals("success flag not set correctly", "true",
				actualUserToProductMsg.getSuccess());

		count = userToProductDao.getCount();
		assertEquals("getCount failed Incorrect number of rows ", 11, count);

		// test delete aUserToProduct

		xmlUserToProduct = new XmlUserToProduct();
		xmlUserToProduct.setProductId("123456789123");
		xmlUserToProduct.setProductIndex(0);
		xmlUserToProduct.setUserId("wellington");
		xmlUserToProductMsg = new XmlUserToProductMsg();
		xmlUserToProductMsg.setUserToProduct(xmlUserToProduct);

		System.out.println("userToProductDaoTest delete start");
		xmlFormatter.formatXmlUserToProduct(xmlUserToProductMsg);

		userToProductDao = new UserToProductDao(xmlUserToProductMsg);
		userToProductDao.delete();

		count = userToProductDao.getCount();
		assertEquals("getCount failed Incorrect number of rows ", 11, count);

	}

}
