package dto;

public class ClientDTO {

	private int id;
	private String cuit;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String zone;
	
	
	public ClientDTO() {

	}

	
	public int getId() {
		return id;
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


	public void setId(int id) {
		this.id = id;
	}


	public void setCuit(String cuit) {
		this.cuit = cuit;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}
	
	
}
