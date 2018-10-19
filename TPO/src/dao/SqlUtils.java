package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;

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
	
	public static int lastId(String table, String column) throws InvalidClientException, ConnectionException, AccessException {
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

	public static void fixZone(String zoneName) throws AccessException, ConnectionException, InvalidClientException {
		Connection con = SqlUtils.getConnection();
		Statement stmt = null;  
		ResultSet rs = null;
		PreparedStatement prepStm;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		String SQL = "SELECT * FROM Zones WHERE Name = '" + zoneName + "';";
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			if(!rs.next()){
				//xdfghjklkjhgfdsdfghjklkjhgfdsadfghjkljhgfdsdfghjkjhgfdsdfghjkljhgfds
				try {
					
					prepStm = con.prepareStatement("insert into Zones values(?,?)");
					prepStm.setInt(1, lastId("Zones", "ZoneId")+1);
					prepStm.setString(2, zoneName);
					prepStm.executeUpdate();
					
				} catch (SQLException e) {
					throw new AccessException("Access error");
				}
				try {
					prepStm.execute();
				} catch (SQLException e) {
					throw new AccessException("Save error");
				}
				//fdghjkljhgfdfghjkljhgfcfvghjkjhgffghjkjhgfxcvghjkjhgfdxcvghjkjhgfcvbnkjhg
			}
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}
	
}
