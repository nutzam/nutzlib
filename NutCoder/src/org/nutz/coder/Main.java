package org.nutz.coder;

import java.sql.Connection;
import java.sql.DriverManager;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args)  throws Throwable {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
		                                         "jdbc:mysql://localhost:3306/mysql", "root", "123456");
		new Coder(connection).loadDatabaseInfo();
		connection.close();
		System.out.println("Done");
	}

}
