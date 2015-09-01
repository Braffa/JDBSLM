package com.braffa.sellem.dao.utility.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.braffa.sellem.dao.utility.hsqldb.LoginDAOHsqldb;
import com.braffa.sellem.dao.utility.sql.SQLQueries;

public class LoginDAOMysql {
	
	private static final Logger logger = Logger.getLogger(LoginDAOMysql.class);
	
	private SQLQueries sqlQueries = new SQLQueries();
	
	public String getCount() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Connection connection = sqlQueries.mysqlConnection ();
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		LoginDAOMysql loginDAOMysql = new LoginDAOMysql();
		System.out.println(loginDAOMysql.getCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
