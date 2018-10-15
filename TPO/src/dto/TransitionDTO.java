package dto;

import java.util.Date;

public class TransitionDTO {

	private int claimId;
	private Date date;
	private int userId;
	private int previousState;
	private int newState;
	private String description;
	
	public TransitionDTO(int claimId, Date date, int userId, int previousState, int newState, String description) {
		super();
		this.claimId = claimId;
		this.date = date;
		this.userId = userId;
		this.previousState = previousState;
		this.newState = newState;
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
	public int getPreviousState() {
		return previousState;
	}
	public int getNewState() {
		return newState;
	}
	public String getDescription() {
		return description;
	}
	
	
	
}
