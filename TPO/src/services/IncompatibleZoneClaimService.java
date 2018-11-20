package services;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.IncompatibleZoneClaim;
import dao.ClientDAO;
import dao.IncompatibleZoneClaimDAO;
import dto.IncompatibleZoneClaimDTO;
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
import observer.Observable;

public class IncompatibleZoneClaimService extends Observable{
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
		Client existingClient = ClientService.getIntance().getClientById(dto.getClientId());
		
		if(existingClient != null) {
			String description = dto.getDescription();
			
			IncompatibleZoneClaim newClaim = new IncompatibleZoneClaim(existingClient, new Date(), description, existingClient.getZone());
			newClaim.save();
			updateObservers(newClaim);
			return newClaim.getClaimId();
		
		}
		throw new InvalidClientException("Client not found");
	}
	
	private void updateObservers(IncompatibleZoneClaim claim) {
		List<IncompatibleZoneClaimDTO> claimToSend = new LinkedList<>();
		claimToSend.add(claim.toDTO());
		updateObservers(claimToSend);
	}

	public List<IncompatibleZoneClaimDTO> getAllIncompatibleZoneClaimsDTO() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		
		List<IncompatibleZoneClaim> claims = new IncompatibleZoneClaimDAO().getAllIncompatibleZoneClaims();
		List<IncompatibleZoneClaimDTO> claimsDTO =  new LinkedList<>();
		
		for (IncompatibleZoneClaim i : claims) {
			claimsDTO.add(i.toDTO());
		}
		return claimsDTO;
	}

	public List<IncompatibleZoneClaimDTO> getAllIncompatibleZoneClaimsDTOFromClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		List<IncompatibleZoneClaim> claims = new IncompatibleZoneClaimDAO().getAllIncompatibleZoneClaimsFromClient(clientId);
		List<IncompatibleZoneClaimDTO> claimsDTO =  new LinkedList<>();
		
		for (IncompatibleZoneClaim i : claims) {
			claimsDTO.add(i.toDTO());
		}
		return claimsDTO;
	}
	
}
