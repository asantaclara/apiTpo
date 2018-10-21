package dao;

import java.awt.Composite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Claim;
import backEnd.CompositeClaim;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;

public class CompositeClaimDAO {

	public static void save(CompositeClaim claim) throws ConnectionException, InvalidClaimException, AccessException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
	
		if(claim.getClaimId() != 0) {
			throw new InvalidClaimException("Claim already in data base");
		}
		
		claim.setClaimId(SqlUtils.lastId("Claims", "ClaimId") + 1);
		
		try {
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
			addCompositePart(claim.getClaimId(), c.getClaimId());
		}
	}
	
	private static void addCompositePart(int idComposite, int idIndividual) throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();
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

	private static List<Claim> getListOfClaims(int claimId) throws AccessException, ConnectionException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		List<Claim> returnList = new LinkedList<>();
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM CompositeClaims JOIN IncompatibleZoneClaims ON CompositeClaims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId "
																								+ "WHERE CompositeClaims.CompositeClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {		
			while(rs.next()){					
				returnList.add(IncompatibleZoneClaimDAO.getIncompatibleZoneClaim(rs.getInt(2)));
			}
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
		
		SQL = "SELECT * FROM CompositeClaims JOIN WrongInvoiceClaims ON CompositeClaims.ClaimId = WrongInvoiceClaims.WrongInvoiceId "
																								+ "WHERE CompositeClaims.CompositeClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {		
			while(rs.next()){					
				returnList.add(WrongInvoicingClaimDAO.getWrongInvoicingClaim(rs.getInt(2)));
			}
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
		
		SQL = "SELECT * FROM CompositeClaims JOIN MoreQuantityClaims ON CompositeClaims.ClaimId = MoreQuantityClaims.MoreQuantityId "
																								+ "WHERE CompositeClaims.CompositeClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {		
			while(rs.next()){					
				returnList.add(MoreQuantityClaimDAO.getMoreQuantityClaim(rs.getInt(2)));
			}
			return returnList;
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
		
	}

	public static CompositeClaim getCompositeClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM CompositeClaims JOIN Claims ON CompositeClaims.CompositeClaimId = Claims.ClaimId WHERE CompositeClaims.CompositeClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				CompositeClaim newClaim = new CompositeClaim(ClientDAO.getClient(rs.getInt(5)), new Date(rs.getDate(7).getTime()), rs.getString(6));
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
	}

	public static List<CompositeClaim> getAllClaims() throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM CompositeClaims";
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
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
	}
}
