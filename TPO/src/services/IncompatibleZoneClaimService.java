package services;

import java.sql.SQLException;
import java.util.Date;

import backEnd.Client;
import backEnd.IncompatibleZoneClaim;
import dao.ClientDAO;
import dto.IncompatibleZoneClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;

public class IncompatibleZoneClaimService {
	private static IncompatibleZoneClaimService  instance = null;
	
	public static IncompatibleZoneClaimService getIntance() {
		if (instance == null) {
			instance = new IncompatibleZoneClaimService();
		}
		return instance;
	}
	
	private IncompatibleZoneClaimService() {
		
	}
	
	public int addIncompatibleZoneClaim(IncompatibleZoneClaimDTO dto) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, SQLException {
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		if(existingClient != null) {
			String description = dto.getDescription();
			
			IncompatibleZoneClaim newClaim = new IncompatibleZoneClaim(existingClient, new Date(), description, existingClient.getZone());
			newClaim.save();
			return newClaim.getClaimId();
		
		}
		throw new InvalidClientException("Client not found");
	}
	
}
