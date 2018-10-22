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
	protected ClaimType claimType;
	
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

	public void treatClaim(User responsable, State newState, String description) throws InvalidTransitionException, ConnectionException, AccessException, SQLException {
		
		Transition newTran = new Transition(claimId, actualState, newState, date, description, responsable);
		newTran.save();
		actualState = newState;
	}
	public abstract ClaimDTO toDTO();
	public abstract void save() throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException, SQLException; 

}
