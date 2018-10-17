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
import Exceptions.UserBoardException;
import Models.UserBoard;

public class UserBoardDAO {
	/**
	 * 
	 * @param boardId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws UserBoardException
	 */
	static public UserBoard getUserBoardThroughId(int boardId) throws ConnectException, AccessException, UserBoardException{
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
		
		String SQL = "SELECT  * FROM userBoards where boardId = " + boardId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Error de consulta");
		}
		try {
			
			if(rs.next()){
				UserBoard userBoard = new UserBoard(rs.getInt(1), rs.getString(2), rs.getString(3));
				return userBoard;
			}
			else{
				throw new TransitionException("el userBoard con id: " + boardId + " no existe");
			}
		} catch (SQLException e) {
			throw new ConnectException("No es posible acceder a los datos");
		}
	}
	
	/**
	 * 
	 * @param u
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveUserBoard(UserBoard u) throws ConnectException, AccessException{
		Connection con;
		
		try {
			con = ConnectionFactory.getInstance().getConection();
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectException("No esta disponible el acceso al Servidor");
		} 
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into userBoards values(?,?,?,?)");
			stm.setInt(1, u.getBoardId());
			stm.setString(2, u.getAllClaims());
			stm.setString(3, u.getRole());
			stm.setString(4, u.getBoardsToShow());
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
