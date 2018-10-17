package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backEnd.Client;
import backEnd.Zone;
import exceptions.AccessException;
import exceptions.ClientNotFoundException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;


public class ClienteDAO {
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
	

	
	static public Client getClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, ClientNotFoundException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT  * FROM clientes where clientId = " + clientId; // Aca tengo que hacer un join entre el cliente y la zona para que lo que me devuelva sea el cliente con el nombre de la zona
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			
			if(rs.next()){
				Zone zone = new Zone(rs.getString(99)); //Aca en vez del 99 tengo que tomar el numero con el nombre de zona que se me crea en el join de arriba
				Client newClient = new Client(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), zone);
				return newClient;
			}
			else{
				throw new ClientNotFoundException(); //El cliente con el id "clientId" no existe
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
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into clientes values(?,?,?,?,?)");
			stm.setString(1, c.getName());
			stm.setString(2, c.getAddress());
			stm.setString(3, c.getPhoneNumber());
			stm.setInt(4, c.getId());
			stm.setString(5, c.getZone().getName());
			stm.executeUpdate();
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}
		
		try {
			stm.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
	}
}
