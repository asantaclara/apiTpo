package backEnd;

import dao.ZoneDAO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidZoneException;

public class Zone {
	
	private String name;
	private int zoneId=0;
	
	public Zone(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getZoneId() {
		return zoneId;
	}
	
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidZoneException {
		ZoneDAO.save(this);
		
	}
}
