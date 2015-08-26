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


import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Register;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUsers;


public enum RegisteredUsersDAOHsqldb {
	instance;

	private static final Logger logger = Logger
			.getLogger(RegisteredUsersDAOHsqldb.class);

	private String driver = "";
	private String jdbc = "";
	private String database = "";
	private String dbUserName = "";
	private String dbPassword = "";

	private RegisteredUsersDAOHsqldb() {
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
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;

		StringBuffer sql = new StringBuffer(
				"SELECT id, userId, email, firstname, lastname, telephone, crdate, upddate FROM REGISTER ");
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

	public RegisteredUser getRegisteredUser(String userId) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		RegisteredUser registeredUser = new RegisteredUser();
		StringBuffer sql = new StringBuffer(
				"SELECT R.id, R.userId, R.email, R.firstname, R.lastname, R.telephone, R.CRDATE, R.UPDDATE, L.ID, L.userid, L.password, L.authorityLevel");
		sql.append(" FROM REGISTER R, LOGIN L where R.userId = '" + userId
				+ "' and L.userid = '" + userId + "'");
		ps = connection.prepareStatement(sql.toString());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			registeredUser = setUpRegisteredUser (resultSet); 
		}
		resultSet.close();
		ps.close();
		connection.close();
		return registeredUser;
	}

	public Register getAllRegisteredUsers() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		List<RegisteredUser> lOfRegisteredUsers = new ArrayList<RegisteredUser>();
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;

		StringBuffer sql = new StringBuffer(
				"SELECT R.id, R.userId, R.email, R.firstname, R.lastname, R.telephone, R.CRDATE, R.UPDDATE, L.ID, L.userid, L.password, L.authorityLevel ");
		sql.append("FROM REGISTER R, LOGIN L ");
		sql.append("where r.userId = l.userId ");
		ps = connection.prepareStatement(sql.toString());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			RegisteredUser registeredUser = setUpRegisteredUser (resultSet); 
			lOfRegisteredUsers.add(registeredUser);
		}
		RegisteredUsers registeredUsers = new RegisteredUsers(
				lOfRegisteredUsers);
		Register register = new Register();
		register.setRegisteredUsers(registeredUsers);
		resultSet.close();
		ps.close();
		connection.close();
		return register;
	}
	
	private RegisteredUser setUpRegisteredUser (ResultSet resultSet) throws Exception {
		Login login = new Login(resultSet.getString("authorityLevel"),
				resultSet.getString("password"),
				resultSet.getString("userId"));
		
		RegisteredUser registeredUser = new RegisteredUser(
				resultSet.getString("email"),
				resultSet.getString("firstname"),
				resultSet.getString("lastname"),
				resultSet.getString("telephone"), login);
		Timestamp crDate = resultSet.getTimestamp("CRDATE");
		registeredUser.setCrDate( new Date (crDate.getTime()));
		Timestamp updDate = resultSet.getTimestamp("UPDDATE");
		registeredUser.setUpdDate( new Date (updDate.getTime()));
		return registeredUser;
	}

	public void deleteRegisteredUser(RegisteredUser registeredUser)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer("DELETE FROM LOGIN WHERE userId = ");
		sql.append("'" + registeredUser.getLogin().getUserId() + "'");
		System.out.println(sql);
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();

		sql = new StringBuffer("DELETE FROM REGISTER WHERE userId = ");
		sql.append("'" + registeredUser.getLogin().getUserId() + "'");
		System.out.println(sql);
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}

	public void createRegisteredUser(RegisteredUser registeredUser)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer(
				"INSERT INTO LOGIN (userId, password, authorityLevel, CRDATE, UPDDATE) VALUES (");
		sql.append("'" + registeredUser.getLogin().getUserId() + "' ,");
		sql.append("'" + registeredUser.getLogin().getPassword() + "' ,");
		sql.append("'" + registeredUser.getLogin().getAuthorityLevel() + "', ");
		sql.append(" CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP ");
		sql.append(")");
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();

		sql = new StringBuffer(
				"INSERT INTO REGISTER (userId, email, firstname, lastname, telephone, CRDATE, UPDDATE) VALUES (");
		sql.append("'" + registeredUser.getLogin().getUserId() + "', ");
		sql.append("'" + registeredUser.getEmail() + "', ");
		sql.append("'" + registeredUser.getFirstname() + "', ");
		sql.append("'" + registeredUser.getLastname() + "', ");
		sql.append("'" + registeredUser.getTelephone() + "', ");
		sql.append(" CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP ");
		sql.append(")");
		System.out.println(sql);
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}

	public void updateRegisteredUser(RegisteredUser registeredUser)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		StringBuffer sql = new StringBuffer("UPDATE LOGIN SET ");
		sql.append("password = '" + registeredUser.getLogin().getPassword()
				+ "' ,");
		sql.append("authorityLevel = '"
				+ registeredUser.getLogin().getAuthorityLevel() + "', ");
		sql.append("UPDDATE = CURRENT_TIMESTAMP");
		sql.append("WHERE USERID = '" + registeredUser.getLogin().getUserId()
				+ "'");
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();

		sql = new StringBuffer("UPDATE REGISTER SET ");
		sql.append("email = '" + registeredUser.getEmail() + "', ");
		sql.append("firstname = '" + registeredUser.getFirstname() + "', ");
		sql.append("lastname = '" + registeredUser.getLastname() + "', ");
		sql.append("telephone = '" + registeredUser.getTelephone() + "', ");
		sql.append("UPDDATE = CURRENT_TIMESTAMP");
		sql.append("WHERE USERID = '" + registeredUser.getLogin().getUserId()
				+ "'");
		System.out.println(sql);
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
