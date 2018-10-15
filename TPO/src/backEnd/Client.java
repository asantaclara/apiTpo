package backEnd;

import dto.ClientDTO;
import exceptions.InvalidAddressException;
import exceptions.InvalidCuitException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPhoneNumberException;
import exceptions.InvalidZoneException;

public class Client {
	
	private String cuit;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private Zone zone;
	
	
	public Client(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException, InvalidCuitException {
		parameterChecker(cuit, name, address, phoneNumber, email, zone);
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
		this.cuit = cuit;
	}
	
	public String getCuit() {
		return cuit;
	}
	
	public void modify(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException, InvalidCuitException {
		
		parameterChecker(cuit, name, address, phoneNumber, email, zone);
		this.cuit = cuit;
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
		return new ClientDTO(cuit, name, address, phoneNumber, email, zone.getZoneId());
	}
	
	public void save() {
		
	}
	
	private void parameterChecker(String cuit, String name, String address, String phoneNumber, String email, Zone zone) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException, InvalidCuitException {
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
		} else if(cuit == null) {
			throw new InvalidCuitException();
		}
	}

}
