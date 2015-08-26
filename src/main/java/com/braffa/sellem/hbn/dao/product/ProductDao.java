package com.braffa.sellem.hbn.dao.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.hbn.entity.Product;
import com.braffa.sellem.model.xml.product.XmlProduct;
import com.braffa.sellem.model.xml.product.XmlProductMsg;

public class ProductDao extends Dao {

	private static final Logger logger = Logger.getLogger(ProductDao.class);

	private XmlProductMsg xmlProductMsg;

	public ProductDao(XmlProductMsg xmlProductMsg) {
		this.xmlProductMsg = xmlProductMsg;
	}

	@Override
	public XmlProductMsg create() {
		if (logger.isDebugEnabled()) {
			logger.debug("createProduct");
		}
		Product newProduct = new Product(xmlProductMsg.getProduct());
		Session session = getHibernateSessionFactory().openSession();
		session.beginTransaction();
		try {
			Product product = (Product) session.get(Product.class,
					newProduct.getProductId());
			if (null == product) {
				session.save(newProduct);
			}
			xmlProductMsg.setSuccess("true");
		} catch (Exception e) {
			xmlProductMsg.setSuccess("false");
		}
		session.getTransaction().commit();
		return xmlProductMsg;
	}

	@Override
	public void delete() {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteProduct");
		}
		Session session = getHibernateSessionFactory().openSession();
		session.beginTransaction();
		try {
			Product product = (Product) session.get(Product.class,
					xmlProductMsg.getProduct().getProductid());
			if (null != product) {
				session.delete(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
	}

	@Override
	public int getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		Session session = getHibernateSessionFactory().openSession();
		Query q = session.createQuery("From Product ");
		List<Product> lOfProducts = q.list();
		return lOfProducts.size();
	}

	@Override
	public XmlProductMsg read() {
		if (logger.isDebugEnabled()) {
			logger.debug("read productid "
					+ xmlProductMsg.getProduct().getProductid());
		}
		Session session = getHibernateSessionFactory().openSession();
		try {
			Query q = session.createQuery("From Product WHERE productId = '"
					+ xmlProductMsg.getProduct().getProductid() + "'");
			List<Product> lOfProducts = q.list();
			if (lOfProducts.size() > 0) {
				XmlProduct xmlProduct = new XmlProduct(lOfProducts.get(0));
				ArrayList<XmlProduct> lOfxmlProduct = new ArrayList<XmlProduct>();
				lOfxmlProduct.add(xmlProduct);
				xmlProductMsg.setLOfProducts(lOfxmlProduct);
				xmlProductMsg.setSuccess("true");
			} else {
				ArrayList<XmlProduct> lOfxmlProduct = new ArrayList<XmlProduct>();
				xmlProductMsg.setLOfProducts(lOfxmlProduct);
				xmlProductMsg.setSuccess("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			xmlProductMsg.setSuccess("false");
		}
		return xmlProductMsg;
	}

	@Override
	public XmlProductMsg readAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("readAll ");
		}
		Session session = getHibernateSessionFactory().openSession();
		try {
			Query q = session.createQuery("From Product");
			List<Product> lOfProducts = q.list();
			ArrayList<XmlProduct> lOfxmlProduct = new ArrayList<XmlProduct>();
			for (Product product : lOfProducts) {
				XmlProduct xmlProduct = new XmlProduct(product);
				lOfxmlProduct.add(xmlProduct);
			}
			xmlProductMsg.setLOfProducts(lOfxmlProduct);
			xmlProductMsg.setSuccess("true");
		} catch (Exception e) {
			xmlProductMsg.setSuccess("false");
		}
		return xmlProductMsg;
	}

	@Override
	public XmlProductMsg update() {
		if (logger.isDebugEnabled()) {
			logger.debug("updateProduct");
		}
		try {
			XmlProduct xmlProduct = xmlProductMsg.getProduct();
			Product updatedProduct = new Product(xmlProduct);
			Session session = getHibernateSessionFactory().openSession();
			session.beginTransaction();
			Product product = (Product) session.get(Product.class,
					updatedProduct.getProductId());
			boolean changed = false;
			if (null != product) {
				if (null != updatedProduct.getAuthor()
						&& updatedProduct.getAuthor() != product.getAuthor()) {
					product.setAuthor(updatedProduct.getAuthor());
					changed = true;
				}
				if (null != updatedProduct.getImageLargeURL()
						&& updatedProduct.getImageLargeURL() != product
								.getImageLargeURL()) {
					product.setImageLargeURL(updatedProduct.getImageLargeURL());
					changed = true;
				}
				if (null != updatedProduct.getImageURL()
						&& updatedProduct.getImageURL() != product
								.getImageURL()) {
					product.setImageURL(updatedProduct.getImageURL());
					changed = true;
				}
				if (null != updatedProduct.getManufacturer()
						&& updatedProduct.getManufacturer() != product
								.getManufacturer()) {
					product.setManufacturer(updatedProduct.getManufacturer());
					changed = true;
				}
				if (null != updatedProduct.getProductgroup()
						&& updatedProduct.getProductgroup() != product
								.getProductgroup()) {
					product.setProductgroup(updatedProduct.getProductgroup());
					changed = true;
				}
				if (null != updatedProduct.getProductidtype()
						&& updatedProduct.getProductidtype() != product
								.getProductidtype()) {
					product.setProductidtype(updatedProduct.getProductidtype());
					changed = true;
				}
				if (null != updatedProduct.getSource()
						&& updatedProduct.getSource() != product.getSource()) {
					product.setSource(updatedProduct.getSource());
					changed = true;
				}
				if (null != updatedProduct.getSourceid()
						&& updatedProduct.getSourceid() != product
								.getSourceid()) {
					product.setSourceid(updatedProduct.getSourceid());
					changed = true;
				}
				if (null != updatedProduct.getTitle()
						&& updatedProduct.getTitle() != product.getTitle()) {
					product.setTitle(updatedProduct.getTitle());
					changed = true;
				}
				if (changed) {
					product.setUpdDate(new Date());
					session.update(product);
					session.getTransaction().commit();
				}
			}
			XmlProduct updatedXmlProduct = new XmlProduct(product);
			ArrayList<XmlProduct> lOfXmlProduct = new ArrayList<XmlProduct>();
			lOfXmlProduct.add(updatedXmlProduct);
			xmlProductMsg.setLOfProducts(lOfXmlProduct);
			xmlProductMsg.setSuccess("true");
		} catch (Exception e) {
			e.printStackTrace();
			xmlProductMsg.setSuccess("false");
		}
		return xmlProductMsg;
	}

	public XmlProductMsg search() {
		if (logger.isDebugEnabled()) {
			logger.debug("search");
		}
		XmlProduct searchProduct = xmlProductMsg.getProduct();
		String searchField = xmlProductMsg.getSearchField();
		StringBuffer sql = new StringBuffer(" FROM Product where ");

		if (searchField.equals("author")) {
			sql.append("LOWER(author) like '%"
					+ searchProduct.getAuthor().toLowerCase() + "%'");
		} else if (searchField.equals("title")) {
			sql.append("LOWER(title) like '%"
					+ searchProduct.getTitle().toLowerCase() + "%'");
		} else if (searchField.equals("productid")) {
			sql.append("LOWER(productid) like '%"
					+ searchProduct.getProductid().toLowerCase() + "%'");
		} else if (searchField.equals("manufacturer")) {
			sql.append("LOWER(manufacturer) like '%"
					+ searchProduct.getManufacturer().toLowerCase() + "%'");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("SQL " + sql);
		}
		Session session = getHibernateSessionFactory().openSession();
		try {
			Query q = session.createQuery(sql.toString());
			List<Product> lOfProducts = q.list();
			ArrayList<XmlProduct> lOfXmlProducts = new ArrayList<XmlProduct>();
			for (Product product : lOfProducts) {
				XmlProduct xmlProduct = new XmlProduct(product);
				lOfXmlProducts.add(xmlProduct);
			}
			xmlProductMsg.setLOfProducts(lOfXmlProducts);
			xmlProductMsg.setSuccess("true");
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
			xmlProductMsg.setSuccess("false");
		}
		return xmlProductMsg;
	}
	
	@Override
	public XmlProductMsg remove() {
		if (logger.isDebugEnabled()) {
			logger.debug("remove");
		}
		Session session = getHibernateSessionFactory().openSession();
		session.beginTransaction();
		try {
			Product product = (Product) session.get(Product.class,
					xmlProductMsg.getProduct().getProductid());
			if (null != product) {
				session.delete(product);
				XmlProduct xmlProduct = new XmlProduct();
				xmlProductMsg.setProduct(xmlProduct);
				xmlProductMsg.setSuccess("true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			xmlProductMsg.setSuccess("false");
		}
		session.getTransaction().commit();
		return xmlProductMsg;
	}

}
