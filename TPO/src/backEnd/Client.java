package backEnd;

import dto.ClientDTO;
import exceptions.InvalidAddressException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPhoneNumberException;
import exceptions.InvalidZoneException;

public class Client {
	
	private int clientId;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private Zone zone;
	
	
	public Client(String name, String address, String phoneNumber, String email, Zone zone) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException {
		parameterChecker(name, address, phoneNumber, email, zone);
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
		this.clientId = 0;
	}
	
	public void setId(int clientId) {
		this.clientId = clientId;
	}
	
	public int getId() {
		return clientId;
	}
	
	public void modify(String name, String address, String phoneNumber, String email, Zone zone) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException {
		
		parameterChecker(name, address, phoneNumber, email, zone);
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
	}
	
	public Zone getZone() {
		return zone;
	}
	
	public ClientDTO toDTO(){
		return new ClientDTO(clientId, name, address, phoneNumber, email, zone.getZoneId());
	}
	
	private void parameterChecker(String name, String address, String phoneNumber, String email, Zone zone) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException {
		if(name == null || name.length() < 1) {
			throw new InvalidNameException();
		} else if(address == null || address.length() < 1) {
			throw new InvalidAddressException();
		} else if(phoneNumber == null || phoneNumber.length() < 1) {
			throw new InvalidPhoneNumberException();
		} else if(email == null || email.length() < 1) {
			throw new InvalidEmailException();
		} else if(zone == null) {
			throw new InvalidZoneException();
		}
	}

}
