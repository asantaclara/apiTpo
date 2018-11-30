package services;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import backEnd.Claim;
import backEnd.State;
import backEnd.Transition;
import dao.ClaimDAO;
import dao.TransitionDAO;
import dto.ClaimDTO;
import dto.TransitionDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;
import observer.Observable;

public class ClaimService extends Observable{

	private static ClaimService  instance = null;
	
	public static ClaimService getIntance() {
		if (instance == null) {
			instance = new ClaimService();
		}
		return instance;
	}
	
	private ClaimService() {
		
	}
	
	public String getClaimState(int claimNumber) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		Claim claim = new ClaimDAO().getClaim(claimNumber);
		return claim.getActualState().name();
	}
	
	public void treatClaim(TransitionDTO dto) throws InvalidTransitionException, ConnectionException, AccessException, SQLException, InvalidUserException, InvalidRoleException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidInvoiceItemException {
		//Este metodo me esta devolviendo una List<ClaimDTO> con todas las claims que se vieron modificadas en el proceso.
		
		if(dto.getResponsableId() == 0 || dto.getNewState() == null || dto.getClaimId() == 0 || dto.getDescription() == null) {
			throw new InvalidTransitionException("Missing parameters");
		}
		
		Claim aux = getClaim(dto.getClaimId());
		aux.treatClaim(UserService.getIntance().getUserById(dto.getResponsableId()), State.valueOf(dto.getNewState()), dto.getDescription());
		updateObservers();
		
		CompositeClaimService.getIntance().updateCompositeClaims(aux.getClaimId());
	}

	public List<ClaimDTO> getClaimsFromClient(int clientId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		List<ClaimDTO> claims = new LinkedList<>();
		
		for (Claim c : new ClaimDAO().getClaimsFromClient(clientId)) {
			claims.add(c.toDTO());
		}
		
		return claims;
	}
	
	public Claim getClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		return new ClaimDAO().getClaim(claimId);
	}

	public ClaimDTO getClaimDTO(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		return getClaim(claimId).toDTO();
	}

	public List<TransitionDTO> getAllTransitionsOfClaim(int claimId) throws ConnectionException, AccessException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		List<TransitionDTO> auxList = new LinkedList<>();
		
		for (Transition t : new TransitionDAO().getAllTransitionOfClaim(claimId)) {
			auxList.add(t.toDTO());
		}
		return auxList;
	}

}
