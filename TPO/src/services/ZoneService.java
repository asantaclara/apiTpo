package services;

import java.util.LinkedList;
import java.util.List;

import backEnd.Zone;
import dao.ZoneDAO;
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

	public List<ZoneDTO> getAllZones() throws ConnectionException, AccessException, InvalidZoneException {
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
		updateObservers();
		return newZone.getId();
	}


	public void modifyZone(ZoneDTO dto) throws AccessException, InvalidZoneException, ConnectionException {
		Zone existingZone = new ZoneDAO().getZone(dto.getZoneId());
		existingZone.setName(dto.getName());
		existingZone.modify();
		updateObservers();
	}

	
}
