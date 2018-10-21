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
import exceptions.InvalidZoneException;

public class InvoiceItemDAO {

	public static void save(InvoiceItem invoiceItem, int claimId) throws InvalidProductException, ConnectionException, AccessException, InvalidInvoiceItemException {
		Connection con = SqlUtils.getConnection();
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
	}
	
	public static InvoiceItem getInvoiceItem(int invoiceItemId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidInvoiceItemException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM InvoicesItems WHERE InvoiceItemId = " + invoiceItemId; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				InvoiceItem newInvoiceItem = new InvoiceItem(InvoiceDAO.getInvoice(rs.getInt(2)), rs.getString(3));
				newInvoiceItem.setId(rs.getInt(1));
				return newInvoiceItem;
			}
			else{
				throw new InvalidInvoiceItemException("InvoiceItem not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	public static List<InvoiceItem> getAllInvoiceItemsOfClaim(WrongInvoicingClaim claim) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM InvoicesItems JOIN WrongInvoiceClaims ON InvoicesItems.ClaimId = WrongInvoiceClaims.WrongInvoiceId"
							+ " WHERE WrongInvoiceClaims.WrongInvoiceId = " + claim.getClaimId();
		
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		
		try {
			List<InvoiceItem> returnList = new LinkedList<>();
			InvoiceItem newInvoiceItem = null;
			
			while(rs.next()){
				newInvoiceItem = new InvoiceItem(InvoiceDAO.getInvoice(rs.getInt(2)), rs.getString(3));
				newInvoiceItem.setId(rs.getInt(1));
				returnList.add(newInvoiceItem);
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}
}
