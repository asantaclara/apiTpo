package backEnd;

import dao.ZoneDAO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidZoneException;

public class Zone {
	
	private String name;
	private int id = 0;
	
	public Zone(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int zoneId) {
		this.id = zoneId;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidZoneException {
		new ZoneDAO().save(this);
		
	}
}
