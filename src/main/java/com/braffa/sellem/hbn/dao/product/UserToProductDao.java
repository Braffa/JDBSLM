package com.braffa.sellem.hbn.dao.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.hbn.entity.UserToProduct;
import com.braffa.sellem.model.xml.product.XmlUserToProduct;
import com.braffa.sellem.model.xml.product.XmlUserToProductMsg;

public class UserToProductDao extends Dao {

	private static final Logger logger = Logger
			.getLogger(UserToProductDao.class);

	private XmlUserToProductMsg xmlUserToProductMsg;

	public UserToProductDao(XmlUserToProductMsg xmlUserToProductMsg) {
		this.xmlUserToProductMsg = xmlUserToProductMsg;
	}

	@Override
	public XmlUserToProductMsg create() {
		if (logger.isDebugEnabled()) {
			logger.debug("createUserToProduct");
		}
		try {
			UserToProduct newUserToProduct = new UserToProduct(
					xmlUserToProductMsg.getUserToProduct());
			StringBuffer sql = new StringBuffer();
			sql.append("From UserToProduct WHERE PRODUCTID = '");
			sql.append(newUserToProduct.getProductId());
			sql.append("' AND USERID = '");
			sql.append(newUserToProduct.getUserId());
			sql.append("'");

			Session session = this.getHibernateSessionFactory().openSession();
			session.beginTransaction();
			Query q = session.createQuery(sql.toString());
			List<UserToProduct> lOfUserToProducts = q.list();
			if (lOfUserToProducts.size() == 0) {
				session.save(newUserToProduct);
			} else {
				newUserToProduct.setProductIndex(lOfUserToProducts.size());
				session.save(newUserToProduct);
			}
			session.getTransaction().commit();
			xmlUserToProductMsg.setSuccess("true");
		} catch (Exception e) {
			xmlUserToProductMsg.setSuccess("false");
		}
		return xmlUserToProductMsg;
	}

	@Override
	public void delete() {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteProduct");
		}
		try {
			XmlUserToProduct xmlUserToProduct = xmlUserToProductMsg
					.getUserToProduct();
			StringBuffer sql = new StringBuffer();
			sql.append("From UserToProduct WHERE PRODUCTID = '");
			sql.append(xmlUserToProduct.getProductId());
			sql.append("' AND USERID = '");
			sql.append(xmlUserToProduct.getUserId());
			sql.append("'");
			Session session = this.getHibernateSessionFactory().openSession();
			session.beginTransaction();
			Query q = session.createQuery(sql.toString());
			List<UserToProduct> lOfUserToProducts = q.list();
			if (lOfUserToProducts.size() == 1) {
				UserToProduct UserToProductToDelete = lOfUserToProducts.get(0);
				UserToProduct deletedUserToProduct = (UserToProduct) session
						.get(UserToProduct.class, UserToProductToDelete.getId());
				session.delete(deletedUserToProduct);
			}
			session.getTransaction().commit();
			xmlUserToProductMsg.setSuccess("true");
		} catch (Exception e) {
			xmlUserToProductMsg.setSuccess("false");
		}
	}

	@Override
	public int getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		Session session = this.getHibernateSessionFactory().openSession();
		Query q = session.createQuery("From UserToProduct ");
		List<UserToProduct> lOfUserToProducts = q.list();
		return lOfUserToProducts.size();
	}

	@Override
	public XmlUserToProductMsg read() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserToProduct");
		}
		try {
			XmlUserToProduct xmlUserToProduct = xmlUserToProductMsg
					.getUserToProduct();
			StringBuffer sql = new StringBuffer();
			sql.append("From UserToProduct WHERE PRODUCTID = '");
			sql.append(xmlUserToProduct.getProductId());
			sql.append("' AND USERID = '");
			sql.append(xmlUserToProduct.getUserId());
			sql.append("' AND PRODUCTINDEX = ");
			sql.append(xmlUserToProduct.getProductIndex());
			if (logger.isDebugEnabled()) {
				logger.debug(sql);
			}
			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session.createQuery(sql.toString());
			List<UserToProduct> lOfUserToProducts = q.list();
			ArrayList<XmlUserToProduct> lOfXmlUserToProducts = new ArrayList<XmlUserToProduct>();
			for (UserToProduct userToProduct : lOfUserToProducts) {
				XmlUserToProduct readUserToProduct = new XmlUserToProduct(
						userToProduct);
				lOfXmlUserToProducts.add(readUserToProduct);
			}
			xmlUserToProductMsg.setLOfXmlUserToProduct(lOfXmlUserToProducts);
			xmlUserToProductMsg.setSuccess("true");
		} catch (Exception e) {
			e.printStackTrace();
			xmlUserToProductMsg.setSuccess("false");
		}
		return xmlUserToProductMsg;
	}

	@Override
	public XmlUserToProductMsg readAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("readAll");
		}
		try {
			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session.createQuery("From UserToProduct ");
			List<UserToProduct> lOfUserToProducts = q.list();
			ArrayList<XmlUserToProduct> lOfXmlUserToProducts = new ArrayList<XmlUserToProduct>();
			for (UserToProduct userToProduct : lOfUserToProducts) {
				XmlUserToProduct xmlUserToProduct = new XmlUserToProduct(
						userToProduct);
				lOfXmlUserToProducts.add(xmlUserToProduct);
			}
			xmlUserToProductMsg.setLOfXmlUserToProduct(lOfXmlUserToProducts);
			xmlUserToProductMsg.setSuccess("true");
		} catch (Exception e) {
			xmlUserToProductMsg.setSuccess("false");
		}
		return xmlUserToProductMsg;
	}

	public XmlUserToProductMsg search() {
		if (logger.isDebugEnabled()) {
			logger.debug("search");
		}
		try {
			XmlUserToProduct xmlUserToProduct = xmlUserToProductMsg
					.getUserToProduct();
			StringBuffer sql = new StringBuffer();
			sql.append("From UserToProduct WHERE ");
			if (xmlUserToProductMsg.getSearchField().equals("PRODUCTID")) {
				sql.append(" PRODUCTID = '");
				sql.append(xmlUserToProduct.getProductId());
				sql.append("'");
			} else if (xmlUserToProductMsg.getSearchField().equals("USERID")) {
				sql.append(" USERID = '");
				sql.append(xmlUserToProduct.getUserId());
				sql.append("'");
			}

			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session.createQuery(sql.toString());
			List<UserToProduct> lOfUserToProducts = q.list();
			ArrayList<XmlUserToProduct> lOfXmlUserToProducts = new ArrayList<XmlUserToProduct>();
			for (UserToProduct userToProduct : lOfUserToProducts) {
				XmlUserToProduct readUserToProduct = new XmlUserToProduct(
						userToProduct);
				lOfXmlUserToProducts.add(readUserToProduct);
			}
			xmlUserToProductMsg.setLOfXmlUserToProduct(lOfXmlUserToProducts);
			xmlUserToProductMsg.setSuccess("true");
		} catch (Exception e) {
			xmlUserToProductMsg.setSuccess("false");
		}
		return xmlUserToProductMsg;
	}

	@Override
	public Object update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object readListOfKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
