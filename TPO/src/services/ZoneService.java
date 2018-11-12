package services;

import java.util.LinkedList;
import java.util.List;

import backEnd.WrongInvoicingClaim;
import backEnd.Zone;
import dao.ZoneDAO;
import dto.WrongInvoicingClaimDTO;
import dto.ZoneDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidZoneException;
import observer.Observable;

public class ZoneService extends Observable{

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

	public List<ZoneDTO> getAllZones() throws ConnectionException, AccessException {
		List<Zone> aux =  new ZoneDAO().getAllZones();
		List<ZoneDTO> zonesDTOList = new LinkedList<>();
		
		for (Zone zone : aux) {
			zonesDTOList.add(zone.toDTO());
		}
		
		return zonesDTOList;
	}
	
	public int addZone(String zoneName) throws ConnectionException, AccessException, InvalidZoneException {
		Zone newZone = new Zone(zoneName);
		newZone.save();
		updateObservers(newZone);
		return newZone.getId();
	}
	
	private void updateObservers(Zone z) {
		List<ZoneDTO> zoneToSend = new LinkedList<>();
		zoneToSend.add(z.toDTO());
		updateObservers(zoneToSend);
	}

}
