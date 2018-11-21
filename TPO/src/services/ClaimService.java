package services;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import backEnd.Claim;
import backEnd.State;
import dao.ClaimDAO;
import dao.UserDAO;
import dto.ClaimDTO;
import dto.TransitionDTO;
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
	
	public String getClaimState(int claimNumber) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		Claim claim = new ClaimDAO().getClaim(claimNumber);
		return claim.getActualState().name();
	}
	
	public void treatClaim(TransitionDTO dto) throws InvalidTransitionException, ConnectionException, AccessException, SQLException, InvalidUserException, InvalidRoleException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		//Este metodo me esta devolviendo una List<ClaimDTO> con todas las claims que se vieron modificadas en el proceso.
		
		if(dto.getResponsableId() == 0 || dto.getNewState() == null || dto.getClaimId() == 0 || dto.getDescription() == null) {
			throw new InvalidTransitionException("Missing parameters");
		}
		
		Claim aux = getClaim(dto.getClaimId());
		aux.treatClaim(UserService.getIntance().getUserById(dto.getResponsableId()), State.valueOf(dto.getNewState()), dto.getDescription());
		updateObservers(aux);
		
		CompositeClaimService.getIntance().updateCompositeClaims(aux.getClaimId());
	}

	public List<ClaimDTO> getClaimsFromClient(int clientId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		List<ClaimDTO> claims = new LinkedList<>();
		
		for (Claim c : new ClaimDAO().getClaimsFromClient(clientId)) {
			claims.add(c.toDTO());
		}
		
		return claims;
	}
	
	private void updateObservers(Claim claim) {
		List<ClaimDTO> claimToSend = new LinkedList<>();
		claimToSend.add(claim.toDTO());
		updateObservers(claimToSend);
	}
	
	public Claim getClaim(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		return new ClaimDAO().getClaim(claimId);
	}

	public ClaimDTO getClaimDTO(int claimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		return getClaim(claimId).toDTO();
	}
}
