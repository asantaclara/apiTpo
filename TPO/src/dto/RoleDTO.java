package dto;

public class RoleDTO {

	private String role;
	private int userId;
	
	public RoleDTO(String role, int userId) {
		this.role = role;
		this.userId = userId;
	}
	
	public String getRole() {
		return role;
	}
	public int getUserId() {
		return userId;
	}
}
