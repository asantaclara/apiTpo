package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;

public class ZoneDAO {

	public static int fixZone(String zoneName) throws AccessException, ConnectionException, InvalidClientException {
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
			int zoneId;
			if(!rs.next()){
				try {
					zoneId = SqlUtils.lastId("Zones", "ZoneId")+1;
					prepStm = con.prepareStatement("insert into Zones values(?,?)");
					prepStm.setInt(1, zoneId);
					prepStm.setString(2, zoneName);
					prepStm.executeUpdate();
				} catch (SQLException e) {
					throw new AccessException("Save error");
				}
			}else {
				zoneId = rs.getInt(1);
			}
			return zoneId;
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}
	
}
