package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static ConnectionFactory instance;
	String connectionUrl = "jdbc:sqlserver://172.16.123.128;databaseName=TestDb;user=sa;password=34390221";
	
	private ConnectionFactory() throws ClassNotFoundException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	}
	
	public static ConnectionFactory getInstance() throws ClassNotFoundException{
		if(instance == null)
			instance = new ConnectionFactory();
		return instance;
	}
	
	public Connection getConection() throws SQLException{
		return DriverManager.getConnection(connectionUrl);
	}
	
}
