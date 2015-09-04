package com.braffa.sellem.hbn.dao.authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.hbn.HibernateUtil;
import com.braffa.sellem.model.hbn.entity.Product;
import com.braffa.sellem.model.hbn.entity.RegisteredUser;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUser;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.product.XmlProduct;

public class RegisteredUserDao extends Dao {

	private static final Logger logger = Logger
			.getLogger(RegisteredUserDao.class);

	private XmlRegisteredUserMsg xmlRegisteredUserMsg;

	public RegisteredUserDao(XmlRegisteredUserMsg registeredUserMsg) {
		xmlRegisteredUserMsg = registeredUserMsg;
	}

	@Override
	public XmlRegisteredUserMsg create() {
		if (logger.isDebugEnabled()) {
			logger.debug("createRegisteredUser");
		}
		try {
			XmlRegisteredUser xmlRegisteredUser = xmlRegisteredUserMsg
					.getRegisteredUser();
			RegisteredUser newRegisteredUser = new RegisteredUser(
					xmlRegisteredUser);
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			RegisteredUser registeredUser = (RegisteredUser) session.get(
					RegisteredUser.class, newRegisteredUser.getUserId());
			if (null == registeredUser) {
				session.save(newRegisteredUser);
			}
			session.getTransaction().commit();
			xmlRegisteredUserMsg.setRegisteredUser(new XmlRegisteredUser());
			xmlRegisteredUserMsg.setSuccess("true");
		} catch (Exception e) {
			xmlRegisteredUserMsg.setSuccess("false");
		}
		return xmlRegisteredUserMsg;
	}

	@Override
	public void delete() {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteRegisteredUser");
		}
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			RegisteredUser registeredUser = (RegisteredUser) session.get(
					RegisteredUser.class, xmlRegisteredUserMsg
							.getRegisteredUser().getLogin().getUserId());
			if (null != registeredUser) {
				session.delete(registeredUser);
			}
			session.getTransaction().commit();
			xmlRegisteredUserMsg.setRegisteredUser(new XmlRegisteredUser());
			xmlRegisteredUserMsg.setSuccess("true");
		} catch (Exception e) {
			xmlRegisteredUserMsg.setSuccess("false");
		}
	}

	public int getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Session session = this.getHibernateSessionFactory().openSession();
		Query q = session.createQuery("From RegisteredUser ");
		List<RegisteredUser> lOfRegisteredUser = q.list();
		return lOfRegisteredUser.size();
	}

	@Override
	public XmlRegisteredUserMsg read() {
		if (logger.isDebugEnabled()) {
			logger.debug("getRegisteredUser");
		}
		try {
			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session
					.createQuery("From RegisteredUser WHERE USERID = '"
							+ xmlRegisteredUserMsg.getRegisteredUser()
									.getLogin().getUserId() + "'");
			List<RegisteredUser> lOfRegisteredUsers = q.list();
			XmlRegisteredUser xmlRegisteredUser = new XmlRegisteredUser(
					lOfRegisteredUsers.get(0));
			ArrayList<XmlRegisteredUser> lOfXmlRegisteredUsers = new ArrayList<XmlRegisteredUser>();
			lOfXmlRegisteredUsers.add(xmlRegisteredUser);
			xmlRegisteredUserMsg.setLOfRegisteredUsers(lOfXmlRegisteredUsers);
			xmlRegisteredUserMsg.setSuccess("true");
		} catch (Exception e) {
			xmlRegisteredUserMsg.setSuccess("false");
		}
		return xmlRegisteredUserMsg;
	}

	@Override
	public XmlRegisteredUserMsg readAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("getRegisteredUser");
		}
		try {
			Session session = this.getHibernateSessionFactory().openSession();
			Query q = session.createQuery("From RegisteredUser");
			List<RegisteredUser> lOfRegisteredUsers = q.list();
			ArrayList<XmlRegisteredUser> lOfXmlRegisteredUsers = new ArrayList<XmlRegisteredUser>();
			for (RegisteredUser registeredUser : lOfRegisteredUsers) {
				XmlRegisteredUser xmlRegisteredUser = new XmlRegisteredUser(
						registeredUser);
				lOfXmlRegisteredUsers.add(xmlRegisteredUser);
			}
			xmlRegisteredUserMsg.setLOfRegisteredUsers(lOfXmlRegisteredUsers);
			xmlRegisteredUserMsg.setSuccess("true");
		} catch (Exception e) {
			xmlRegisteredUserMsg.setSuccess("false");
		}
		return xmlRegisteredUserMsg;
	}

	@Override
	public XmlRegisteredUserMsg update() {
		if (logger.isDebugEnabled()) {
			logger.debug("updateRegisteredUser");
		}
		try {
			XmlRegisteredUser xmlRegisteredUser = xmlRegisteredUserMsg
					.getRegisteredUser();
			RegisteredUser updatedRegisteredUser = new RegisteredUser(
					xmlRegisteredUser);
			Session session = this.getHibernateSessionFactory().openSession();
			session.beginTransaction();
			RegisteredUser registeredUser = (RegisteredUser) session.get(
					RegisteredUser.class, updatedRegisteredUser.getUserId());
			boolean changed = false;
			if (null != registeredUser) {
				if (null != updatedRegisteredUser.getEmail()
						&& updatedRegisteredUser.getEmail() != registeredUser
								.getEmail()) {
					registeredUser.setEmail(updatedRegisteredUser.getEmail());
					changed = true;
				}
				if (null != updatedRegisteredUser.getFirstname()
						&& updatedRegisteredUser.getFirstname() != registeredUser
								.getFirstname()) {
					registeredUser.setFirstname(updatedRegisteredUser
							.getFirstname());
					changed = true;
				}
				if (null != updatedRegisteredUser.getLastname()
						&& updatedRegisteredUser.getLastname() != registeredUser
								.getLastname()) {
					registeredUser.setLastname(updatedRegisteredUser
							.getLastname());
					changed = true;
				}
				if (null != updatedRegisteredUser.getTelephone()
						&& updatedRegisteredUser.getTelephone() != registeredUser
								.getTelephone()) {
					registeredUser.setTelephone(updatedRegisteredUser
							.getTelephone());
					changed = true;
				}
				if (changed) {
					registeredUser.setUpdDate(new Date());
					session.update(registeredUser);
					session.getTransaction().commit();
				}
			}
			ArrayList<XmlRegisteredUser> lOfXmlRegisteredUsers = new ArrayList<XmlRegisteredUser>();
			lOfXmlRegisteredUsers.add(new XmlRegisteredUser(registeredUser));
			xmlRegisteredUserMsg = new XmlRegisteredUserMsg(
					lOfXmlRegisteredUsers);
			xmlRegisteredUserMsg.setSuccess("true");
		} catch (Exception e) {
			xmlRegisteredUserMsg.setSuccess("false");
		}
		return xmlRegisteredUserMsg;
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

	@Override
	public Object readListOfKeys() {
		if (logger.isDebugEnabled()) {
			logger.debug("readListOfKeys");
		}
		Session session = getHibernateSessionFactory().openSession();
		try {
			StringBuffer sql = new StringBuffer("From RegisteredUser WHERE ");
			int index = xmlRegisteredUserMsg.getLOfRegisteredUsers().size();
			int count = 0;
			for (XmlRegisteredUser registeredUser : xmlRegisteredUserMsg.getLOfRegisteredUsers()) {
				sql.append("(userId = '" + registeredUser.getLogin().getUserId() + "')" );
				count++;
				if (index > 1 && count < index) {
					sql.append(" or ");
				}
			}
			System.out.println("sql " + sql.toString());
			Query q = session.createQuery(sql.toString());
			List<RegisteredUser> lOfRegisteredUsers = q.list();
			ArrayList<XmlRegisteredUser> lOfXmlRegisteredUsers = new ArrayList<XmlRegisteredUser>();
			for (RegisteredUser registeredUser : lOfRegisteredUsers) {
				XmlRegisteredUser xmlRegisteredUser = new XmlRegisteredUser(
						registeredUser);
				lOfXmlRegisteredUsers.add(xmlRegisteredUser);
			}
			xmlRegisteredUserMsg.setLOfRegisteredUsers(lOfXmlRegisteredUsers);
			xmlRegisteredUserMsg.setSuccess("true");
		} catch (Exception e) {
			xmlRegisteredUserMsg.setSuccess("false");
		}
		return xmlRegisteredUserMsg;
	}
}
