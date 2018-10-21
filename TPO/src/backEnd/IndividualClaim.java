package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dto.ClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;

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

	public abstract void treatClaim(User responsable, State newState, String description);
	public abstract ClaimDTO toDTO();
	public abstract void save() throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException; 

}
