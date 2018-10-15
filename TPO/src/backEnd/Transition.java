package backEnd;

import java.util.Date;

import dto.TransitionDTO;

public class Transition {

	private int claimId;
	private State previousState;
	private State newState;
	private Date date;
	private String description;
	private User responsable;
	
	public Transition(int claimId, State previousState, State newState, Date date, String description,
			User responsable) {
		super();
		this.claimId = claimId;
		this.previousState = previousState;
		this.newState = newState;
		this.date = date;
		this.description = description;
		this.responsable = responsable;
	}
	
	public TransitionDTO toDTO() {
		return new TransitionDTO(claimId, date, responsable.getUserId(), previousState.getState(), newState.getState(), description);
	}
	
	
}
