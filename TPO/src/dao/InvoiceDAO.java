package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Exceptions.AccessException;
import Exceptions.ConnectException;
import Exceptions.InvoiceException;
import Models.Invoice;


public class InvoiceDAO {
	/**
	 * 
	 * @param invoiceId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws InvoiceException
	 */
	static public Invoice getInvoiceThroughId(int invoiceId) throws ConnectException, AccessException, InvoiceException{
		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {    
			con = ConnectionFactory.getInstance().getConection();
		}
		catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw new ConnectException("No esta disponible el acceso al Servidor");
		}
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Error de acceso");
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
				throw new ClientException("El cliente con id: " + clientId + " no existe");
			}
		} catch (SQLException e) {
			throw new ConnectException("No es posible acceder a los datos");
		}
	}
	/**
	 * Dado una factura la almacena en la BD
	 * @param Invoice i
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveInvoice(Invoice i) throws ConnectException, AccessException{
		Connection con;
		
		try {
			con = ConnectionFactory.getInstance().getConection();
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectException("No esta disponible el acceso al Servidor");
		} 
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into invoices values(?,?,?,?,?)");
			stm.setInt(1, i.getInoviceId());
			stm.setString(2, i.getClient());
			stm.setDate(3, i.getDate());  //convertir de java.sql.date a java.util.date
			stm.setInt(4, i.getItems());
			stm.executeUpdate();
			
		} catch (SQLException e) {
			throw new AccessException("Error de acceso");
		}
		
		try {
			stm.execute();
		} catch (SQLException e) {
			throw new AccessException("No se pudo guardar");
		}
	}
}
