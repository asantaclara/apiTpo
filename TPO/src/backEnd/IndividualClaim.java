package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dto.ClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidDateException;
import exceptions.InvalidDescriptionException;

public abstract class IndividualClaim extends Claim {

	
	protected List<Transition> transitions;
	protected State actualState;
	protected ClaimType claimType;
	
	public IndividualClaim(Client client, Date date, String description) throws InvalidClientException, InvalidDateException, InvalidDescriptionException {
		super(client, date, description);
		this.actualState = State.ENTERED;
		transitions = new LinkedList<>();
	}

	@Override
	public State getActualState() {
		return actualState;
	}

	public abstract void treatClaim(User responsable, State newState, String description);
	public abstract ClaimDTO toDTO();
	public abstract void save() throws ConnectionException, AccessException; 

}
