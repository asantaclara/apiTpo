package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.Zone;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;

public class ZoneDAO {

	public static int fixZone(String zoneName) throws AccessException, ConnectionException {
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
	
	static public List<Zone> getAllZones() throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Zones"; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {
			List<Zone> returnList = new LinkedList<>();
			Zone newZone = null;
			
			while(rs.next()){						
				newZone = new Zone(rs.getString(2));
				newZone.setId(rs.getInt(1));
				returnList.add(newZone);
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	static public Zone getZone(int zoneId) throws InvalidClientException, ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Zones where ZoneId = " + zoneId; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){					
				Zone newZone = new Zone(rs.getString(2));
				newZone.setId(rs.getInt(1));
				return newZone;
			}
			else{
				throw new InvalidClientException("Zone not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	static public void save(Zone zone) throws ConnectionException, AccessException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
	
		/*
		 * Si el cliente no tiene el clientId cargado quiere decir que es un cliente nuevo por lo tanto busco el ultimo
		 * clientId que hay en la base de datos, le sumo uno y se lo asigno a este cliente nuevo para despues guardarlo.
		 */
		if(zone.getId() != 0) {
			throw new InvalidZoneException("Zone already in data base");
		}

		zone.setId(SqlUtils.lastId("Zones", "ZoneId") + 1); 
		try {
			prepStm = con.prepareStatement("insert into Zones values(?,?)");
			prepStm.setInt(1, zone.getId());
			prepStm.setString(2, zone.getName());			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
	}
}
