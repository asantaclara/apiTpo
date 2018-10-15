package dto;

import backEnd.Roles;

public class UserDTO {

	private int userId;
	private String name;
	private Roles principalRole;
	private Roles secondaryRole;
	
	public UserDTO(int userId, String name, Roles principalRole, Roles secondaryRole) {
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
	public Roles getPrincipalRole() {
		return principalRole;
	}
	public Roles getSecondaryRole() {
		return secondaryRole;
	}
	
	
}
