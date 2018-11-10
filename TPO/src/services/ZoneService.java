package services;

import backEnd.Zone;
import dao.ZoneDAO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidZoneException;

public class ZoneService {

	private static ZoneService  instance = null;
	
	public static ZoneService getIntance() {
		if (instance == null) {
			instance = new ZoneService();
		}
		return instance;
	}
	
	private ZoneService() {
		
	}
	
	public Zone getZoneById(int zoneId) throws ConnectionException, AccessException, InvalidZoneException {
		return new ZoneDAO().getZone(zoneId);
	}
	
	public Zone getZoneByName(String zoneName) throws AccessException, ConnectionException, InvalidZoneException {
		return new ZoneDAO().getZone(zoneName);
	}
}
