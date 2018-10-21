package dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CompositeClaimDTO extends ClaimDTO{

	private List<Integer> claimsId = new LinkedList<>();
	
	public CompositeClaimDTO(int claimId, int clientId, String description, Date date) {
		super(claimId, clientId, description, date);
		
	}

	public List<Integer> getClaimsId() {
		return claimsId;
	}
	
	public void addClaimId(int claimId) {
		claimsId.add(claimId);
	}
	
	
	
	
}
