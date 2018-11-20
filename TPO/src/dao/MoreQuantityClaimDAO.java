package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.ClaimType;
import backEnd.Client;
import backEnd.IncompatibleZoneClaim;
import backEnd.MoreQuantityClaim;
import backEnd.ProductItem;
import backEnd.State;
import backEnd.Transition;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;
import services.ClientService;
import services.ZoneService;

public class MoreQuantityClaimDAO {

	public void save(MoreQuantityClaim claim) throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException {
		Connection con = SqlUtils.getConnection();
		try {
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
				
				prepStm2 = con.prepareStatement("insert into MoreQuantityClaims values(?,?,?)");
				prepStm2.setInt(1, claim.getClaimId());
				prepStm2.setString(2,claim.getClaimType().name());
				prepStm2.setInt(3, claim.getInvoice().getId());
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm1.execute();
				prepStm2.execute();
				con.commit();
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new AccessException("DB Error");
				}
				throw new AccessException("Save error");
			}
			
			for (ProductItem pi : claim.getProducts()) {
				pi.save();
				new MoreQuantityClaimProductItemDAO().save(claim, pi);
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public MoreQuantityClaim getMoreQuantityClaim(int claimId) throws AccessException, ConnectionException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM MoreQuantityClaims JOIN Claims ON MoreQuantityClaims.MoreQuantityId = Claims.ClaimId WHERE Claims.ClaimId = " + claimId;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					Client client = ClientService.getIntance().getClientById(rs.getInt(6));
					MoreQuantityClaim newClaim = new MoreQuantityClaim(client, new Date(rs.getDate(8).getTime()), rs.getString(7), 
							ClaimType.valueOf(rs.getString(2)), new InvoiceDAO().getInvoice(rs.getInt(3)));
					newClaim.setClaimId(rs.getInt(1));
					newClaim.setActualState(State.valueOf(rs.getString(5)));
					for (ProductItem pi : ProductItemDAO.getProductItemsOfMoreQuantityClaim(newClaim)) {
						newClaim.addProductItem(pi.getProduct(), pi.getQuantity());
					}
					for (Transition t : new TransitionDAO().getAllTransitionOfClaim(rs.getInt(1))) {
						newClaim.addTransition(t);
					}
					return newClaim;
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

	
	public List<MoreQuantityClaim> getAllMoreQuantityClaims() throws ConnectionException, InvalidClaimException, InvalidInvoiceException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidProductItemException{
		String sql = "SELECT * FROM MoreQuantityClaims JOIN Claims ON MoreQuantityClaims.MoreQuantityId = Claims.ClaimId";
		return getAllMoreQuantityClaims(sql);
	}
	
	public List<MoreQuantityClaim> getAllMoreQuantityClaimsFromClient(int clientId) throws ConnectionException, InvalidClaimException, InvalidInvoiceException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidProductItemException{
		String sql = "SELECT * FROM MoreQuantityClaims JOIN Claims ON MoreQuantityClaims.MoreQuantityId = Claims.ClaimId where claims.ClientId = " + clientId;
		return getAllMoreQuantityClaims(sql);
	}
	
	private List<MoreQuantityClaim> getAllMoreQuantityClaims(String sql) throws ConnectionException, InvalidClaimException, InvalidInvoiceException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidProductItemException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<MoreQuantityClaim> returnList = new LinkedList<>();
				MoreQuantityClaim newClaim = null;
				Client client = null;
				
				while(rs.next()){	
					client = ClientService.getIntance().getClientById(rs.getInt(6));
					newClaim = new MoreQuantityClaim(client, new Date(rs.getDate(8).getTime()), rs.getString(7), 
							ClaimType.valueOf(rs.getString(2)), new InvoiceDAO().getInvoice(rs.getInt(3)));
					newClaim.setClaimId(rs.getInt(1));
					newClaim.setActualState(State.valueOf(rs.getString(5)));
					for (ProductItem pi : ProductItemDAO.getProductItemsOfMoreQuantityClaim(newClaim)) {
						newClaim.addProductItem(pi.getProduct(), pi.getQuantity());
					}
					for (Transition t : new TransitionDAO().getAllTransitionOfClaim(rs.getInt(1))) {
						newClaim.addTransition(t);
					}
					returnList.add(newClaim);
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
