package dto;

import java.util.Date;
import java.util.List;
import java.util.LinkedList;

public abstract class ClaimDTO {

	protected int claimId;
	protected int clientId;
	protected String description;
	protected Date date;
	protected String state;
	
	public ClaimDTO() {
		
	}
	
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
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
	
	public String[] toDataRow() {
		String[] aux = new String[4];
		
		aux[0] = String.valueOf(claimId);
		aux[1] = String.valueOf(clientId);
		aux[2] = date.toString();
		aux[3] = description;
		
		return aux;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	
}
