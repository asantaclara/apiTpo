package dto;

public class ClientDTO {

	private int clientId;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private int zone;
	
	
	public ClientDTO(int clientId, String name, String address, String phoneNumber, String email, int zone) {
		this.clientId = clientId;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
	}
	
	public int getClientId() {
		return clientId;
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
