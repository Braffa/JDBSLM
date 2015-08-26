package com.braffa.sellem.dao.utility.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalogs;



public enum UserToCatalogDAOHsqldb {
	instance;

	private static final Logger logger = Logger
			.getLogger(UserToCatalogDAOHsqldb.class);

	private String driver = "";
	private String jdbc = "";
	private String database = "";
	private String dbUserName = "";
	private String dbPassword = "";

	private UserToCatalogDAOHsqldb() {
		DatabaseResources.instance();
		driver = DatabaseResources.DB_DRIVER;
		jdbc = DatabaseResources.DB_JDBC;
		database = DatabaseResources.DB_DATABASE;
		dbUserName = DatabaseResources.DB_USER_NAME;
		dbPassword = DatabaseResources.DB_PASSWORD;
	}

	public String getCount(UserToCatalog userToCatalog) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;

		StringBuffer sql = new StringBuffer(
				"SELECT id, userId, productId, productIndex, crdate, upddate FROM USER_TO_CATALOG WHERE ");
		sql.append(" productId ='" + userToCatalog.getProductId() + "'");
		ps = connection.prepareStatement(sql.toString());
		ResultSet resultSet = ps.executeQuery();
		int rows = 0;
		while (resultSet.next()) {
			rows++;
		}
		resultSet.close();
		ps.close();
		connection.close();
		return String.valueOf(rows);
	}

	public UserToCatalogs getUserToCatalogByUserId(String userId)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		UserToCatalogs userToCatalogs = null;
		StringBuffer sql = new StringBuffer(
				"SELECT id, userId, productId, productIndex, crdate, upddate FROM USER_TO_CATALOG where userId = '"
						+ userId + "'");
		userToCatalogs = fetchUserToCatalogs(sql.toString());
		return userToCatalogs;
	}

	public UserToCatalogs getUserToCatalogByProductId(String ProductId)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		StringBuffer sql = new StringBuffer(
				"SELECT id, userId, productId, productIndex, crdate, upddate FROM USER_TO_CATALOG where productId = '"
						+ ProductId + "'");
		UserToCatalogs userToCatalogs = fetchUserToCatalogs(sql.toString());
		return userToCatalogs;
	}
	
	public UserToCatalogs getUserToCatalog()
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserToCatalog");
		}
		StringBuffer sql = new StringBuffer("SELECT id, userId, productId, productIndex, crdate, upddate FROM USER_TO_CATALOG ");
		sql.append("ORDER BY productId, userId, productIndex");
		UserToCatalogs userToCatalogs = fetchUserToCatalogs(sql.toString());
		return userToCatalogs;
	}	

	private UserToCatalogs fetchUserToCatalogs(String sql) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		List<UserToCatalog> lOfUserToCatalogs = new ArrayList<UserToCatalog>();
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		ps = connection.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			UserToCatalog userToCatalog = new UserToCatalog();
			userToCatalog.setUserId(resultSet.getString("userId"));
			userToCatalog.setProductId(resultSet.getString("productId"));
			userToCatalog.setProductIndex(resultSet.getString("productIndex"));
			Timestamp crDate = resultSet.getTimestamp("CRDATE");
			userToCatalog.setCrDate( new Date (crDate.getTime()));
			Timestamp updDate = resultSet.getTimestamp("UPDDATE");
			userToCatalog.setUpdDate( new Date (updDate.getTime()));
			lOfUserToCatalogs.add(userToCatalog);
		}
		UserToCatalogs userToCatalogs = new UserToCatalogs(lOfUserToCatalogs);
		resultSet.close();
		ps.close();
		connection.close();
		return userToCatalogs;
	}

	public void createUserToCatalog(UserToCatalog userToCatalog)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		StringBuffer sql = new StringBuffer(
				"INSERT INTO USER_TO_CATALOG (userId, productId, productIndex, crdate, upddate) VALUES (");
		sql.append("'" + userToCatalog.getUserId() + "', ");
		sql.append("'" + userToCatalog.getProductId() + "', ");
		sql.append("'" + userToCatalog.getProductIndex() + "', ");
		sql.append(" CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP");
		sql.append(")");
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		PreparedStatement ps;
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}

	public void deleteUserToCatalog(UserToCatalog userToCatalog)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		StringBuffer sql = new StringBuffer(
				"DELETE FROM USER_TO_CATALOG WHERE ");
		sql.append("userId = '" + userToCatalog.getUserId() + "'");
		sql.append(" AND productId = '" + userToCatalog.getProductId() + "'");
		sql.append(" AND productIndex = '" + userToCatalog.getProductIndex()
				+ "'");
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		PreparedStatement ps;
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
