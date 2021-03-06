package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.IncompatibleZoneClaim;
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
import services.ClientService;
import services.ZoneService;

public class IncompatibleZoneClaimDAO {

	public void save(IncompatibleZoneClaim claim) throws ConnectionException, InvalidClaimException, AccessException, SQLException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm1;
			PreparedStatement prepStm2;
			
			if(claim.getClaimId() != 0) {
				throw new InvalidClaimException("Claim already in data base");
			}
			
			claim.setClaimId(SqlUtils.lastId("Claims", "ClaimId") + 1);
			
			try {
				con.setAutoCommit(false);
				prepStm1 = con.prepareStatement("insert into Claims values(?,?,?,?,?)");
				prepStm1.setInt(1, claim.getClaimId());
				prepStm1.setString(2,claim.getActualState().name());
				prepStm1.setInt(3, claim.getClient().getId());
				prepStm1.setString(4, claim.getDescription());
				prepStm1.setDate(5, new java.sql.Date(claim.getDate().getTime()));
				
				prepStm2 = con.prepareStatement("insert into IncompatibleZoneClaims values(?,?)");
				prepStm2.setInt(1, claim.getClaimId());
				prepStm2.setInt(2, claim.getZone().getId());
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm1.execute();
				prepStm2.execute();
				con.commit();
			} catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
				throw new AccessException("Save error");
			}
		} finally {
			SqlUtils.closeConnection(con);
		}
		
	}

	public IncompatibleZoneClaim getIncompatibleZoneClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM IncompatibleZoneClaims JOIN Claims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId WHERE Claims.ClaimId = " + claimId;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					Client client = ClientService.getIntance().getClientById(rs.getInt(5));
					IncompatibleZoneClaim newClaim = new IncompatibleZoneClaim(client, new Date(rs.getDate(7).getTime()), rs.getString(6), ZoneService.getIntance().getZoneById(rs.getInt(2)));
					newClaim.setClaimId(rs.getInt(1));
					newClaim.setActualState(State.valueOf(rs.getString(4)));
					for (Transition t : new TransitionDAO().getAllTransitionOfClaim(rs.getInt(1))) {
						newClaim.addTransition(t);
					}
					return newClaim;
				}
				else{
					throw new InvalidClaimException("Claim not found");
				}
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	
	public List<IncompatibleZoneClaim> getAllIncompatibleZoneClaims() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		String sql = "SELECT * FROM IncompatibleZoneClaims JOIN Claims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId";
		return getAllIncompatibleZoneClaims(sql);
	}

	public List<IncompatibleZoneClaim> getAllIncompatibleZoneClaimsFromClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		String sql = "SELECT * FROM IncompatibleZoneClaims JOIN Claims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId where claims.ClientId = " + clientId;
		return getAllIncompatibleZoneClaims(sql);
	}
	
	private List<IncompatibleZoneClaim> getAllIncompatibleZoneClaims(String sql) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<IncompatibleZoneClaim> returnList = new LinkedList<>();
				IncompatibleZoneClaim newClaim = null;
				Client client = null;
				
				while(rs.next()){	
					client = ClientService.getIntance().getClientById(rs.getInt(5));
					newClaim = new IncompatibleZoneClaim(client, new Date(rs.getDate(7).getTime()), rs.getString(6), ZoneService.getIntance().getZoneById(rs.getInt(2)));
					newClaim.setClaimId(rs.getInt(1));
					newClaim.setActualState(State.valueOf(rs.getString(4)));
					for (Transition t : new TransitionDAO().getAllTransitionOfClaim(rs.getInt(1))) {
						newClaim.addTransition(t);
					}
					returnList.add(newClaim);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public List<IncompatibleZoneClaim> getAllClaimsForZoneResponsable() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		String sql = "SELECT * FROM IncompatibleZoneClaims JOIN Claims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId where claims.state IN ('ENTERED','IN_TREATMENT') and ClientId in (select clientId from Clients where active = 1)";
		return getAllIncompatibleZoneClaims(sql);
	}

	public List<IncompatibleZoneClaim> getAllOpenIncompatibleZoneClaimsByClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		String sql = "SELECT * FROM IncompatibleZoneClaims JOIN Claims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId where claims.state IN ('ENTERED','IN_TREATMENT')  and claims.ClientId = " + clientId;
		return getAllIncompatibleZoneClaims(sql);
	}
}
