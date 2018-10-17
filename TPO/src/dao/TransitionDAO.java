package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Exceptions.AccessException;
import Exceptions.ConnectException;
import Exceptions.TransitionException;
import Models.Transition;


public class TransitionDAO {
	
	/**
	 * 
	 * @param claimId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws TransitionException
	 */
	static public Transition getTransitionThroughId(int claimId) throws ConnectException, AccessException, TransitionException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Error de acceso");
		}
		
		String SQL = "SELECT  * FROM transitions where claimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Error de consulta");
		}
		try {
			
			if(rs.next()){
				Transition transition = new Transition(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),,rs.getString(6));
				return transition;
			}
			else{
				throw new TransitionException("La transicion con id: " + claimId + " no existe");
			}
		} catch (SQLException e) {
			throw new ConnectException("No es posible acceder a los datos");
		}
	}
	/**
	 * Dado un transicion lo almacena en la BD
	 * @param Transition t
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveTransition(Transition t) throws ConnectException, AccessException{
		Connection con = SqlUtils.getConnection();

		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into transitions values(?,?,?,?,?,?)");
			stm.setString(1, t.getClaimId());
			stm.setString(2, t.getPreviousState());
			stm.setString(3, t.getNewState());
			stm.setDate(4, t.getDate());
			stm.setString(5, t.getDescription());
			stm.setString(6, t.getResponsable());
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
