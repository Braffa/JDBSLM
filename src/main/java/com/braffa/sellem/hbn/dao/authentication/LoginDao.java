package com.braffa.sellem.hbn.dao.authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.model.hbn.entity.Login;
import com.braffa.sellem.model.xml.authentication.XmlLogin;
import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;

public class LoginDao extends Dao {

	private static final Logger logger = Logger.getLogger(LoginDao.class);

	private XmlLoginMsg loginMsg;

	public LoginDao(XmlLoginMsg aLoginMsg) {
		loginMsg = aLoginMsg;
	}

	@Override
	public XmlLoginMsg create() {
		if (logger.isDebugEnabled()) {
			logger.debug("saveLogin");
		}
		try {
			Login newLogin = new Login(loginMsg.getXmllogin());
			newLogin.setCrDate(new Date());
			newLogin.setUpdDate(new Date());
			Session session = this.getHibernateSessionFactory().openSession();
			session.beginTransaction();
			Login login = (Login) session
					.get(Login.class, newLogin.getUserId());
			if (null == login) {
				session.save(newLogin);
			}
			session.getTransaction().commit();
			loginMsg.setXmllogin(new XmlLogin());
			loginMsg.setSuccess("true");
		} catch (Exception e) {
			loginMsg.setSuccess("false");
		}
		return loginMsg;
	}

	@Override
	public void delete() {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteLogin");
		}
		try {
			Session session = this.getHibernateSessionFactory().openSession();
			session.beginTransaction();
			Login login = (Login) session.get(Login.class, loginMsg
					.getXmllogin().getUserId());
			if (null != login) {
				session.delete(login);
			}
			session.getTransaction().commit();
			loginMsg.setXmllogin(new XmlLogin());
			loginMsg.setSuccess("true");
		} catch (Exception e) {
			loginMsg.setSuccess("false");
		}
	}

	public int getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		Session session = this.getHibernateSessionFactory().openSession();
		Query q = session.createQuery("From Login ");
		List<Login> lOfLogins = q.list();
		return lOfLogins.size();
	}

	@Override
	public Object read() {
		if (logger.isDebugEnabled()) {
			logger.debug("getLogin");
		}
		try {
			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session.createQuery("From Login WHERE USERID = '"
					+ loginMsg.getXmllogin().getUserId() + "'");
			List<Login> lOfLogins = q.list();
			XmlLogin xmlLogin = new XmlLogin(lOfLogins.get(0));
			ArrayList<XmlLogin> lOfXmlLogin = new ArrayList<XmlLogin>();
			lOfXmlLogin.add(xmlLogin);
			loginMsg.setLOfLogins(lOfXmlLogin);
			loginMsg.setSuccess("true");
		} catch (Exception e) {
			loginMsg.setSuccess("false");
		}
		return loginMsg;
	}

	@Override
	public XmlLoginMsg readAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllXMLLogins");
		}
		try {
			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session.createQuery("From Login ");
			List<Login> lOfLogins = q.list();
			ArrayList<XmlLogin> lOfXmlLogin = new ArrayList<XmlLogin>();
			for (Login login : lOfLogins) {
				XmlLogin xmlLogin = new XmlLogin(login);
				lOfXmlLogin.add(xmlLogin);
			}
			loginMsg.setLOfLogins(lOfXmlLogin);
			loginMsg.setSuccess("true");
		} catch (Exception e) {
			loginMsg.setSuccess("false");
		}
		return loginMsg;
	}

	public XmlLoginMsg update() {
		if (logger.isDebugEnabled()) {
			logger.debug("updateLogin");
		}
		try {
			Login updatedLogin = new Login(loginMsg.getXmllogin());
			Session session = this.getHibernateSessionFactory().openSession();
			session.beginTransaction();
			Login login = (Login) session.get(Login.class,
					updatedLogin.getUserId());
			boolean changed = false;
			if (null != login) {
				if (null != updatedLogin.getAuthorityLevel()
						&& updatedLogin.getAuthorityLevel() != login
								.getAuthorityLevel()) {
					login.setAuthorityLevel(updatedLogin.getAuthorityLevel());
					changed = true;
				}
				if (null != updatedLogin.getPassword()
						&& updatedLogin.getPassword() != login.getPassword()) {
					login.setPassword(updatedLogin.getPassword());
					changed = true;
				}
				if (changed) {
					login.setUpdDate(new Date());
					session.update(login);
					session.getTransaction().commit();
				}
			}
			ArrayList<XmlLogin> lOfXmlLogin = new ArrayList<XmlLogin>();
			lOfXmlLogin.add(new XmlLogin(login));
			loginMsg.setXmllogin(new XmlLogin());
			loginMsg.setLOfLogins(lOfXmlLogin);
			loginMsg.setSuccess("true");
		} catch (Exception e) {
			loginMsg.setSuccess("false");
		}
		return loginMsg;
	}

	@Override
	public Object search() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}
}
