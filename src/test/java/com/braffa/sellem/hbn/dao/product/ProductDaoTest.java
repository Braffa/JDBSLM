package com.braffa.sellem.hbn.dao.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.dao.utility.mysql.MySQLSetUp;
import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.xml.formatter.XmlFormatter;
import com.braffa.sellem.model.xml.product.XmlProduct;
import com.braffa.sellem.model.xml.product.XmlProductMsg;

public class ProductDaoTest {

	private static final Logger logger = Logger.getLogger(ProductDaoTest.class);

	public static ProductDao productDao;

	@BeforeClass
	public static void setUp() {
		if (logger.isDebugEnabled()) {
			logger.debug("setUp");
		}
		try {
			MySQLSetUp mySQLSetUp = new MySQLSetUp();
			mySQLSetUp.setUpProduct();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @AfterClass
	//public static void tearDown() {
		// Dao.shutdown();
	//}

	@Test
	public void productDaoTest() {

		XmlFormatter xmlFormatter = new XmlFormatter();

		XmlProductMsg xmlProductMsg = new XmlProductMsg();
		productDao = new ProductDao(xmlProductMsg);

		// test getCount

		int count = productDao.getCount();
		assertEquals("getCount failed Incorrect number of rows ", 6, count);

		// test read

		xmlProductMsg = new XmlProductMsg();
		XmlProduct xmlProduct = new XmlProduct();
		xmlProduct.setProductid("9780789724410");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest read start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		XmlProductMsg products = (XmlProductMsg) productDao.read();
		assertEquals("read failed success flag not set correctly", "true",
				products.getSuccess());

		XmlProduct actualProduct = products.getLOfProducts().get(0);
		assertEquals("getProduct failed Incorrect author returned ",
				"Mark Wutka", actualProduct.getAuthor());
		assertTrue("getProduct failed crDate is null",
				null != actualProduct.getCrDate());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg",
				actualProduct.getImageLargeURL());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg",
				actualProduct.getImageURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ",
				"QUE", actualProduct.getManufacturer());
		assertEquals("getProduct failed Incorrect product group returned ",
				"Book", actualProduct.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ",
				"9780789724410", actualProduct.getProductid());
		assertEquals("getProduct failed Incorrect product type returned ",
				"EAN", actualProduct.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon",
				actualProduct.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ",
				"789724413", actualProduct.getSourceid());
		assertEquals(
				"getProduct failed Incorrect title returned ",
				"Using Java Server Pages and Servlets Special Edition (Special Edition Using)",
				actualProduct.getTitle());
		assertTrue("getProduct failed updDate is null",
				null != actualProduct.getUpdDate());
		System.out.println("productDaoTest read result");
		xmlFormatter.formatXmlProducts(products);

		// test readAll
		

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest readAll start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		products = (XmlProductMsg) productDao.readAll();

		assertEquals("getLogin failed success flag not set correctly", "true",
				products.getSuccess());

		products.getLOfProducts();
		ArrayList<XmlProduct> lOfProducts = products.getLOfProducts();
		assertEquals(
				"getAllProducts failedIncorrect number of product objects returned ",
				6, lOfProducts.size());
		System.out.println("productDaoTest readAll start");
		xmlFormatter.formatXmlProducts(products);
		
		// read list of products
		
		lOfProducts = new ArrayList<XmlProduct>();
		XmlProduct product = new XmlProduct();
		product.setProductid("9780789724410");
		lOfProducts.add(product);
		product = new XmlProduct();
		product.setProductid("9780789799999");
		lOfProducts.add(product);
		product = new XmlProduct();
		product.setProductid("978098056856");
		lOfProducts.add(product);
		xmlProductMsg = new XmlProductMsg();
		xmlProductMsg.setLOfProducts(lOfProducts);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest readLOfProducts start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		products = (XmlProductMsg) productDao.readListOfKeys();
		assertEquals("read failed success flag not set correctly", "true",
				products.getSuccess());
		assertEquals("readLOfProducts expected 3 rows returned", 3,
				products.getLOfProducts().size());	

		// test createProduct

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setAuthor("Dave Brayfield");
		xmlProduct
				.setImageLargeURL("http://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg");
		xmlProduct
				.setImageURL("ttp://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg");
		xmlProduct.setManufacturer("braffa");
		xmlProduct.setProductgroup("DVD");
		xmlProduct.setProductid("1234567891234");
		xmlProduct.setProductidtype("ISBN");
		xmlProduct.setSource("witton Park");
		xmlProduct.setTitle("How to screw things up");

		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest create start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		products = productDao.create();
		assertEquals("getLogin failed success flag not set correctly", "true",
				products.getSuccess());
		count = productDao.getCount();
		assertEquals("createProduct failed Incorrect number of rows ", 7, count);
		productDao = new ProductDao(xmlProductMsg);

		// test deleteProduct

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("9780789724410");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest delete start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		productDao.delete();

		count = productDao.getCount();
		assertEquals("deleteProduct failed Incorrect number of rows ", 6, count);
		
		// test remove

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("9780789799999");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest remove start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		productDao.remove();

		count = productDao.getCount();
		assertEquals("deleteProduct failed Incorrect number of rows ", 5, count);

		// test updateProduct Author

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setAuthor("James Smith");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		XmlProductMsg actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect author returned ",
				"James Smith", actualProduct.getAuthor());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct large image URL

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct
				.setImageLargeURL("http://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals(
				"updateProduct failed Incorrect large image URL returned ",
				"http://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg",
				actualProduct.getImageLargeURL());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct image URL

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct
				.setImageURL("http://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51YKR0ZVKRL._SL75_.jpg",
				actualProduct.getImageURL());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct manufacturer URL

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setManufacturer("Dickens");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect manufacturer returned ",
				"Dickens", actualProduct.getManufacturer());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct manufacturer

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setProductgroup("Magazine");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect product group returned ",
				"Magazine", actualProduct.getProductgroup());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct product id type

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setProductidtype("EAN");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals(
				"updateProduct failed Incorrect product id type returned ",
				"EAN", actualProduct.getProductidtype());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct source

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setSource("Tesco");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect source returned ",
				"Tesco", actualProduct.getSource());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct source id

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setSourceid("123456");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect source id returned ",
				"123456", actualProduct.getSourceid());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test updateProduct title

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setProductid("5050582388237");
		xmlProduct.setTitle("should this work");
		xmlProductMsg.setProduct(xmlProduct);
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest update start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.update();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("updateProduct failed Incorrect title returned ",
				"should this work", actualProduct.getTitle());
		System.out.println("productDaoTest update result");
		xmlFormatter.formatXmlProducts(actualProductMsg);

		// test getProductBySearch

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setAuthor("Dave Brayfield");
		xmlProductMsg.setProduct(xmlProduct);
		xmlProductMsg.setSearchField("author");
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest search start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.search();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		lOfProducts = actualProductMsg.getLOfProducts();
		assertEquals("getProductBySearch by author failed expected 1 object",
				1, lOfProducts.size());
		actualProduct = actualProductMsg.getLOfProducts().get(0);
		assertEquals("getProductBySearch by author failed", "Dave Brayfield",
				actualProduct.getAuthor());
		assertEquals("getProductBySearch by author failed",
				"How to screw things up", actualProduct.getTitle());

		xmlProductMsg = new XmlProductMsg();
		xmlProduct = new XmlProduct();
		xmlProduct.setAuthor("s");
		xmlProductMsg.setProduct(xmlProduct);
		xmlProductMsg.setSearchField("author");
		productDao = new ProductDao(xmlProductMsg);
		System.out.println("productDaoTest search start");
		xmlFormatter.formatXmlProducts(xmlProductMsg);
		actualProductMsg = (XmlProductMsg) productDao.search();
		assertEquals("getLogin failed success flag not set correctly", "true",
				actualProductMsg.getSuccess());

		lOfProducts = actualProductMsg.getLOfProducts();
		assertEquals("getProductBySearch by author failed expected 4 object",
				4, lOfProducts.size());

	}
}
