package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.ProductItem;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidZoneException;
import services.ClientService;

public class InvoiceDAO {

	public List<Invoice> getAllInvoices() throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException{
		String sql = "SELECT * FROM Invoices WHERE Active = 1"; 
		return getAllInvoicesPrivate(sql);
	}
	
	public List<Invoice> getAllInvoicesFromClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException{
		String sql = "SELECT * FROM Invoices WHERE clientId = " + clientId + " AND Active = 1" ; 
		return getAllInvoicesPrivate(sql);
	}
	
	private static List<Invoice> getAllInvoicesPrivate(String sql) throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<Invoice> returnList = new LinkedList<>();
				Invoice newInvoice = null;
				
				while(rs.next()){	
					Client client = ClientService.getIntance().getClientById(rs.getInt(2));
					newInvoice = new Invoice(client,new Date(rs.getDate(3).getTime())); //Con esto paso de sql a utils
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
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public Invoice getInvoice(int invoiceId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM Invoices WHERE InvoiceId = " + invoiceId;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					if(rs.getByte(4) == 1) {	
						Client client = new ClientDAO().getClient(rs.getInt(2));
						Invoice newInvoice = new Invoice(client,new Date(rs.getDate(3).getTime()));
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	public void save(Invoice invoice) throws InvalidInvoiceException, ConnectionException, AccessException, InvalidProductException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
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
				new InvoiceProductItemDAO().save(invoice, pi);
			}	
		} finally {
			SqlUtils.closeConnection(con);
		}
		
	}
	
	public void modify(Invoice invoice) throws ConnectionException, AccessException, InvalidInvoiceException {
		Connection con = SqlUtils.getConnection();
		try {
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
}
