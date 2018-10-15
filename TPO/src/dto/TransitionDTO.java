package dto;

import java.util.Date;

public class TransitionDTO {

	private int claimId;
	private Date date;
	private int userId;
	private String previousState;
	private String newState;
	private String description;
	
	public TransitionDTO(int claimId, Date date, int userId, String previousState, String newState, String description) {
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
