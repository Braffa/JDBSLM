package com.braffa.sellem.dao.utility.hsqldb;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class DatabaseResources {

	private static final Logger logger = Logger
			.getLogger(DatabaseResources.class);

	private static DatabaseResources _instance = null;

	public static String DB_DRIVER = "";
	public static String DB_JDBC = "";
	public static String DB_DATABASE = "";
	public static String DB_USER_NAME = "";
	public static String DB_PASSWORD = "";
	public static String DB_TYPE = "";

	public static String DB_HSQLDB = "hsqldb";

	protected DatabaseResources() {
		try {
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream("database.properties");
			prop.load(inputStream);
			DB_DRIVER = prop.getProperty("driver");
			DB_JDBC = prop.getProperty("jdbc");
			DB_DATABASE = prop.getProperty("database");
			DB_USER_NAME = prop.getProperty("dbUserName");
			DB_PASSWORD = prop.getProperty("dbPassword");
			DB_TYPE = prop.getProperty("dbtype");
		} catch (Exception e) {
			DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
			DB_JDBC = "jdbc:hsqldb:";
			DB_DATABASE = "sellMyThings";
			DB_USER_NAME = "braffa";
			DB_PASSWORD = "lindsay23";
			DB_TYPE = "hsqldb";
		}
	}

	static public DatabaseResources instance() {
		if (_instance == null) {
			_instance = new DatabaseResources();
			if (logger.isDebugEnabled()) {
				logger.debug("DB_DRIVER " + DB_DRIVER);
				logger.debug("DB_JDBC " + DB_JDBC);
				logger.debug("DB_DATABASE " + DB_DATABASE);
				logger.debug("DB_USER_NAME " + DB_USER_NAME);
				logger.debug("DB_PASSWORD " + DB_PASSWORD);
				logger.debug("DB_TYPE " + DB_TYPE);
			}
		}
		return _instance;
	}

}
