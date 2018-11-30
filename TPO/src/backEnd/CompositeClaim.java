package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.CompositeClaimDAO;
import dto.CompositeClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;

public class CompositeClaim extends Claim{

	private List<Claim> claims = new LinkedList<>();
	
	public CompositeClaim(Client client, Date date, String description) throws InvalidClaimException {
		super(client, date, description);
	}
	
	public void addClaim(Claim claim) throws InvalidClaimException {
		
		if(claim == null) {
			throw new InvalidClaimException("Claim doesn't exist");
		}
		
		if(claim.getClient().getId() ==  client.getId()) {			
			claims.add(claim);
		} else {
			throw new InvalidClaimException("The claim doesn't belong to the client");
		}
	}
	
	@Override
	public State getActualState() {
		//Hay que ver como hacer para que si alguna claim esta en CLOSED que devuelva CLOSED
		//Si alguna esta en ENTERED pero ninguna en CLOSED que me devuelva ENTERED
		//Si ninguna esta en closed que devuelva el menor valor entre ingresada, en tratamiento y resuelta
		int entered = 0;
		int solved = 0;
		
		for (Claim claim : claims) {
			switch (claim.getActualState()) {
			case CLOSED:
				return State.CLOSED;
			case ENTERED:
				entered++;
				break;
			case IN_TREATMENT:
				break;
			case SOLVED:
				solved++;
				break;
			default:
				break;
			}
		}
		
		if(solved == claims.size()) {
			return State.SOLVED;
		} else if(entered == claims.size()) {
			return State.ENTERED;
		} else {
			return State.IN_TREATMENT;
		}
	}

	@Override
	public void treatClaim(User responsable, State newState, String description) throws InvalidClaimException {
		throw new InvalidClaimException("Can't treat compositeClaim");
	}

	@Override
	public CompositeClaimDTO toDTO() {
		
		CompositeClaimDTO aux = new CompositeClaimDTO();
		
		aux.setClaimId(claimId);
		aux.setClientId(client.getId());
		aux.setDescription(description);
		aux.setDate(date);
		aux.setState(this.getActualState().name());
		
		for (Claim claim : claims) {
			aux.addIndividualClaimId(claim.getClaimId());
		}
		
		return aux;
	}

	@Override
	public void save() throws ConnectionException, AccessException, InvalidClaimException {
		new CompositeClaimDAO().save(this);
	}
	
	public List<Claim> getIndividualClaims() {
		return claims;
	}

	
}
