package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import dto.ClaimQuantityPerMonthDTO;
import dto.ClaimsPerCategoryDTO;
import dto.ClientDTO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;
import services.ClientService;
import services.UserService;

public class StatisticsDAO {

	public List<ClientDTO> getRankingClientsOfClaims() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con); 
			String sql = "select Claims.ClientId as Cliente, count(claims.clientId) as Cantidad from Claims join Clients on Clients.ClientId = Claims.ClientId " + 
					"where Clients.Active = 1 group by Claims.ClientId ORDER BY Cantidad DESC";
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<ClientDTO> returnList = new LinkedList<>();
				ClientDTO client = null;
				int ranking = 1;
				while(rs.next()){	
					client = ClientService.getIntance().getClientById(rs.getInt(1)).toDTO();
					client.setRanking(ranking++);
					client.setClaimsCount(rs.getInt(2));
					returnList.add(client);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public List<ClaimQuantityPerMonthDTO> getRankingClaimsPerMonth() throws ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con); 
			String sql = "SELECT count(claims.claimId) as Cantidad, MONTH(claims.date) as Mes from Claims group by MONTH(claims.date), month(claims.date)";
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<ClaimQuantityPerMonthDTO> returnList = new LinkedList<>();
				ClaimQuantityPerMonthDTO month = null;
				while(rs.next()){	
					month = new ClaimQuantityPerMonthDTO();
					month.setQuantity(rs.getInt(1));
					month.setMonth(Month.of(rs.getInt(2)).toString());
					returnList.add(month);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public ClaimsPerCategoryDTO getAmountOfClaimsPerCategory() throws AccessException, ConnectionException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con); 
			String sql = "SELECT count(IncompatibleZoneClaims.IncompatibleZoneId) as IncompatibleZone, count(MoreQuantityClaims.MoreQuantityId) as MoreQuantity, "
					+ "count(WrongInvoiceClaims.WrongInvoiceId) as WrongInvoicing, count(claims.claimId) as Total FROM Claims "
					+ "LEFT JOIN WrongInvoiceClaims ON CLAIMS.ClaimId = WrongInvoiceClaims.WrongInvoiceId "
					+ "LEFT JOIN IncompatibleZoneClaims ON Claims.ClaimId = IncompatibleZoneClaims.IncompatibleZoneId "
					+ "LEFT JOIN MoreQuantityClaims ON Claims.ClaimId = MoreQuantityClaims.MoreQuantityId";
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				ClaimsPerCategoryDTO claims = new ClaimsPerCategoryDTO();
				if(rs.next()){	
					claims.setIncompatibleZoneClaims(rs.getInt(1));
					claims.setMoreQuantityClaims(rs.getInt(2));
					claims.setWrongInvoicingClaims(rs.getInt(3));
					claims.setCompositeClaims(rs.getInt(4)-rs.getInt(3)-rs.getInt(2)-rs.getInt(1));
				}
				return claims;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public List<UserDTO> getRankingOfUsers() throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con); 
			String sql = "select a2.UserId, avg(datediff(day, a1.Date,  a2.Date)) as diferencia" + 
					"	from (select ClaimId, PrevState, State, date, USERID " + 
					"			from Transitions" + 
					"			where PrevState = 'entered') a1" + 
					"	inner join " + 
					"		(select ClaimId, PrevState, State, date, USERID " + 
					"			from Transitions" + 
					"			where State in ('solved','closed')) a2 on a1.ClaimId = a2.ClaimId" + 
					" group by a2.UserId ORDER BY diferencia asc";
					
			ResultSet rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<UserDTO> userList = new LinkedList<>();
				UserDTO user = null;
				int i = 1;
				while(rs.next()){	
					user = UserService.getIntance().getUserById(rs.getInt(1)).toDTO();
					user.setRanking(i++);
					user.setAvgReponseTime(rs.getInt(2));
					userList.add(user);
				}
				return userList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

}
