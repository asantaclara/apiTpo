package services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Claim;
import backEnd.CompositeClaim;
import dao.ClaimDAO;
import dao.ClientDAO;
import dao.CompositeClaimDAO;
import dto.CompositeClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;

public class CompositeClaimService {
	private static CompositeClaimService  instance = null;
	
	public static CompositeClaimService getIntance() {
		if (instance == null) {
			instance = new CompositeClaimService();
		}
		return instance;
	}
	
	private CompositeClaimService() {
		
	}
	
	public int addCompositeClaim(CompositeClaimDTO dto) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		if(dto.getClientId() == 0 || dto.getDescription() == null) {
			throw new InvalidClaimException("Missing parameters");
		}
		CompositeClaim claim = new CompositeClaim(new ClientDAO().getClient(dto.getClientId()), new Date(), dto.getDescription());
		for (Integer i : dto.getInidividualClaimsId()) {
			Claim claimAux = new ClaimDAO().getClaim(i.intValue());
			if(claim.getClient().getId() == dto.getClientId()) {				
				claim.addClaim(claimAux);
			} else {
				throw new InvalidClaimException("Claim doesn't belong to the client");
			}
		}
		claim.save();
		return claim.getClaimId();
	}
	
	public List<CompositeClaim> updateCompositeClaims(int individualClaimId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException{
		List<CompositeClaim> compositeClaims = new CompositeClaimDAO().getAllClaimsByIndividualClaim(individualClaimId);
		for (CompositeClaim compositeClaim : compositeClaims) {
			new ClaimDAO().updateState(compositeClaim);
		}
		return compositeClaims;
	}
}
