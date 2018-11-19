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
import exceptions.InvalidZoneException;

public class ZoneDAO {
	
	public List<Zone> getAllZones() throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM Zones"; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public Zone getZone(int zoneId) throws ConnectionException, AccessException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			
			String sql = "SELECT * FROM Zones where ZoneId = " + zoneId; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){					
					Zone newZone = new Zone(rs.getString(2));
					newZone.setId(rs.getInt(1));
					return newZone;
				}
				else{
					throw new InvalidZoneException("Zone not found");
				}
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public void save(Zone zone) throws ConnectionException, AccessException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();
		try {
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public Zone getZone(String zoneName) throws AccessException, ConnectionException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			
			String sql = "SELECT * FROM Zones where Name = '" + zoneName + "'"; 
			
			rs =  SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){					
					Zone newZone = new Zone(rs.getString(2));
					newZone.setId(rs.getInt(1));
					return newZone;
				}
				else{
					throw new InvalidZoneException("Zone not found");
				}
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public void modify(Zone zone) throws AccessException, InvalidZoneException, ConnectionException{
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(zone.getId() == 0) {
				throw new InvalidZoneException("Zone not in data base");
			}
			
			try {
				prepStm = con.prepareStatement("UPDATE Zones SET Name = ? WHERE ZoneId = " + zone.getId());
				prepStm.setString(1, zone.getName());
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}
			try {
				prepStm.execute();
			} catch (SQLException e) {
				throw new AccessException("Save error");
			}
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
}
