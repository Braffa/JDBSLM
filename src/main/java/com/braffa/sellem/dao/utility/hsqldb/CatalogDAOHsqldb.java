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

import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;
import com.braffa.sellem.model.xml.webserviceobjects.product.Products;






public enum CatalogDAOHsqldb {
	instance;

	private static final Logger logger = Logger
			.getLogger(CatalogDAOHsqldb.class);

	private String driver = "";
	private String jdbc = "";
	private String database = "";
	private String dbUserName = "";
	private String dbPassword = "";

	private CatalogDAOHsqldb() {
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
				"SELECT id, author, imageURL, imageLargeURL, manufacturer, productgroup, ");
		sql.append("productid, productidtype, source, sourceid, title, CRDATE, UPDDATE ");
		sql.append("FROM CATALOG ");

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

	public Catalog getCatalog() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		StringBuffer sql = new StringBuffer(
				"SELECT id, author, imageURL, imageLargeURL, manufacturer, productgroup, productid, productidtype, source, sourceid, title, CRDATE, UPDDATE FROM CATALOG ");
		Catalog catalog = fetchCatalog(sql.toString());
		return catalog;
	}

	public Catalog getCatalog(String key) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(key);
		}
		StringBuffer sql = new StringBuffer(
				"SELECT id, author, imageURL, imageLargeURL, manufacturer, productgroup, productid, productidtype, source, sourceid, title, CRDATE, UPDDATE  ");
		sql.append(" FROM CATALOG where productid = '" + key + "'");
		Catalog catalog = null;
		try {
			catalog = fetchCatalog(sql.toString());
		} catch (Exception e) {
			// product not found
			e.printStackTrace();
		}
		return catalog;
	}

	public Product getCatalogBySearchUserId(String productId) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Product product = null;
		StringBuffer sql = new StringBuffer(
				"SELECT id, author, imageURL, imageLargeURL, manufacturer, productgroup, productid, productidtype, source, sourceid, title, CRDATE, UPDDATE  ");
		sql.append("FROM CATALOG ");
		sql.append("WHERE productid = '" + productId + "'");
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		ps = connection.prepareStatement(sql.toString());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			product = setUpProduct(resultSet);
		}
		resultSet.close();
		ps.close();
		connection.close();
		return product;
	}

	public Catalog getCatalogBySearch(String searchField, String searchValue)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		StringBuffer sql = new StringBuffer(
				"SELECT id, author, imageURL, imageLargeURL, manufacturer, productgroup, productid, productidtype, source, sourceid, title, CRDATE, UPDDATE  ");
		sql.append(" FROM CATALOG where ");

		if (searchField.equals("author")) {
			sql.append("LOWER(author) like '%" + searchValue.toLowerCase()
					+ "%'");
		} else if (searchField.equals("title")) {
			sql.append("LOWER(title) like '%" + searchValue.toLowerCase()
					+ "%'");
		} else if (searchField.equals("productid")) {
			sql.append("LOWER(productid) like '%" + searchValue.toLowerCase()
					+ "%'");
		} else if (searchField.equals("manufacturer")) {
			sql.append("LOWER(manufacturer) like '%"
					+ searchValue.toLowerCase() + "%'");
		}
		Catalog catalog = fetchCatalog(sql.toString());
		return catalog;
	}

	private Catalog fetchCatalog(String sql) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		List<Product> lOfProducts = new ArrayList<Product>();
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		PreparedStatement ps;
		ps = connection.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Product product = setUpProduct(resultSet);
			lOfProducts.add(product);
		}
		Products products = new Products(lOfProducts);
		Catalog catalog = new Catalog(products);
		resultSet.close();
		ps.close();
		connection.close();
		return catalog;
	}

	private Product setUpProduct(ResultSet resultSet) throws Exception {
		Product product = new Product();
		product.setAuthor(resultSet.getString("author"));
		product.setImageURL(resultSet.getString("imageURL"));
		product.setImageLargeURL(resultSet.getString("imageLargeURL"));
		product.setManufacturer(resultSet.getString("manufacturer"));
		product.setProductgroup(resultSet.getString("productgroup"));
		product.setProductid(resultSet.getString("productid"));
		product.setProductidtype(resultSet.getString("productidtype"));
		product.setSource(resultSet.getString("source"));
		product.setSourceid(resultSet.getString("sourceid"));
		product.setTitle(resultSet.getString("title"));
		Timestamp crDate = resultSet.getTimestamp("CRDATE");
		product.setCrDate( new Date (crDate.getTime()));
		Timestamp updDate = resultSet.getTimestamp("UPDDATE");
		product.setUpdDate( new Date (updDate.getTime()));
		return product;
	}

	public void createProduct(Product product) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		StringBuffer sql = new StringBuffer(
				"INSERT INTO CATALOG (author, imageURL, imageLargeURL, manufacturer, productgroup, "
						+ "productid, productidtype, source, sourceid, title, CRDATE, UPDDATE) VALUES (");
		sql.append("'" + product.getAuthor() + "', ");
		sql.append("'" + product.getImageURL() + "', ");
		sql.append("'" + product.getImageLargeURL() + "', ");
		sql.append("'" + product.getManufacturer() + "', ");
		sql.append("'" + product.getProductgroup() + "', ");
		sql.append("'" + product.getProductid() + "', ");
		sql.append("'" + product.getProductidtype() + "', ");
		sql.append("'" + product.getSource() + "', ");
		sql.append("'" + product.getSourceid() + "', ");
		sql.append("'" + product.getTitle() + "', ");
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

	public void deleteProduct(Product product) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		StringBuffer sql = new StringBuffer("DELETE FROM CATALOG WHERE ");
		sql.append(" productid = '" + product.getProductid() + "'");
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
		}
		PreparedStatement ps;
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}

	public void updateProduct(Product product) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbc + database,
				dbUserName, dbPassword);
		StringBuffer sql = new StringBuffer("UPDATE CATALOG SET ");
		sql.append("AUTHOR = '" + product.getAuthor() + "', ");
		sql.append("imageURL = '" + product.getImageURL() + "', ");
		sql.append("imageLargeURL = '" + product.getImageLargeURL() + "', ");
		sql.append("manufacturer = '" + product.getManufacturer() + "', ");
		sql.append("productgroup = '" + product.getProductgroup() + "', ");
		sql.append("productidtype = '" + product.getProductidtype() + "', ");
		sql.append("source = '" + product.getSource() + "', ");
		sql.append("sourceid = '" + product.getSourceid() + "', ");
		sql.append("title = '" + product.getTitle() + "', ");
		sql.append("UPDDATE = CURRENT_TIMESTAMP ");
		sql.append(" where productid = '" + product.getProductid() + "'");
		System.out.println(sql);
		PreparedStatement ps;
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
