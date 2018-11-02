package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import backEnd.Client;
import backEnd.IncompatibleZoneClaim;
import backEnd.State;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;

public class IncompatibleZoneClaimDAO {

	public void save(IncompatibleZoneClaim claim) throws ConnectionException, InvalidClaimException, AccessException, SQLException {
		Connection con = SqlUtils.getConnection();
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
			
			prepStm2 = con.prepareStatement("insert into IncompatibleZoneClaims values(?,?)");
			prepStm2.setInt(1, claim.getClaimId());
			prepStm2.setInt(2, claim.getZone().getId());
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm1.execute();
			prepStm2.execute();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			throw new AccessException("Save error");
		}
		
	}

	public IncompatibleZoneClaim getIncompatibleZoneClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM IncompatibleZoneClaims JOIN Claims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId WHERE Claims.ClaimId = " + claimId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				Client client = new ClientDAO().getClient(rs.getInt(5));
				IncompatibleZoneClaim newClaim = new IncompatibleZoneClaim(client, new Date(rs.getDate(7).getTime()), rs.getString(6), new ZoneDAO().getZone(rs.getInt(2)));
				newClaim.setClaimId(rs.getInt(1));
				newClaim.setActualState(State.valueOf(rs.getString(4)));
				return newClaim;
			}
			else{
				throw new InvalidClaimException("Claim not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}
}