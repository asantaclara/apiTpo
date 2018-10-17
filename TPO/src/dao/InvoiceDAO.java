package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidInvoiceException;


public class InvoiceDAO {
	/**
	 * 
	 * @param invoiceId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws InvoiceException
	 */
	static public Invoice getInvoiceThroughId(int invoiceId){
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT  * FROM invoices where invoiceId = " + invoiceId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Error de consulta");
		}
		try {
			
			if(rs.next()){
				Invoice invoice = new Invoice(rs.getString(1));
				return invoice;
			}
			else{
				throw new InvalidInvoiceException("Invoice number: " + invoiceId + " doesn't exist");
			}
		} catch (SQLException e) {
			throw new ConnectionException("No es posible acceder a los datos");
		}
	}
	/**
	 * Dado una factura la almacena en la BD
	 * @param Invoice i
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveInvoice(Invoice i){
		Connection con = SqlUtils.getConnection();
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into invoices values(?,?,?,?,?)");
			stm.setInt(1, i.getInoviceId());
			stm.setString(2, i.getClient());
			stm.setDate(3, i.getDate());  //convertir de java.sql.date a java.util.date
			stm.setInt(4, i.getItems());
			stm.executeUpdate();
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}
		
		try {
			stm.execute();
		} catch (SQLException e) {
			throw new AccessException("Imposible to save");
		}
	}
}
