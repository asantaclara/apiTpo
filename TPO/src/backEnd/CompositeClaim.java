package backEnd;

import java.util.Date;
import java.util.List;

import dto.ClaimDTO;
import exceptions.InvalidClientException;
import exceptions.InvalidDateException;
import exceptions.InvalidDescriptionException;

public class CompositeClaim extends Claim{

	private List<Claim> claims;
	
	public CompositeClaim(Client client, Date date, String description) throws InvalidClientException, InvalidDateException, InvalidDescriptionException {
		super(client, date, description);
	}
	
	public void addClaims(Claim claim) {
		claims.add(claim);
	}
	
	@Override
	public State getActualState() {
		//Hay que ver como hacer para que si alguna claim esta en CLOSED que devuelva CLOSED
		//Si alguna esta en ENTERED pero ninguna en CLOSED que me devuelva ENTERED
		//Si ninguna esta en closed que devuelva el menor valor entre ingresada, en tratamiento y resuelta
		
		return null;
	}

	@Override
	public void treatClaim(User responsable, State newState, String description) {
		// Esto no puede hacer nada porque no se puede manejar una claim compuesta, solo se puede ver si esta resuelta.
		
	}

	@Override
	public ClaimDTO toDTO() {
		// No entiendo como hacer para usar la claim compuesta.
		return null;
	}

	
}
