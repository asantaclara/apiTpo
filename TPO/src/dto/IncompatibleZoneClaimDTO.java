package dto;

import java.util.List;

public class IncompatibleZoneClaimDTO extends ClaimDTO {

	private int zoneId;
	
	public IncompatibleZoneClaimDTO() {
		super();
	}
	
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	
	public int getZoneId() {
		return zoneId;
	}

	@Override
	public List<TransitionDTO> getTransitions() {
		return transitions;
	}

	@Override
	public void addTransition(TransitionDTO dto) {
		transitions.add(dto);
	}
	
}
