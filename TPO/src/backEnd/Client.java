package backEnd;

import dao.ClientDAO;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;

public class Client {
	
	private int id = 0;
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
			if(!(!cuit.matches("\\d{2}-\\d{8}-\\d{1}"))) {
				this.cuit = cuit;				
			} else {
				throw new InvalidClientException("Invalid cuit");
			}
		}
		if(name != null) {	
			if (!(name.length() < 1 || !name.matches("[a-zA-Zñ ]+$"))) {
				this.name = name;
			} else {
				throw new InvalidClientException("Invalid name");
			}
		}
		if(address != null) {	
			String[] addressAux = address.split(" ");
			if(!(address.length() < 1 || addressAux.length < 2|| !address.matches("[a-zA-Z0-9ñ ]+$") || !addressAux[addressAux.length-1].matches("[0-9]+$"))) {				
				this.address = address;
			} else {
				throw new InvalidClientException("Invalid address");
			}
		}
		if(phoneNumber != null && phoneNumber.length() > 0) {	
			if(!(phoneNumber.length() < 1 || !phoneNumber.matches("\\d{4}-\\d{4}"))) {				
				this.phoneNumber = phoneNumber;
			} else {
				throw new InvalidClientException("Invalid phone number");
			}
		}
		if(email != null) {	
			if(!(email.length() < 1 || !email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))) {				
				this.email = email;
			} else {
				throw new InvalidClientException("Invalid email");	
			}
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
	
	public void save() throws ConnectionException, AccessException, InvalidClientException {
		new ClientDAO().save(this);
	}
	
	public void modify() throws ConnectionException, AccessException, InvalidClientException {
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
		String[] addressAux = address.split(" ");
		
		if(name == null || name.length() < 1 || !name.matches("[a-zA-Zñ ]+$")) {
			throw new InvalidClientException("Invalid name");
		} else if(address == null || addressAux.length < 2 || address.length() < 1 || !address.matches("[a-zA-Z0-9ñ ]+$") || !addressAux[addressAux.length-1].matches("[0-9]+$")) {
			throw new InvalidClientException("Invalid address");
		} else if(phoneNumber == null || phoneNumber.length() < 1 || !phoneNumber.matches("\\d{4}-\\d{4}")) {
			throw new InvalidClientException("Invalid phone number");
		} else if(email == null || email.length() < 1 || !email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
			throw new InvalidClientException("Invalid email");
		} else if(zone == null) {
			throw new InvalidClientException("Invalid zone");
		} else if(cuit == null || !cuit.matches("\\d{2}-\\d{8}-\\d{1}")) {
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
