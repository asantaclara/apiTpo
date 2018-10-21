package backEnd;

import java.util.Date;

import dao.TransitionDAO;
import dto.TransitionDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidTransitionException;

public class Transition {

	private int claimId;
	private State previousState;
	private State newState;
	private Date date;
	private String description;
	private User responsable;
	private int id = 0;
	
	public Transition(int claimId, State previousState, State newState, Date date, String description,
			User responsable) throws InvalidTransitionException {
		super();
		this.claimId = claimId;
		this.previousState = previousState;
		this.newState = newState;
		this.date = date;
		this.description = description;
		this.responsable = responsable;
		
		if(newState.getValue() <= previousState.getValue()) {
			throw new InvalidTransitionException("Invalid transition from " + previousState.name() + " to " + newState.name());
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getClaimId() {
		return claimId;
	}

	public State getPreviousState() {
		return previousState;
	}

	public State getNewState() {
		return newState;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public User getResponsable() {
		return responsable;
	}

	public TransitionDTO toDTO() {
		return new TransitionDTO(claimId, date, responsable.getId(), previousState.name(), newState.name(), description);
	}
	
	public void save() throws ConnectionException, AccessException, InvalidTransitionException {
		TransitionDAO.save(this);
	}
	
	
}
