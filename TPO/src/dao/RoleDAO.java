package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Exceptions.AccessException;
import Exceptions.ConnectException;
import Exceptions.RoleException;
import Exceptions.TransitionException;
import Exceptions.UserException;
import Models.Role;
import Models.User;

public class RoleDAO {
	/**
	 * 
	 * @param roleId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws RoleException
	 */
	static public Role getRoleThroughId(int roleId) throws ConnectException, AccessException, RoleException{
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
		
		String SQL = "SELECT  * FROM roles where roleId = " + roleId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Error de consulta");
		}
		try {
			
			if(rs.next()){
				Role role = new Role(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				return role;
			}
			else{
				throw new TransitionException("el rol con id: " + roleId + " no existe");
			}
		} catch (SQLException e) {
			throw new ConnectException("No es posible acceder a los datos");
		}
	}
	/**
	 * 
	 * @param r
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveRole(Role r) throws ConnectException, AccessException{
		Connection con;
		
		try {
			con = ConnectionFactory.getInstance().getConection();
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectException("No esta disponible el acceso al Servidor");
		} 
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into roles values(?,?,?,?)");
			stm.setInt(1, r.getRoleId());
			stm.setString(2, r.getDescription();
			stm.setString(3, r.getUsers());
			stm.setDate(4, r.getBoardsToShow());
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
