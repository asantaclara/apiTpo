package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import backEnd.Client;
import backEnd.InvoiceItem;
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

	public void save(WrongInvoicingClaim claim) throws ConnectionException, AccessException, InvalidProductException, InvalidInvoiceItemException, InvalidClaimException {
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
				
				prepStm2 = con.prepareStatement("insert into WrongInvoiceClaims values(?)");
				prepStm2.setInt(1, claim.getClaimId());
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm1.execute();
				prepStm2.execute();
				con.commit();
			} catch (SQLException e) {
				try {
					con.rollback();		
				} catch (SQLException e2) {
					e.printStackTrace();
					throw new AccessException("DB Error");
				}
				throw new AccessException("Save error");
			}
			
			for (InvoiceItem i : claim.getInvoices()) {
				i.save(claim.getClaimId());
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public WrongInvoicingClaim getWrongInvoicingClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM WrongInvoiceClaims JOIN Claims ON Claims.ClaimId = WrongInvoiceClaims.WrongInvoiceId WHERE Claims.ClaimId = " + claimId;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					Client client = new ClientDAO().getClient(rs.getInt(4));
					WrongInvoicingClaim newClaim = new WrongInvoicingClaim(client, new Date(rs.getDate(6).getTime()), rs.getString(5));
					newClaim.setClaimId(rs.getInt(1));
					newClaim.setActualState(State.valueOf(rs.getString(3)));
					for (InvoiceItem i : new InvoiceItemDAO().getAllInvoiceItemsOfClaim(newClaim)) {
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
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
}
