package dto;

import java.util.Date;

public abstract class ClaimDTO {

	protected int claimId;
	protected int clientId;
	protected String description;
	protected Date date;
	
	
	public ClaimDTO(int claimId, int clientId, String description, Date date) {
		this.claimId = claimId;
		this.clientId = clientId;
		this.description = description;
		this.date = date;
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
	public Date getDate() {
		return date;
	}
	
	
}
