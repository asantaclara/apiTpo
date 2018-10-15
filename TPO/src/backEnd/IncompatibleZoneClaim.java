package backEnd;

import java.util.Date;

import dto.IncompatibleZoneClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidDateException;
import exceptions.InvalidDescriptionException;

public class IncompatibleZoneClaim extends IndividualClaim {

	private Zone zone;
	
	public IncompatibleZoneClaim(Client client, Date date, String description, Zone zone) throws InvalidClientException, InvalidDateException, InvalidDescriptionException {
		super(client, date, description);
		this.zone = zone;
	}

	@Override
	public void treatClaim(User responsable, State newState, String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public IncompatibleZoneClaimDTO toDTO() {
		return new IncompatibleZoneClaimDTO(claimId, client.getCuit(), description, zone.getZoneId());
	}

	@Override
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}

}
