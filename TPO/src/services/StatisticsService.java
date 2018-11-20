package services;

import java.util.List;

import dao.StatisticsDAO;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;

public class StatisticsService {
	
	private static StatisticsService  instance = null;
	
	public static StatisticsService getIntance() {
		if (instance == null) {
			instance = new StatisticsService();
		}
		return instance;
	}
	
	private StatisticsService() {
		
	}

	public List<ClientDTO> getRankingOfClaims() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		return new StatisticsDAO().getRankingOfClaims();
	}
}
