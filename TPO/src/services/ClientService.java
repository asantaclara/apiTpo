package services;

import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import dao.ClientDAO;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;
import observer.Observable;

public class ClientService extends Observable{

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
		Client c = new Client(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), ZoneService.getIntance().getZoneByName(dto.getZone()));
		c.save();
		updateObservers();
		return c.getId();
	}
	
	public void modifyClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException {
		
		Client existingClient =  new ClientDAO().getActiveClient(dto.getId());
		
		if (existingClient != null) {
			existingClient.modify(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), (dto.getZone() != null) ? ZoneService.getIntance().getZoneByName(dto.getZone()) : null);
		}
		updateObservers();
	}

	public void removeClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		
		Client existingClient =  new ClientDAO().getActiveClient(clientId);
		
		existingClient.deactivateClient();
		updateObservers();
	}
	
	public Client getClientById(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		return new ClientDAO().getClient(clientId);
	}
	
	public ClientDTO getClientDTOById(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
			return getClientById(clientId).toDTO();
	}

	public List<ClientDTO> getAllActiveClients() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		List<Client> clients =  new ClientDAO().getAllActiveClients();
		List<ClientDTO> aux = new LinkedList<>();
		for (Client client : clients) {
			aux.add(client.toDTO());
		}
		return aux;
	}
}
