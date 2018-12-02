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
	
	public Transition(int claimId, State previousState, State newState, Date date, String description, User responsable) throws InvalidTransitionException {
		if(claimId < 1) {
			throw new InvalidTransitionException("Invalid Id");
		}
		this.claimId = claimId;
		
		if(previousState == null || newState == null ||newState.getValue() <= previousState.getValue()) {
			throw new InvalidTransitionException("Invalid transition from " + previousState.name() + " to " + newState.name());
		}
		this.previousState = previousState;
		this.newState = newState;
		
		if(date == null) {
			throw new InvalidTransitionException("Invalid date");
		}
		this.date = date;
		
		if(description == null || description.length() == 0) {
			throw new InvalidTransitionException("Invalid description");
		}
		this.description = description;
		
		if (responsable == null) {
			throw new InvalidTransitionException("Invalid responsable");
		}
		this.responsable = responsable;
		
		
	}
	
	public void setId(int id) throws InvalidTransitionException {
		if(id < 1) {
			throw new InvalidTransitionException("Invalid id");
		}
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
		
		TransitionDTO aux = new TransitionDTO();
		
		aux.setClaimId(claimId);
		aux.setDate(date);
		aux.setResponsableId(responsable.getId());
		aux.setPreviousState(previousState.name());
		aux.setNewState(newState.name());
		aux.setDescription(description);
		
		return aux;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidTransitionException{
		new TransitionDAO().save(this);
	}
	
	
}
