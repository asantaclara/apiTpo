package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static ConnectionFactory instance;
	String connectionUrl = "jdbc:sqlserver://127.0.0.1;databaseName=AI_4034m_01;user=eze;password=eze";
	
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
