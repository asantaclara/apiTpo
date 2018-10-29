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
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;

public class TransitionDAO {

	public void save(Transition transition) throws ConnectionException, InvalidTransitionException, AccessException, SQLException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm1;
		
		if(transition.getId() != 0) {
			throw new InvalidTransitionException("Transition already in data base");
		}
		
		transition.setId(SqlUtils.lastId("Transitions", "TransitionId") + 1); 
		try {
			con.setAutoCommit(false);
			
			prepStm1 = con.prepareStatement("insert into Transitions values(?,?,?,?,?,?,?)");
			prepStm1.setInt(1, transition.getId());
			prepStm1.setInt(2, transition.getClaimId());
			prepStm1.setString(3, transition.getPreviousState().name());
			prepStm1.setString(4, transition.getNewState().name());
			prepStm1.setDate(5, new java.sql.Date(transition.getDate().getTime()));
			prepStm1.setString(6, transition.getDescription());
			prepStm1.setInt(7, transition.getResponsable().getId());

		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm1.execute();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new AccessException("Save error");
		}
	}

	public List<Transition> getAllTransitionOfClaim(int claimId) throws ConnectionException, AccessException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException{
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
													new Date(rs.getDate(5).getTime()), rs.getString(6), new UserDAO().getUser(rs.getInt(7)));	
				newTransition.setId(rs.getInt(1));
				returnList.add(newTransition);
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	public Transition getTransitionById(int transitionId) throws AccessException, InvalidUserException, ConnectionException, InvalidTransitionException, InvalidRoleException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
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
						new Date(rs.getDate(5).getTime()), rs.getString(6), new UserDAO().getUser(rs.getInt(7)));	
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
