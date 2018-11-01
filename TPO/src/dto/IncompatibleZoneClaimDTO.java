package dto;

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
	
}
