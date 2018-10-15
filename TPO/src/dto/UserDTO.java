package dto;

public class UserDTO {

	private int userId;
	private String name;
	private String principalRole;
	private String secondaryRole;
	
	public UserDTO(int userId, String name, String principalRole, String secondaryRole) {
		this.userId = userId;
		this.name = name;
		this.principalRole = principalRole;
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
