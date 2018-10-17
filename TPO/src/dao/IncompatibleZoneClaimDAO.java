package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backEnd.IncompatibleZoneClaim;
import backEnd.Invoice;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;

public class IncompatibleZoneClaimDAO {
	
	static public IncompatibleZoneClaim getIncompatibleZoneClaimThroughId(int claimNumber){
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT  * FROM IncompatibleZoneClaim where claimNumber = " + claimNumber;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			
			if(rs.next()){
				IncompatibleZoneClaim claim = new IncompatibleZoneClaim(rs.getString(1),rs.getString(2));
				return claim;
			}
			else{
				throw new InvalidClaimException("Claim not found");
			}
		} catch (SQLException e) {
			throw new ConnectionException("Connection error");
		}
	}

	
	static public void saveInvoice(Invoice i) throws ConnectionException, AccessException{
		Connection con = SqlUtils.getConnection();
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into invoices values(?,?,?,?,?)");
			stm.setInt(1, i.getId());
			stm.setString(2, i.getClient().get);
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
