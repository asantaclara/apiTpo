package services;

import backEnd.Claim;
import backEnd.CompositeClaim;
import dao.ClaimDAO;
import dao.ClientDAO;
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
		CompositeClaim claim = new CompositeClaim(new ClientDAO().getClient(dto.getClientId()), dto.getDate(), dto.getDescription());
		for (Integer i : dto.getInidividualClaimsId()) {
			Claim claimAux = new ClaimDAO().getClaim(i.intValue());
			claim.addClaim(claimAux);
		}
		claim.save();
		return claim.getClaimId();
	}
	
}
