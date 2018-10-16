package dto;

public class IncompatibleZoneClaimDTO extends ClaimDTO {

	private int zoneId;
	
	public IncompatibleZoneClaimDTO(int claimId, int clientId, String description, int zoneId) {
		super(claimId, clientId, description);
		this.zoneId = zoneId;
	}

	public int getZoneId() {
		return zoneId;
	}
	
	
	
}
