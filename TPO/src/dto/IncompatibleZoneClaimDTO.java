package dto;

public class IncompatibleZoneClaimDTO extends ClaimDTO {

	private int zoneId;
	
	public IncompatibleZoneClaimDTO(int claimId, String clientCuit, String description, int zoneId) {
		super(claimId, clientCuit, description);
		this.zoneId = zoneId;
	}

	public int getZoneId() {
		return zoneId;
	}
	
	
	
}
