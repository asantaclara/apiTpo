package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.Zone;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;


public class ClientDAO {

	
	public List<Client> getAllClients() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM Clients JOIN Zones ON Clients.ZoneId = Zones.ZoneId WHERE Active = 1"; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<Client> returnList = new LinkedList<>();
				Client newClient = null;
				
				while(rs.next()){				
					newClient = new Client(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), new Zone(rs.getString(10)));
					newClient.setId(rs.getInt(1));
					returnList.add(newClient);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	public Client getClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sqlQuery = "SELECT * FROM Clients JOIN Zones ON Clients.ZoneId = Zones.ZoneId where clientId = " + clientId; 
			
			rs = SqlUtils.executeQuery(stmt, con, sqlQuery);
			
			try {
				if(rs.next()){
					if(rs.getByte(8) == 1) { // If client is Active				
						Client newClient = new Client(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), new ZoneDAO().getZone(rs.getString(10)));
						//								CUIT			Name				Address			PhoneNumber		Mail									Zone
						newClient.setId(rs.getInt(1));
						return newClient;
					} else {
						throw new InvalidClientException("The client is not active");
					}
				}
				else{
					throw new InvalidClientException("Client not found");
				}
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public void save(Client client) throws ConnectionException, AccessException, InvalidClientException{
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			/*
			 * Si el cliente no tiene el clientId cargado quiere decir que es un cliente nuevo por lo tanto busco el ultimo
			 * clientId que hay en la base de datos, le sumo uno y se lo asigno a este cliente nuevo para despues guardarlo.
			 */
			if(client.getId() != 0) {
				throw new InvalidClientException("Client already in data base");
			}
			
			client.setId(SqlUtils.lastId("Clients", "ClientId") + 1); 
			try {
				prepStm = con.prepareStatement("insert into Clients values(?,?,?,?,?,?,?,?)");
				prepStm.setInt(1, client.getId());
				prepStm.setString(2, client.getName());
				prepStm.setString(3, client.getCuit());
				prepStm.setString(4, client.getAddress());
				prepStm.setString(5, client.getPhoneNumber());
				prepStm.setString(6, client.getEmail());
				prepStm.setInt(7, client.getZone().getId());
				prepStm.setByte(8, (byte) ((client.isActive() == true) ? 1 : 0));
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm.execute();
			} catch (SQLException e) {
				if(e.getErrorCode() == 2627) {
					throw new InvalidClientException("Existing CUIT");
				}
				throw new AccessException("Save error");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	public void modify(Client client) throws ConnectionException, AccessException, InvalidClientException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(client.getId() == 0) {
				throw new InvalidClientException("Client not in data base");
			}
			
			try {
				prepStm = con.prepareStatement("UPDATE Clients SET Name = ?, Cuit = ?, Address = ?, Phone = ?, Mail = ?, ZoneId = ?, Active = ? WHERE ClientId = " + client.getId());
				prepStm.setString(1, client.getName());
				prepStm.setString(2, client.getCuit());
				prepStm.setString(3, client.getAddress());
				prepStm.setString(4, client.getPhoneNumber());
				prepStm.setString(5, client.getEmail());
				prepStm.setInt(6, client.getZone().getId());
				prepStm.setByte(7, (byte) ((client.isActive() == true) ? 1 : 0));
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}
			try {
				prepStm.execute();
			} catch (SQLException e) {
				if(e.getErrorCode() == 2627) {
					throw new InvalidClientException("Existing cuit");
				}else {					
					throw new AccessException("Save error");
				}
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
}
