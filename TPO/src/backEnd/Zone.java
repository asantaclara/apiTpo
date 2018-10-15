package backEnd;

import exceptions.AccessException;
import exceptions.ConnectionException;

public class Zone {
	
	private String name;
	private int zoneId;
	
	public Zone(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getZoneId() {
		return zoneId;
	}
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}
}
