package backEnd;

import java.util.Date;

import dto.ClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;

public abstract class Claim {
	
	protected int claimId;
	protected Client client;
	protected Date date;
	protected String description;
	
	public Claim(Client client, Date date, String description) throws InvalidClaimException {
		if(client == null) {
			throw new InvalidClaimException("Client not found");
		}
		if(date == null) {
			throw new InvalidClaimException("Date not found");
		}
		if(description == null || description.length() == 0) {
			throw new InvalidClaimException("Description not found");
		}
		
		this.client = client;
		this.date = date;
		this.description = description;
	}
	
	public abstract State getActualState();
	public abstract void treatClaim(User responsable, State newState, String description);
	public abstract ClaimDTO toDTO();
	public abstract void save() throws ConnectionException, AccessException;
	
	public boolean validateClient(Client client) {
		return (client.equals(this.client));
	}
	
	public int getClaimId() {
		return claimId;
	}
	
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}
	
}
