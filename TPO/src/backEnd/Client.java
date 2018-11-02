package backEnd;

import dao.ClientDAO;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
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
	
	public void modify(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidClientException, ConnectionException, AccessException {
		
		if(cuit != null) {			
			this.cuit = cuit;
		}
		if(name != null) {			
			this.name = name;
		}
		if(address != null) {			
			this.address = address;
		}
		if(phoneNumber != null && phoneNumber.length() > 0) {			
			this.phoneNumber = phoneNumber;
		}
		if(email != null) {			
			this.email = email;
		}
		if(zone != null) {			
			this.zone = zone;	
		}
		new ClientDAO().modify(this);
		
	}
	
	public void deactivateClient() throws ConnectionException, AccessException, InvalidClientException {
		activeClient = false;	
		new ClientDAO().modify(this);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void saveInDB() throws ConnectionException, AccessException, InvalidClientException {
		new ClientDAO().save(this);
	}
	
	public void modifyInDB() throws ConnectionException, AccessException, InvalidClientException {
		new ClientDAO().modify(this);
	}
	
	public ClientDTO toDTO(){
		ClientDTO aux = new ClientDTO();
		aux.setAddress(address);
		aux.setCuit(cuit);
		aux.setEmail(email);
		aux.setId(id);
		aux.setName(name);
		aux.setPhoneNumber(phoneNumber);
		aux.setZone(zone.getName());
		
		return aux;
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

	//--------------------------------------------------------------------GETERS START-------------------------------------------------------------------------------------	
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

		public boolean isActive() {
			return activeClient;
		}
		
		public Zone getZone() {
			return zone;
		}
		
		public int getId() {
			return id;
		}
		
	//--------------------------------------------------------------------GETERS END-------------------------------------------------------------------------------------

}
