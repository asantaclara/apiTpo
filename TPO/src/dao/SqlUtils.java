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
	
	public static Statement createStatement(Connection con) throws AccessException, ConnectionException {
		
		try {
			return con.createStatement();
		} catch (SQLException e1) {
			closeConnection(con);
			e1.printStackTrace();
			throw new AccessException("Access error");
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

	public static void closeConnection(Connection con) throws ConnectionException {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectionException("Close error");
		}
	}
	
	public static ResultSet executeQuery(Statement stmt, Connection con, String sql) throws ConnectionException, AccessException {		
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e1) {
			closeConnection(con);
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
	}
	
}
