package services;

import java.sql.SQLException;

import backEnd.Claim;
import backEnd.State;
import dao.ClaimDAO;
import dao.UserDAO;
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
		Claim aux = new ClaimDAO().getClaim(dto.getClaimId());
		aux.treatClaim(new UserDAO().getUser(dto.getResponsableId()), State.valueOf(dto.getNewState()), dto.getDescription());
		
	}
}
