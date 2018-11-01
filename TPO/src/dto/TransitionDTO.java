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

	public void setUserId(int userId) {
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
	public int getUserId() {
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
	
}
