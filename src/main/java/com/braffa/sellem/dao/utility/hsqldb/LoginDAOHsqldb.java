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

import com.braffa.sellem.dao.utility.sql.SQLQueries;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;



public enum LoginDAOHsqldb {
	instance;

	private static final Logger logger = Logger.getLogger(LoginDAOHsqldb.class);
	
	private SQLQueries sqlQueries = new SQLQueries();

	private String driver = "";
	private String jdbc = "";
	private String database = "";
	private String dbUserName = "";
	private String dbPassword = "";

	private LoginDAOHsqldb() {
		DatabaseResources.instance();
		driver = DatabaseResources.DB_DRIVER;
		jdbc = DatabaseResources.DB_JDBC;
		database = DatabaseResources.DB_DATABASE;
		dbUserName = DatabaseResources.DB_USER_NAME;
		dbPassword = DatabaseResources.DB_PASSWORD;
	}

	public String getCount() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Connection connection = sqlQueries.hsqlConnection();
		Class.forName(driver);
		PreparedStatement ps;
		ps = connection.prepareStatement(sqlQueries.loginCountSql());
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

	public Login getLogin(Login aLogin) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getLogin " + aLogin.getUserId());
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;

		StringBuffer sql = new StringBuffer(
				"SELECT USERID, PASSWORD, AUTHORITYLEVEL, CRDATE, UPDDATE FROM LOGIN WHERE USERID = ");
		sql.append("'" + aLogin.getUserId() + "'");
		ps = connection.prepareStatement(sql.toString());
		ResultSet resultSet = ps.executeQuery();
		Login login = new Login();
		login.setUserId("9999");
		while (resultSet.next()) {
			login.setUserId(resultSet.getString("USERID"));
			login.setPassword(resultSet.getString("PASSWORD"));
			login.setAuthorityLevel(resultSet.getString("AUTHORITYLEVEL"));
			Timestamp crDate = resultSet.getTimestamp("CRDATE");
			login.setCrDate( new Date (crDate.getTime()));
			Timestamp updDate = resultSet.getTimestamp("UPDDATE");
			login.setUpdDate( new Date (updDate.getTime()));
		}
		resultSet.close();
		ps.close();
		connection.close();
		return login;
	}

	public List<Login> getAllLogins() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		List<Login> lOfLogins = new ArrayList<Login>();
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer(
				"SELECT USERID, PASSWORD, AUTHORITYLEVEL, CRDATE, UPDDATE FROM LOGIN ");
		ps = connection.prepareStatement(sql.toString());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Login login = new Login();
			login.setUserId(resultSet.getString("USERID"));
			login.setPassword(resultSet.getString("PASSWORD"));
			login.setAuthorityLevel(resultSet.getString("AUTHORITYLEVEL"));
			Timestamp crDate = resultSet.getTimestamp("CRDATE");
			login.setCrDate( new Date (crDate.getTime()));
			Timestamp updDate = resultSet.getTimestamp("UPDDATE");
			login.setUpdDate( new Date (updDate.getTime()));
			lOfLogins.add(login);
		}
		resultSet.close();
		ps.close();
		connection.close();
		return lOfLogins;
	}

	public void deleteLogin(Login login) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer("DELETE FROM LOGIN WHERE userId = ");
		sql.append("'" + login.getUserId() + "'");
		System.out.println(sql);
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}

	public void createLogin(Login login) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer(
				"INSERT INTO LOGIN (userId, password, authorityLevel, CRDATE, UPDDATE) VALUES (");
		sql.append("'" + login.getUserId() + "' ,");
		sql.append("'" + login.getPassword() + "' ,");
		sql.append("'" + login.getAuthorityLevel() + "', ");
		sql.append(" CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP)");
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}

	public void updateLogin(Login login) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer("UPDATE LOGIN SET ");
		sql.append("password = '" + login.getPassword() + "' ,");
		sql.append("authorityLevel = '" + login.getAuthorityLevel() + "', ");
		sql.append("UPDDATE = CURRENT_TIMESTAMP");
		sql.append("WHERE USERID = '" + login.getUserId() + "'");
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
