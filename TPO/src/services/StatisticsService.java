package services;

import java.util.List;

import dao.StatisticsDAO;
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

	public List<ClientDTO> getRankingClientsOfClaims() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		return new StatisticsDAO().getRankingClientsOfClaims();
	}

	public List<ClaimQuantityPerMonthDTO> getRankingClaimsPerMonth() throws ConnectionException, AccessException {
		return new StatisticsDAO().getRankingClaimsPerMonth();
	}

	public ClaimsPerCategoryDTO getAmountOfClaimsPerCategory() throws AccessException, ConnectionException {
		return new StatisticsDAO().getAmountOfClaimsPerCategory();
	}

	public List<UserDTO> getRankingOfUsers() throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		return new StatisticsDAO().getRankingOfUsers();
	}

}
