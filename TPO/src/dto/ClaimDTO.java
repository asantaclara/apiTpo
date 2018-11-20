package dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class ClaimDTO {

	protected int claimId;
	protected int clientId;
	protected String description;
	protected Date date;
	protected String state;
	protected List<TransitionDTO> transitions = new LinkedList<>();
	
	public ClaimDTO() {
		
	}
	
	public abstract List<TransitionDTO> getTransitions();
	public abstract void addTransition(TransitionDTO dto);
	
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
		String[] aux = new String[5];
		
		aux[0] = String.valueOf(claimId);
		aux[1] = String.valueOf(clientId);
		aux[2] = date.toString();
		aux[3] = description;
		aux[4] = state;
		
		return aux;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	
}
