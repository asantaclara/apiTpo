package dto;

import java.util.Date;

public class IncompatibleZoneClaimDTO extends ClaimDTO {

	private int zoneId;
	
	public IncompatibleZoneClaimDTO(int claimId, int clientId, String description, int zoneId, Date date) {
		super(claimId, clientId, description, date);
		this.zoneId = zoneId;
	}

	public int getZoneId() {
		return zoneId;
	}
	
	
	
}
