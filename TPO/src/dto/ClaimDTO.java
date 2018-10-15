package dto;

public abstract class ClaimDTO {

	protected int claimId;
	protected String clientCuit;
	protected String description;
	protected int claimType;
	
	
	public ClaimDTO(int claimId, String clientCuit, String description) {
		this.claimId = claimId;
		this.clientCuit = clientCuit;
		this.description = description;
	}
	
	public int getClaimId() {
		return claimId;
	}
	public String getClientCuit() {
		return clientCuit;
	}
	public String getDescription() {
		return description;
	}
	public int getClaimType() {
		return claimType;
	}
	
	
}
