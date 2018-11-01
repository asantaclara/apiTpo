package dto;

public class RoleDTO {

	private String role;
	private int userId;
	
	public RoleDTO() {
		
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getRole() {
		return role;
	}
	public int getUserId() {
		return userId;
	}
}
