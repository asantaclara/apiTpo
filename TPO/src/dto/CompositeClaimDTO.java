package dto;

import java.util.LinkedList;
import java.util.List;

public class CompositeClaimDTO extends ClaimDTO{

	private List<Integer> individualClaimsId = new LinkedList<>();
	
	public CompositeClaimDTO() {
		super();		
	}

	public void addIndividualClaimId(int individualClaimsId) {
		this.individualClaimsId.add(Integer.valueOf(individualClaimsId));
	}

	public List<Integer> getInidividualClaimsId() {
		return individualClaimsId;
	}
	
}
