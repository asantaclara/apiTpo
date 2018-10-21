package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backEnd.Claim;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;

public class ClaimDAO {

	public static Claim getClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
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
					return CompositeClaimDAO.getCompositeClaim(claimId);
				} else if(rs.getInt(7) != 0){
					return IncompatibleZoneClaimDAO.getIncompatibleZoneClaim(claimId);
				} else if(rs.getInt(6) != 0) {
					return WrongInvoicingClaimDAO.getWrongInvoicingClaim(claimId);
				} else if(rs.getInt(11) != 0) {
					return MoreQuantityClaimDAO.getMoreQuantityClaim(claimId);
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
	
}
