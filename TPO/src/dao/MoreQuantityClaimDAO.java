package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import backEnd.ClaimType;
import backEnd.MoreQuantityClaim;
import backEnd.ProductItem;
import backEnd.State;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;

public class MoreQuantityClaimDAO {

	public static void save(MoreQuantityClaim claim) throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm1;
		PreparedStatement prepStm2;
	
		if(claim.getClaimId() != 0) {
			throw new InvalidClaimException("Claim already in data base");
		}
		
		claim.setClaimId(SqlUtils.lastId("Claims", "ClaimId") + 1);
		
		try {
			prepStm1 = con.prepareStatement("insert into Claims values(?,?,?,?,?)");
			prepStm1.setInt(1, claim.getClaimId());
			prepStm1.setString(2,claim.getActualState().name());
			prepStm1.setInt(3, claim.getClient().getId());
			prepStm1.setString(4, claim.getDescription());
			prepStm1.setDate(5, new java.sql.Date(claim.getDate().getTime()));
			
			prepStm2 = con.prepareStatement("insert into MoreQuantityClaims values(?,?,?)");
			prepStm2.setInt(1, claim.getClaimId());
			prepStm2.setString(2,claim.getClaimType().name());
			prepStm2.setInt(3, claim.getInvoice().getId());
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm1.execute();
			prepStm2.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
		
		for (ProductItem pi : claim.getProducts()) {
			pi.save();
			MoreQuantityClaimProductItemDAO.save(claim.getClaimId(), pi.getId());
		}
	}

	public static MoreQuantityClaim getMoreQuantityClaim(int claimId) throws AccessException, ConnectionException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM MoreQuantityClaims JOIN Claims ON MoreQuantityClaims.MoreQuantityId = Claims.ClaimId WHERE Claims.ClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
					MoreQuantityClaim newClaim = new MoreQuantityClaim(ClientDAO.getClient(rs.getInt(6)), new Date(rs.getDate(8).getTime()), rs.getString(7), 
														ClaimType.valueOf(rs.getString(2)), InvoiceDAO.getInvoice(rs.getInt(3)));
					newClaim.setClaimId(rs.getInt(1));
					newClaim.setActualState(State.valueOf(rs.getString(5)));
					for (ProductItem pi : ProductItemDAO.getProductItemsOfMoreQuantityClaim(newClaim)) {
						newClaim.addProductItem(pi.getProduct(), pi.getQuantity());
					}
					return newClaim;
			}
			else{
				throw new InvalidInvoiceException("Invoice not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

}
