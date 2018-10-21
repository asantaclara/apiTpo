package backEnd;

import java.util.Date;

import dao.IncompatibleZoneClaimDAO;
import dto.IncompatibleZoneClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;

public class IncompatibleZoneClaim extends IndividualClaim {

	private Zone zone;
	
	public IncompatibleZoneClaim(Client client, Date date, String description, Zone zone) throws InvalidClaimException {
		super(client, date, description);
		this.zone = zone;
	}

	@Override
	public void treatClaim(User responsable, State newState, String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public IncompatibleZoneClaimDTO toDTO() {
		return new IncompatibleZoneClaimDTO(claimId, client.getId(), description, zone.getId(), date);
	}

	@Override
	public void save() throws ConnectionException, AccessException, InvalidClaimException {
		IncompatibleZoneClaimDAO.save(this);
		
	}

	public Zone getZone() {
		return zone;
	}
}
