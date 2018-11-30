package services;

import java.util.Date;
import java.util.List;

import backEnd.Claim;
import backEnd.CompositeClaim;
import dao.ClaimDAO;
import dao.CompositeClaimDAO;
import dto.CompositeClaimDTO;
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

public class CompositeClaimService extends Observable{
	private static CompositeClaimService  instance = null;
	
	public static CompositeClaimService getIntance() {
		if (instance == null) {
			instance = new CompositeClaimService();
		}
		return instance;
	}
	
	private CompositeClaimService() {
		
	}
	
	public int addCompositeClaim(CompositeClaimDTO dto) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		if(dto.getClientId() == 0 || dto.getDescription() == null) {
			throw new InvalidClaimException("Missing parameters");
		}
		CompositeClaim claim = new CompositeClaim(ClientService.getIntance().getClientById(dto.getClientId()), new Date(), dto.getDescription());
		for (Integer i : dto.getInidividualClaimsId()) {
			Claim claimAux = ClaimService.getIntance().getClaim(i.intValue());
			if(claim.getClient().getId() == dto.getClientId()) {				
				claim.addClaim(claimAux);
			} else {
				throw new InvalidClaimException("Claim doesn't belong to the client");
			}
		}
		claim.save();
		updateObservers();
		
		return claim.getClaimId();
	}
	
	public List<CompositeClaim> updateCompositeClaims(int individualClaimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException{
		List<CompositeClaim> compositeClaims = new CompositeClaimDAO().getAllClaimsByIndividualClaim(individualClaimId);		
		for (CompositeClaim compositeClaim : compositeClaims) {
			new ClaimDAO().updateState(compositeClaim);

		}
		updateObservers();
		
		return compositeClaims;
	}
	
}
