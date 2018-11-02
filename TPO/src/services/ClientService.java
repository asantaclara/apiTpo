package services;

import backEnd.Client;
import dao.ClientDAO;
import dao.ZoneDAO;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;

public class ClientService {

	private static ClientService  instance = null;
	
	public static ClientService getIntance() {
		if (instance == null) {
			instance = new ClientService();
		}
		return instance;
	}
	
	private ClientService() {
		
	}
	
	public int addClient (ClientDTO dto) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		Client c = new Client(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), new ZoneDAO().getZone(dto.getZone()));
		c.saveInDB();
		return c.getId();
	}
	
	public void modifyClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException {
		
		Client existingClient =  new ClientDAO().getClient(dto.getId());
		
		if (existingClient != null) {
			existingClient.modify(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), new ZoneDAO().getZone(dto.getZone()));
		}
	}

	public void removeClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		existingClient.deactivateClient();
	}
}
