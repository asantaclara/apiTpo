package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.InvoiceItem;
import backEnd.WrongInvoicingClaim;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;

public class InvoiceItemDAO {

	public void save(InvoiceItem invoiceItem, int claimId) throws InvalidProductException, ConnectionException, AccessException, InvalidInvoiceItemException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(invoiceItem.getId() != 0) {
				throw new InvalidInvoiceItemException("InvoiceItem already in data base");
			}
			
			invoiceItem.setId(SqlUtils.lastId("InvoicesItems", "InvoiceItemId") + 1); 
			try {
				prepStm = con.prepareStatement("insert into InvoicesItems values(?,?,?,?)");
				prepStm.setInt(1, invoiceItem.getId());
				prepStm.setInt(2, invoiceItem.getInvoice().getId());
				prepStm.setString(3, invoiceItem.getInconsistency());
				prepStm.setInt(4, claimId);
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AccessException("Save error");
			}	
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	public InvoiceItem getInvoiceItem(int invoiceItemId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidInvoiceItemException, InvalidZoneException, InvalidProductItemException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM InvoicesItems WHERE InvoiceItemId = " + invoiceItemId; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					InvoiceItem newInvoiceItem = new InvoiceItem(new InvoiceDAO().getActiveInvoice(rs.getInt(2)), rs.getString(3));
					newInvoiceItem.setId(rs.getInt(1));
					return newInvoiceItem;
				}
				else{
					throw new InvalidInvoiceItemException("InvoiceItem not found");
				}
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public List<InvoiceItem> getAllInvoiceItemsOfClaim(WrongInvoicingClaim claim) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidInvoiceItemException, InvalidProductItemException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM InvoicesItems JOIN WrongInvoiceClaims ON InvoicesItems.ClaimId = WrongInvoiceClaims.WrongInvoiceId"
					+ " WHERE WrongInvoiceClaims.WrongInvoiceId = " + claim.getClaimId();
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<InvoiceItem> returnList = new LinkedList<>();
				InvoiceItem newInvoiceItem = null;
				
				while(rs.next()){
					newInvoiceItem = new InvoiceItem(new InvoiceDAO().getActiveInvoice(rs.getInt(2)), rs.getString(3));
					newInvoiceItem.setId(rs.getInt(1));
					returnList.add(newInvoiceItem);
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
