package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import backEnd.Invoice;
import backEnd.ProductItem;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class InvoiceProductItemDAO {

	public void save(Invoice invoice, ProductItem pi) throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			try {
				prepStm = con.prepareStatement("insert into Invoice_ProductItem values(?,?)");
				prepStm.setInt(1, invoice.getId());
				prepStm.setInt(2, pi.getId());
				
				
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
	
}
