package dto;

public abstract class ClaimDTO {

	protected int claimId;
	protected int clientId;
	protected String description;
	protected int claimType;
	
	
	public ClaimDTO(int claimId, int clientId, String description) {
		this.claimId = claimId;
		this.clientId = clientId;
		this.description = description;
	}
	
	public int getClaimId() {
		return claimId;
	}
	public int getClientId() {
		return clientId;
	}
	public String getDescription() {
		return description;
	}
	public int getClaimType() {
		return claimType;
	}
	
	
}
