package backEnd;

import java.sql.SQLException;
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
	public IncompatibleZoneClaimDTO toDTO() {
		IncompatibleZoneClaimDTO aux = new IncompatibleZoneClaimDTO();
		
		aux.setClaimId(claimId);
		aux.setClientId(client.getId());
		aux.setDescription(description);
		aux.setZoneId(zone.getId());
		aux.setDate(date);
		aux.setState(actualState.name());
		for (Transition transition : transitions) {
			aux.addTransition(transition.toDTO());
		}
		
		return aux;
	}

	@Override
	public void save() throws ConnectionException, AccessException, InvalidClaimException, SQLException {
		new IncompatibleZoneClaimDAO().save(this);
		
	}

	public Zone getZone() {
		return zone;
	}
}
