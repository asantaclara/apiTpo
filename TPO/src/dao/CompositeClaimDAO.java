package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Claim;
import backEnd.Client;
import backEnd.CompositeClaim;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;

public class CompositeClaimDAO {

	public void save(CompositeClaim claim) throws ConnectionException, InvalidClaimException, AccessException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(claim.getClaimId() != 0) {
				throw new InvalidClaimException("Claim already in data base");
			}
			
			claim.setClaimId(SqlUtils.lastId("Claims", "ClaimId") + 1);
			
			try {
				con.setAutoCommit(false);
				prepStm = con.prepareStatement("insert into Claims values(?,?,?,?,?)");
				prepStm.setInt(1, claim.getClaimId());
				prepStm.setString(2,claim.getActualState().name());
				prepStm.setInt(3, claim.getClient().getId());
				prepStm.setString(4, claim.getDescription());
				prepStm.setDate(5, new java.sql.Date(claim.getDate().getTime()));
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm.execute();
			} catch (SQLException e) {
				throw new AccessException("Save error");
			}
			
			for (Claim c : claim.getIndividualClaims()) {
				addCompositePart(con, claim.getClaimId(), c.getClaimId());
			}	
			
	
			try {
				con.commit();
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					throw new AccessException("DB Error");
				}
				throw new AccessException("Save error");
			}

		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	private static void addCompositePart(Connection con, int idComposite, int idIndividual) throws ConnectionException, AccessException {
		PreparedStatement prepStm;
		try {
			
			prepStm = con.prepareStatement("insert into CompositeClaims values(?,?)");
			prepStm.setInt(1, idComposite);
			prepStm.setInt(2, idIndividual);
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}			
	}

	private static List<Claim> getListOfClaims(int claimId) throws AccessException, ConnectionException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String SQL = "SELECT * FROM Claims LEFT JOIN WrongInvoiceClaims ON CLAIMS.ClaimId = WrongInvoiceClaims.WrongInvoiceId "
					+ "LEFT JOIN IncompatibleZoneClaims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId "
					+ "LEFT JOIN CompositeClaims ON Claims.ClaimId = CompositeClaims.CompositeClaimId "
					+ "LEFT JOIN MoreQuantityClaims ON Claims.ClaimId = MoreQuantityClaims.MoreQuantityId WHERE CompositeClaims.CompositeClaimId = " + claimId;
			
			rs = SqlUtils.executeQuery(stmt, con, SQL);
			
			try {
				List<Claim> claimList = new LinkedList<>();
				while(rs.next()){
					Claim claim = new ClaimDAO().getClaim(rs.getInt(10));
					claimList.add(claim);
				}
				return claimList;
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
		
	}
	
	public CompositeClaim getCompositeClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String SQL = "SELECT * FROM CompositeClaims JOIN Claims ON CompositeClaims.CompositeClaimId = Claims.ClaimId WHERE CompositeClaims.CompositeClaimId = " + claimId;
			
			rs = SqlUtils.executeQuery(stmt, con, SQL);
			
			try {
				if(rs.next()){
					Client client = new ClientDAO().getClient(rs.getInt(5));
					CompositeClaim newClaim = new CompositeClaim(client, new Date(rs.getDate(7).getTime()), rs.getString(6));
					newClaim.setClaimId(rs.getInt(1));
					for (Claim c : getListOfClaims(claimId)) {
						newClaim.addClaim(c);
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

	public List<CompositeClaim> getAllClaims() throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String SQL = "SELECT * FROM CompositeClaims";
			
			rs = SqlUtils.executeQuery(stmt, con, SQL);
			
			try {
				List<CompositeClaim> returnList = new LinkedList<>();
				CompositeClaim newClaim;
				while(rs.next()){
					newClaim = getCompositeClaim(rs.getInt(1));
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
	
	public List<CompositeClaim> getAllClaimsByIndividualClaim(int individualClaimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String SQL = "SELECT * FROM CompositeClaims WHERE ClaimId = " + individualClaimId;
			
			rs = SqlUtils.executeQuery(stmt, con, SQL);
			
			try {
				List<CompositeClaim> returnList = new LinkedList<>();
				CompositeClaim newClaim;
				
				while(rs.next()){
					newClaim = getCompositeClaim(rs.getInt(1));
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
}
