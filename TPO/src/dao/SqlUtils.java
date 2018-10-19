package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.AccessException;
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
	
	public static int lastId(String table, String column) throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT MAX(" + column + ") AS LastId FROM " + table + ";";
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				return rs.getInt(1);
			}
			else{
				return 0;
			}
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
		
	}

	
}
