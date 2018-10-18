package backEnd;

import dto.ClientDTO;
import exceptions.InvalidClientException;

public class Client {
	
	private int id=0;
	private String cuit;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private Zone zone;
	private boolean activeClient;
	
	
	public Client(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidClientException {
		parameterChecker(cuit, name, address, phoneNumber, email, zone);
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
		this.cuit = cuit;
		activeClient = true;
	}
	
	public String getCuit() {
		return cuit;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public boolean isActiveClient() {
		return activeClient;
	}

	public void modify(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidClientException {
		
		parameterChecker(cuit, name, address, phoneNumber, email, zone);
		this.cuit = cuit;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
	}
	
	public boolean isActive() {
		return activeClient;
	}
	
	public void deactivateClient() {
		activeClient = false;
	}
	
	public Zone getZone() {
		return zone;
	}
	
	public ClientDTO toDTO(){
		return new ClientDTO(id, cuit, name, address, phoneNumber, email, zone.getName());
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void save() {
		
	}
	
	private void parameterChecker(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidClientException {
		if(name == null || name.length() < 1) {
			throw new InvalidClientException("Invalid name");
		} else if(address == null || address.length() < 1) {
			throw new InvalidClientException("Invalid address");
		} else if(phoneNumber == null || phoneNumber.length() < 1) {
			throw new InvalidClientException("Invalid phone number");
		} else if(email == null || email.length() < 1) {
			throw new InvalidClientException("Invalid email");
		} else if(zone == null) {
			throw new InvalidClientException("Invalid zone");
		} else if(cuit == null) {
			throw new InvalidClientException("Invalid cuit");
		}
	}

}
