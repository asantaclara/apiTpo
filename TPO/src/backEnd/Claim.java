package backEnd;

import java.sql.SQLException;
import java.util.Date;

import dto.ClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidTransitionException;

public abstract class Claim {
	
	protected int claimId = 0;
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
	public abstract void treatClaim(User responsable, State newState, String description) throws InvalidClaimException, InvalidTransitionException, ConnectionException, AccessException, SQLException;
	public abstract ClaimDTO toDTO();
	public abstract void save() throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException, SQLException;
	
	public boolean validateClient(Client client) throws InvalidClientException {
		if(client == null) {
			throw new InvalidClientException("Invalid client");
		}
		return (client.equals(this.client));
	}
	
	public int getClaimId() {
		return claimId;
	}
	
	public Client getClient() {
		return client;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setClaimId(int claimId) {
		if(claimId > 0) {			
			this.claimId = claimId;
		}
	}	
}
