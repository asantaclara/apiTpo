package dto;

import backEnd.Roles;

public class RoleDTO {

	private Roles role;
	private int userId;
	
	public RoleDTO(Roles role, int userId) {
		this.role = role;
		this.userId = userId;
	}
	
	public Roles getRole() {
		return role;
	}
	public int getUserId() {
		return userId;
	}
}
