package dto;

public class ClientDTO {

	private int id;
	private String cuit;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String zone;
	
	
	public ClientDTO(int id, String cuit, String name, String address, String phoneNumber, String email, String zone) {
		
		this.id = id;
		this.cuit = cuit;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.zone = zone;
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
	public String getZone() {
		return zone;
	}
	public int getId() {
		return id;
	}
	
}
