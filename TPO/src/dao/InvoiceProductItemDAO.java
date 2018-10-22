package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.AccessException;
import exceptions.ConnectionException;

public class InvoiceProductItemDAO {

	public void save(int invoiceId, int productId) throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
		
		try {
			prepStm = con.prepareStatement("insert into Invoice_ProductItem values(?,?)");
			prepStm.setInt(1, invoiceId);
			prepStm.setInt(2, productId);
			
			
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
	
}
