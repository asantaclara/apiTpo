package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Invoice;
import backEnd.ProductItem;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidZoneException;

public class InvoiceDAO {

	public static List<Invoice> getAllInvoices() throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException{
		String SQL = "SELECT * FROM Invoices WHERE Active = 1"; 
		return getAllInvoicesPrivate(SQL);
	}
	
	public static List<Invoice> getAllInvoicesFromClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException{
		String SQL = "SELECT * FROM Invoices WHERE clientId = " + clientId + " AND Active = 1" ; 
		return getAllInvoicesPrivate(SQL);
	}
	
	private static List<Invoice> getAllInvoicesPrivate(String SQL) throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		
		try {
			List<Invoice> returnList = new LinkedList<>();
			Invoice newInvoice = null;
			
			while(rs.next()){				
				newInvoice = new Invoice(ClientDAO.getClient(rs.getInt(2)),new Date(rs.getDate(3).getTime())); //Con esto paso de sql a utils
				newInvoice.setId(rs.getInt(1));
				for (ProductItem pi : ProductItemDAO.getProductItemsOfInvoice(newInvoice)) {
					newInvoice.addProductItem(pi.getProduct(), pi.getQuantity());
				}
				returnList.add(newInvoice);
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	public static Invoice getInvoice(int invoiceId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Invoices WHERE InvoiceId = " + invoiceId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				if(rs.getByte(4) == 1) {					
					Invoice newInvoice = new Invoice(ClientDAO.getClient(rs.getInt(2)),new Date(rs.getDate(3).getTime()));
					newInvoice.setId(rs.getInt(1));
					for (ProductItem pi : ProductItemDAO.getProductItemsOfInvoice(newInvoice)) {
						newInvoice.addProductItem(pi.getProduct(), pi.getQuantity());
					}
					return newInvoice;
				} else {
					throw new InvalidInvoiceException("The invoice is not active");
				}
			}
			else{
				throw new InvalidInvoiceException("Invoice not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}
	
	public static void save(Invoice invoice) throws InvalidInvoiceException, ConnectionException, AccessException, InvalidProductException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
	
		/*
		 * Si el cliente no tiene el clientId cargado quiere decir que es un cliente nuevo por lo tanto busco el ultimo
		 * clientId que hay en la base de datos, le sumo uno y se lo asigno a este cliente nuevo para despues guardarlo.
		 */
		if(invoice.getId() != 0) {
			throw new InvalidInvoiceException("Invoice already in data base");
		}
		
		invoice.setId(SqlUtils.lastId("Invoices", "InvoiceId") + 1); 
		
		try {
			prepStm = con.prepareStatement("insert into Invoices values(?,?,?,?)");
			prepStm.setInt(1, invoice.getId());
			prepStm.setInt(2, invoice.getClient().getId());
			prepStm.setDate(3, new java.sql.Date(invoice.getDate().getTime()));
			prepStm.setByte(4, (byte) ((invoice.isActive() == true) ? 1 : 0));
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccessException("Save error");
		}
		
		for (ProductItem pi : invoice.getItems()) {
			pi.save();
			InvoiceProductItemDAO.save(invoice.getId(), pi.getId());
		}
		
	}
	
	static public void modify(Invoice invoice) throws ConnectionException, AccessException, InvalidInvoiceException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
		
		if(invoice.getId() == 0) {
			throw new InvalidInvoiceException("Invoice not in data base");
		}

		try {
			prepStm = con.prepareStatement("UPDATE Invoices SET ClientId = ?, Date = ?, Active = ? WHERE InvoiceId = " + invoice.getId());
			prepStm.setInt(1, invoice.getClient().getId());
			prepStm.setDate(2, new java.sql.Date(invoice.getDate().getTime()));
			prepStm.setByte(3, (byte) ((invoice.isActive() == true) ? 1 : 0));
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}
		try {
			prepStm.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
	}
}
