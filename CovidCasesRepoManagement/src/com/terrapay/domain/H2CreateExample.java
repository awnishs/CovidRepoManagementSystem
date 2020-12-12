package com.terrapay.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.terrapay.h2Util.H2JDBCUtils;

public class H2CreateExample {

	private static final String createTableSQL = "create table IF NOT EXISTS covid_repo (\r\n"
			+ "  Aadhaar_Id varchar(20) primary key,\r\n" + "  Name varchar(20),\r\n" + "  State varchar(20),\r\n"
			+ "  City varchar(20),\r\n" + "  PinCode varchar(20),\r\n" + "  Status varchar(20)\r\n" + "  );";

	public void createTable() throws SQLException {

		System.out.println(createTableSQL);

		// Step 1: Establishing a Connection

		try (Connection connection = H2JDBCUtils.getConnection();
				
				// Step 2:Create a statement using connection object
				Statement statement = connection.createStatement();) {

			// Step 3: Execute the query or update query
			statement.execute(createTableSQL);

		} catch (SQLException e) {
			// print SQL exception information
			H2JDBCUtils.printSQLException(e);
		}
	}
}