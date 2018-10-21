package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import backEnd.ClaimType;
import backEnd.InvoiceItem;
import backEnd.MoreQuantityClaim;
import backEnd.ProductItem;
import backEnd.State;
import backEnd.WrongInvoicingClaim;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidZoneException;

public class WrongInvoicingClaimDAO {

	public static void save(WrongInvoicingClaim claim) throws ConnectionException, AccessException, InvalidProductException, InvalidInvoiceItemException, InvalidClaimException {
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
			
			prepStm2 = con.prepareStatement("insert into WrongInvoiceClaims values(?)");
			prepStm2.setInt(1, claim.getClaimId());
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm1.execute();
			prepStm2.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
		
		for (InvoiceItem i : claim.getInvoices()) {
			i.save(claim.getClaimId());
		}
	}

	public static WrongInvoicingClaim getWrongInvoicingClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM WrongInvoiceClaims JOIN Claims ON Claims.ClaimId = WrongInvoiceClaims.WrongInvoiceId WHERE Claims.ClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				WrongInvoicingClaim newClaim = new WrongInvoicingClaim(ClientDAO.getClient(rs.getInt(4)), new Date(rs.getDate(6).getTime()), rs.getString(5));
				newClaim.setClaimId(rs.getInt(1));
				newClaim.setActualState(State.valueOf(rs.getString(3)));
				for (InvoiceItem i : InvoiceItemDAO.getAllInvoiceItemsOfClaim(newClaim)) {
					newClaim.addInovice(i.getInvoice(), i.getInconsistency());
				}		
				return newClaim;
			}
			else{
				throw new InvalidClaimException("Claim not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ConnectionException("Data not reachable");
		}
	}
}
