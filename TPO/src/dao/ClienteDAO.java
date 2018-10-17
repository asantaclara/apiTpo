package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backEnd.Client;
import exceptions.AccessException;
import exceptions.ClientNotFoundException;
import exceptions.ConnectionException;


public class ClienteDAO {
	/**
	 * Dado un id de cliente: busca en la BD, y en caso de encontrarlo devuelve el mismo
	 * @param clientId
	 * @return Client
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws ClientException
	 */
	static public Client getClient(int clientId) throws ConnectionException, AccessException {
		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {    
			con = ConnectionFactory.getInstance().getConection();
		}
		catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw new ConnectionException("Server not available");
		}
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT  * FROM clientes where clientId = " + clientId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			
			if(rs.next()){
				//GetString(5) deberia de devolver una ZONA: debo crear la zona una vez que recibo?
				Client client = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
				return client;
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
		Connection con;
		
		try {
			con = ConnectionFactory.getInstance().getConection();
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectionException(); //No esta disponible el acceso al servidor
		} 
		
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
			throw new AccessException(); //Error de acceso
		}
		
		try {
			stm.execute();
		} catch (SQLException e) {
			throw new AccessException(); //No se pudo guardar
		}
	}
}
