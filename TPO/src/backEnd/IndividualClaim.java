package backEnd;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.ClaimDAO;
import dto.ClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidTransitionException;

public abstract class IndividualClaim extends Claim {

	
	protected List<Transition> transitions;
	protected State actualState;
	
	public IndividualClaim(Client client, Date date, String description) throws InvalidClaimException {
		super(client, date, description);
		this.actualState = State.ENTERED;
		transitions = new LinkedList<>();
	}

	@Override
	public State getActualState() {
		return actualState;
	}
	
	public void setActualState(State actualState) {
		this.actualState = actualState;
	}
	
	public void addTransition(Transition t) throws InvalidTransitionException {
		if(t != null) {			
			transitions.add(t);
		} else {
			throw new InvalidTransitionException("Invalid transition");
		}
	}
	
	public List<Transition> getTransitions() {
		return transitions;
	}

	public void treatClaim(User responsable, State newState, String description) throws InvalidTransitionException, ConnectionException, AccessException {
		if(this instanceof WrongInvoicingClaim) {
			if(responsable.getSecondaryRole() != Roles.INVOICING_RESPONSABLE) {
				throw new InvalidTransitionException("Incompatible Role");
			}
		} else if(this instanceof IncompatibleZoneClaim) {
			if(responsable.getSecondaryRole() != Roles.ZONE_RESPONSABLE) {
				throw new InvalidTransitionException("Incompatible Role");
			}
		} else if(this instanceof MoreQuantityClaim) {
			if(responsable.getSecondaryRole() != Roles.DISTRIBUTION_RESPONSABLE) {
				throw new InvalidTransitionException("Incompatible Role");
			}
		}
		
		Transition newTran = new Transition(claimId, actualState, newState, date, description, responsable);
		newTran.save();
		transitions.add(newTran);
		actualState = newState;
		new ClaimDAO().updateState(this);
	}
	
	public abstract ClaimDTO toDTO();
	public abstract void save() throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException, SQLException; 

}
