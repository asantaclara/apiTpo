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

public class ClaimService {

	private static ClaimService  instance = null;
	
	public static ClaimService getIntance() {
		if (instance == null) {
			instance = new ClaimService();
		}
		return instance;
	}
	
	private ClaimService() {
		
	}
	
	public String getClaimState(int claimNumber) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		Claim claim = new ClaimDAO().getClaim(claimNumber);
		return claim.getActualState().name();
	}
	
	public void treatClaim(TransitionDTO dto) throws InvalidTransitionException, ConnectionException, AccessException, SQLException, InvalidUserException, InvalidRoleException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		if(dto.getResponsableId() == 0 || dto.getNewState() == null || dto.getClaimId() == 0 || dto.getDescription() == null) {
			throw new InvalidTransitionException("Missing parameters");
		}
		Claim aux = new ClaimDAO().getClaim(dto.getClaimId());
		aux.treatClaim(new UserDAO().getUser(dto.getResponsableId()), State.valueOf(dto.getNewState()), dto.getDescription());
		
	}

	public List<ClaimDTO> getClaimsFromClient(int clientId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		List<ClaimDTO> claims = new LinkedList<>();
		
		for (Claim c : new ClaimDAO().getClaimsFromCLient(clientId)) {
			claims.add(c.toDTO());
		}
		
		return claims;
	}
}