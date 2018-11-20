package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;
import services.ClientService;

public class StatisticsDAO {

	public List<ClientDTO> getRankingOfClaims() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
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
					client = ClientService.getIntance().getClientById(rs.getInt(5)).toDTO();
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

}
