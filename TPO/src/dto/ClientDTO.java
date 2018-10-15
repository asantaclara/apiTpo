package dto;

public class ClientDTO {

	private String cuit;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private int zone;
	
	
	public ClientDTO(String cuit, String name, String address, String phoneNumber, String email, int zone) {
		this.cuit = cuit;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
	}
	
	public String getClientId() {
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
	public int getZone() {
		return zone;
	}
	
}
