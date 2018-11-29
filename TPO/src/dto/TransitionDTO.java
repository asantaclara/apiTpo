package dto;

import java.util.Date;

public class TransitionDTO {

	private int claimId;
	private Date date;
	private int userId;
	private String previousState;
	private String newState;
	private String description;
	
	public TransitionDTO() {
		
	}
	
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setResponsableId(int userId) {
		this.userId = userId;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getClaimId() {
		return claimId;
	}
	public Date getDate() {
		return date;
	}
	public int getResponsableId() {
		return userId;
	}
	public String getPreviousState() {
		return previousState;
	}
	public String getNewState() {
		return newState;
	}
	public String getDescription() {
		return description;
	}
	
	public String[] toDataRow() {
		String[] aux = new String[6];
		
		aux[0] = String.valueOf(claimId);
		aux[1] = String.valueOf(date);
		aux[2] = String.valueOf(userId);
		aux[3] = previousState;
		aux[4] = newState;
		aux[5] = description;
		
		return aux;
	}
	
}
