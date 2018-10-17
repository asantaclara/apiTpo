package dao;

import java.sql.Connection;
import java.sql.SQLException;

import exceptions.ConnectionException;

public class SqlUtils {

	public static Connection getConnection() throws ConnectionException {
		try {    
			return ConnectionFactory.getInstance().getConection();
		}
		catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw new ConnectionException("Server not available");
		}
	}
}
