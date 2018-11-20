package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import dto.ClaimQuantityPerMonthDTO;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;
import services.ClientService;

public class StatisticsDAO {

	public List<ClientDTO> getRankingClientsOfClaims() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con); 
			String sql = "select Claims.ClientId as Cliente, count(claims.clientId) as Cantidad from Claims group by Claims.ClientId ORDER BY Cantidad DESC";
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

}
