package com.braffa.sellem.dao.utility.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.braffa.sellem.dao.utility.hsqldb.CreateDatabase;
import com.braffa.sellem.dao.utility.sql.SQLQueries;

public class MySQLSetUp {

	private static final Logger logger = Logger.getLogger(CreateDatabase.class);
	
	private SQLQueries sqlQueries = new SQLQueries();

	public void setUpLogin() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("setUpLogin");
		}

		Connection connection = sqlQueries.mysqlConnection ();
		
		sqlQueries.dropTable(connection, "DROP TABLE LOGIN");
		PreparedStatement ps;

		ps = connection.prepareStatement(sqlQueries.createMysqlLoginTable());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertLogin1());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertLogin2());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertLogin3());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertLogin4());
		ps.executeUpdate();
		
		ps = connection.prepareStatement("commit");
		ps.executeUpdate();

		ps.close();
		connection.close();
	}
	
	public void setUpProduct() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("setUpProduct");
		}
		Connection connection = sqlQueries.mysqlConnection ();
		sqlQueries.dropTable(connection, "DROP TABLE PRODUCT");
		PreparedStatement ps;
		try {

		ps = connection.prepareStatement(sqlQueries.mysqlProductTable ());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertProduct1());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertProduct2() );
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertProduct3());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertProduct4());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertProduct5());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertProduct6());
		ps.executeUpdate();
		
		ps.close();
		connection.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setUpRegisteredUser() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("setUpRegisteredUser");
		}
		Connection connection = sqlQueries.mysqlConnection ();
		sqlQueries.dropTable(connection, "DROP TABLE REGISTEREDUSER");
		PreparedStatement ps;

		ps = connection.prepareStatement(sqlQueries.mysqlRegisteredUserTable());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser1());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser2());
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser3() );
		ps.executeUpdate();

		ps = connection.prepareStatement(sqlQueries.insertRegisteredUser4());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
	
	public void setUpUserToProductTable() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("setUpUserToProductTable");
		}
		Connection connection = sqlQueries.mysqlConnection ();
		sqlQueries.dropTable(connection, "DROP TABLE USERTOPRODUCT");
		PreparedStatement ps;
		
		ps = connection.prepareStatement(sqlQueries.mysqlUserToProductTable());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable1());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable2());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable3());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable4());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable5());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable6());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable7());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable8());
		ps.executeUpdate();
		
		ps = connection.prepareStatement(sqlQueries.insertUserToProductTable9());
		ps.executeUpdate();
		
		ps = connection.prepareStatement("select * from USERTOPRODUCT");
		ps.execute();
		
		ps.close();
		connection.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MySQLSetUp mySQLSetUp = new MySQLSetUp();
		mySQLSetUp.sqlQueries = new SQLQueries();
		try {
			mySQLSetUp.setUpLogin();
			mySQLSetUp.setUpProduct();
			mySQLSetUp.setUpRegisteredUser();
			mySQLSetUp.setUpUserToProductTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
