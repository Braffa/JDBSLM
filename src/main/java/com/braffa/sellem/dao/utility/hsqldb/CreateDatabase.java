package com.braffa.sellem.dao.utility.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.braffa.sellem.dao.utility.sql.SQLQueries;

public enum CreateDatabase {
	instance;
	
	private static final Logger logger = Logger
			.getLogger(CreateDatabase.class);
	
	public void setUpLogin() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("setUpLogin");
		}
		
		SQLQueries sqlQueries = new SQLQueries();
		Connection connection = sqlQueries.hsqlConnection();
		sqlQueries.dropTable(connection, "drop TABLE LOGIN");
		PreparedStatement ps;
		ps = connection.prepareStatement(sqlQueries.hsqlLoginTable());
		ps.executeUpdate();
		sqlQueries.hsqlCached (connection, "LOGIN");

		ps = connection.prepareStatement(sqlQueries.insertLogin1());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertLogin2());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertLogin3());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertLogin4());
		ps.executeUpdate();

		ps.close();
		connection.close();
	}

	public void setUpCatalog() throws Exception {
		SQLQueries sqlQueries = new SQLQueries();
		Connection connection = sqlQueries.hsqlConnection();
		sqlQueries.dropTable(connection, "drop TABLE CATALOG");
		PreparedStatement ps;
		ps = connection.prepareStatement(sqlQueries.hsqlCatalogTable());
		ps.executeUpdate();
		sqlQueries.hsqlCached (connection, "CATALOG");
		
		ps = connection.prepareStatement(sqlQueries.insertCaratlog1());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertCaratlog2());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertCaratlog3());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertCaratlog4());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertCaratlog5());
		ps.executeUpdate();

		ps.close();
		connection.close();
	}

	public void setUpRegister() throws Exception {
		SQLQueries sqlQueries = new SQLQueries();
		Connection connection = sqlQueries.hsqlConnection();
		sqlQueries.dropTable(connection, "drop TABLE REGISTEREDUSER");
		PreparedStatement ps;
		ps = connection.prepareStatement(sqlQueries.hsqlRegisterTable());
		ps.executeUpdate();
		sqlQueries.hsqlCached (connection, "REGISTEREDUSER");
		
		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser1());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser2());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser3());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser4());
		ps.executeUpdate();

		ps.close();
		connection.close();
	}

	public void setUpUserToCatalog() throws Exception {
		SQLQueries sqlQueries = new SQLQueries();
		Connection connection = sqlQueries.hsqlConnection();
		sqlQueries.dropTable(connection, "drop TABLE USER_TO_CATALOG");
		PreparedStatement ps;
		ps = connection.prepareStatement(sqlQueries.hsqlUserToCatalogTable());
		ps.executeUpdate();
		sqlQueries.hsqlCached (connection, "USER_TO_CATALOG");
		
		ps = connection.prepareStatement(sqlQueries.insertUserToCatalogTable1());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertUserToCatalogTable2());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertUserToCatalogTable3());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertUserToCatalogTable4());
		ps.executeUpdate();
		ps = connection.prepareStatement(sqlQueries.insertUserToCatalogTable5());
		ps.executeUpdate();

		ps.close();
		connection.close();
	}

}
