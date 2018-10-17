package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Exceptions.AccessException;
import Exceptions.ConnectException;
import Exceptions.TransitionException;
import Exceptions.UserException;
import Models.User;

public class UserDAO {
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws UserException
	 */
	static public User getUserThroughId(int userId) throws ConnectException, AccessException, UserException{
		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {    
			con = ConnectionFactory.getInstance().getConection();
		}
		catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw new ConnectException("No esta disponible el acceso al Servidor");
		}
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Error de acceso");
		}
		
		String SQL = "SELECT  * FROM users where userId = " + userId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Error de consulta");
		}
		try {
			
			if(rs.next()){
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				return user;
			}
			else{
				throw new TransitionException("el usuario con id: " + userId + " no existe");
			}
		} catch (SQLException e) {
			throw new ConnectException("No es posible acceder a los datos");
		}
	}
	/**
	 * Dado un usuario lo almacena en la BD
	 * @param Transition t
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveUser(User u) throws ConnectException, AccessException{
		Connection con;
		
		try {
			con = ConnectionFactory.getInstance().getConection();
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectException("No esta disponible el acceso al Servidor");
		} 
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into users values(?,?,?,?)");
			stm.setInt(1, u.getUserId());
			stm.setString(2, u.getName();
			stm.setString(3, u.getPrincipalRole());
			stm.setDate(4, u.getSecondaryRole());
			stm.executeUpdate();
			
		} catch (SQLException e) {
			throw new AccessException("Error de acceso");
		}
		
		try {
			stm.execute();
		} catch (SQLException e) {
			throw new AccessException("No se pudo guardar");
		}
	}
}
