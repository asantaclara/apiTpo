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

	@Override
	List<TransitionDTO> getTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void addTransition(TransitionDTO dto) {
		// TODO Auto-generated method stub
		
	}
	
}
