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


public class ClientDAO {
	/**
	 * Dado un id de cliente: busca en la BD, y en caso de encontrarlo devuelve el mismo
	 * @param clientId
	 * @return Client
	 * @throws ConnectionException 
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws InvalidClientException 
	 * @throws ClientNotFoundException 
	 * @throws ClientException
	 */
	
	static public List<Client> getAllClients() throws ConnectionException, AccessException, InvalidClientException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Clients JOIN Zones ON Clients.ZoneId = Zones.ZoneId"; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {
			List<Client> returnList = new LinkedList<>();
			Client newClient = null;
			
			while(rs.next()){
				if(rs.getByte(8) == 1) {					
					newClient = new Client(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), new Zone(rs.getString(10)));
					newClient.setId(rs.getInt(1));
					returnList.add(newClient);
				} else {
					throw new InvalidClientException("The client is not active");
				}
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
		
		
	}
	
	static public Client getClient(int clientId) throws ConnectionException, AccessException, InvalidClientException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Clients JOIN Zones ON Clients.ZoneId = Zones.ZoneId where clientId = " + clientId; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				if(rs.getByte(8) == 1) {					
					Client newClient = new Client(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), new Zone(rs.getString(10)));
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
	}
	/**
	 * Dado un cliente lo almacena en la BD
	 * @param Client c
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveClient(Client c) throws ConnectionException, AccessException{
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
		
		try {
			
			prepStm = con.prepareStatement("insert into clientes values(?,?,?,?,?)");
			prepStm.setString(1, c.getName());
			prepStm.setString(2, c.getAddress());
			prepStm.setString(3, c.getPhoneNumber());
			prepStm.setInt(4, c.getId());
			prepStm.setString(5, c.getZone().getName());
			prepStm.executeUpdate();
			
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
