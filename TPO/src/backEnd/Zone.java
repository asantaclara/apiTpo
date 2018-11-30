package backEnd;

import dao.ZoneDAO;
import dto.ZoneDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidZoneException;

public class Zone {
	
	private String name;
	private int id = 0;
	
	public Zone(String name) throws InvalidZoneException {
		if(name == null || name.length() == 0) {
			throw new InvalidZoneException("Invalid name");
		}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int zoneId) throws InvalidZoneException {
		if (zoneId == 0) {
			throw new InvalidZoneException("Invalid zoneId");
		}
		this.id = zoneId;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidZoneException {
		new ZoneDAO().save(this);
		
	}
	
	public void modify() throws AccessException, InvalidZoneException, ConnectionException {
		new ZoneDAO().modify(this);
	}
	
	public void setName(String name) throws InvalidZoneException {
		if (name == null || name.length() == 0) {
			throw new InvalidZoneException("Invalid name");
		}
		this.name = name;
	}
	
	public ZoneDTO toDTO() {
		ZoneDTO aux = new ZoneDTO();
		
		aux.setName(name);
		aux.setZoneId(id);
		
		return aux;
	}
}
