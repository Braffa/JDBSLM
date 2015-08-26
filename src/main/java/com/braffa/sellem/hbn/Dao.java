package com.braffa.sellem.hbn;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;

public abstract class Dao implements IBaseDao {

	private static final SessionFactory hibernateSessionFactory = buildHibernateSessionFactory();

	private static SessionFactory buildHibernateSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getHibernateSessionFactory().close();
	}

}
