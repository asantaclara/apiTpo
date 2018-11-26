package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import backEnd.Claim;
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

public class ClaimDAO {

	public Claim getClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = SqlUtils.createStatement(con);  
		ResultSet rs = null;
		
		String SQL = "SELECT * FROM Claims LEFT JOIN WrongInvoiceClaims ON CLAIMS.ClaimId = WrongInvoiceClaims.WrongInvoiceId "
				+ "LEFT JOIN IncompatibleZoneClaims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId "
				+ "LEFT JOIN CompositeClaims ON Claims.ClaimId = CompositeClaims.CompositeClaimId "
				+ "LEFT JOIN MoreQuantityClaims ON Claims.ClaimId = MoreQuantityClaims.MoreQuantityId WHERE CLAIMS.ClaimId = " + claimId;

		rs = SqlUtils.executeQuery(stmt, con, SQL);
		
		try {
			if(rs.next()){
				if(rs.getInt(9) != 0) {
					return new CompositeClaimDAO().getCompositeClaim(claimId);
				} else if(rs.getInt(7) != 0){
					return new IncompatibleZoneClaimDAO().getIncompatibleZoneClaim(claimId);
				} else if(rs.getInt(6) != 0) {
					return new WrongInvoicingClaimDAO().getWrongInvoicingClaim(claimId);
				} else if(rs.getInt(11) != 0) {
					return new MoreQuantityClaimDAO().getMoreQuantityClaim(claimId);
				} else {
					throw new InvalidClaimException("Claim not found");
				}
			}
			else{
				throw new InvalidClaimException("Claim not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public List<Claim> getClaimsFromClient(int clientId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			String sqlQuery = "SELECT * FROM Claims LEFT JOIN WrongInvoiceClaims ON CLAIMS.ClaimId = WrongInvoiceClaims.WrongInvoiceId "
					+ "LEFT JOIN IncompatibleZoneClaims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId "
					+ "LEFT JOIN CompositeClaims ON Claims.ClaimId = CompositeClaims.CompositeClaimId "
					+ "LEFT JOIN MoreQuantityClaims ON Claims.ClaimId = MoreQuantityClaims.MoreQuantityId WHERE CLAIMS.ClientId = " + clientId;
			
			rs = SqlUtils.executeQuery(stmt, con, sqlQuery);
			
			List<Claim> claims = new LinkedList<>();
			List<Integer> claimsId = new LinkedList<>();
			try {
				while(rs.next()){
					if(rs.getInt(9) != 0) { // If is a CompositeClaim
						if(!claimsId.contains(rs.getInt(1))) {
							claims.add(new CompositeClaimDAO().getCompositeClaim(rs.getInt(1)));
							claimsId.add(rs.getInt(1));							
						}
					} else if(rs.getInt(7) != 0){ // If is a IncompatibleZoneClaim
						claims.add(new IncompatibleZoneClaimDAO().getIncompatibleZoneClaim(rs.getInt(1)));
						claimsId.add(rs.getInt(1));
					} else if(rs.getInt(6) != 0) { // If is a WrongInvoicingClaim
						claims.add(new WrongInvoicingClaimDAO().getWrongInvoicingClaim(rs.getInt(1)));
						claimsId.add(rs.getInt(1));
					} else if(rs.getInt(11) != 0) { // If is a MoreQuantityClaim
						claims.add(new MoreQuantityClaimDAO().getMoreQuantityClaim(rs.getInt(1)));
						claimsId.add(rs.getInt(1));
					} else {
						throw new InvalidClaimException("Claim not found");
					}
				}
				return claims;
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
		} finally {
			SqlUtils.closeConnection(con);
		}
		
		
	}
	
	public void updateState(Claim c) throws AccessException, ConnectionException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm1;
			
			try {
				prepStm1 = con.prepareStatement("UPDATE Claims SET State='" + c.getActualState().name() +"' WHERE ClaimId = " + c.getClaimId());
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm1.execute();
			} catch (SQLException e) {
				throw new AccessException("Save error");
			}					
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
}
