package dto;

public class UserDTO {

	private int userId;
	private String name;
	private String principalRole;
	private String secondaryRole;
	
	public UserDTO() {
	
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setPrincipalRole(String principalRole) {
		this.principalRole = principalRole;
	}


	public void setSecondaryRole(String secondaryRole) {
		this.secondaryRole = secondaryRole;
	}


	public int getUserId() {
		return userId;
	}
	public String getName() {
		return name;
	}
	public String getPrincipalRole() {
		return principalRole;
	}
	public String getSecondaryRole() {
		return secondaryRole;
	}
	
	
}
