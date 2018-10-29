package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backEnd.Claim;
import backEnd.Transition;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidZoneException;

public class ClaimDAO {

	public Claim getClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Claims LEFT JOIN WrongInvoiceClaims ON CLAIMS.ClaimId = WrongInvoiceClaims.WrongInvoiceId "
				+ "LEFT JOIN IncompatibleZoneClaims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId "
				+ "LEFT JOIN CompositeClaims ON Claims.ClaimId = CompositeClaims.CompositeClaimId "
				+ "LEFT JOIN MoreQuantityClaims ON Claims.ClaimId = MoreQuantityClaims.MoreQuantityId WHERE CLAIMS.ClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
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
		}
	}

	
	public void updateState(Claim c) throws AccessException, ConnectionException, SQLException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm1;
		
		try {
			con.setAutoCommit(false);
			
			prepStm1 = con.prepareStatement("UPDATE Claims SET State='" + c.getActualState().name() +"' WHERE ClaimId = " + c.getClaimId());
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
}
