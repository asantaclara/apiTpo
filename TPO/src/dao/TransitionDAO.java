package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.State;
import backEnd.Transition;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;

public class TransitionDAO {

	public static void save(Transition transition) throws ConnectionException, InvalidTransitionException, AccessException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
	
		if(transition.getId() != 0) {
			throw new InvalidTransitionException("Transition already in data base");
		}
		
		transition.setId(SqlUtils.lastId("Transitions", "TransitionId") + 1); 
		try {
			prepStm = con.prepareStatement("insert into Transitions values(?,?,?,?,?,?,?)");
			prepStm.setInt(1, transition.getId());
			prepStm.setInt(2, transition.getClaimId());
			prepStm.setString(3, transition.getPreviousState().name());
			prepStm.setString(4, transition.getNewState().name());
			prepStm.setDate(5, new java.sql.Date(transition.getDate().getTime()));
			prepStm.setString(6, transition.getDescription());
			prepStm.setInt(7, transition.getResponsable().getId());
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
	}

	public static List<Transition> getAllTransitionOfClaim(int claimId) throws ConnectionException, AccessException, InvalidUserException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Transitions WHERE ClaimId = " + claimId; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {
			List<Transition> returnList = new LinkedList<>();
			Transition newTransition = null;
			
			while(rs.next()){					
				newTransition = new Transition(rs.getInt(2), State.valueOf(rs.getString(3)), State.valueOf(rs.getString(4)), 
													new Date(rs.getDate(5).getTime()), rs.getString(6), UserDAO.getUser(rs.getInt(7)));	
				newTransition.setId(rs.getInt(1));
				returnList.add(newTransition);
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	public static Transition getTransitionById(int transitionId) throws AccessException, InvalidUserException, ConnectionException, InvalidTransitionException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Transitions WHERE TransitionId = " + transitionId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				Transition newTransition = new Transition(rs.getInt(2), State.valueOf(rs.getString(3)), State.valueOf(rs.getString(4)), 
						new Date(rs.getDate(5).getTime()), rs.getString(6), UserDAO.getUser(rs.getInt(7)));	
				newTransition.setId(rs.getInt(1));
				return newTransition;
			}
			else{
				throw new InvalidTransitionException("Transition not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}
}
