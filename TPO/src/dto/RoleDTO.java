package dto;

import java.util.LinkedList;
import java.util.List;

public class RoleDTO {

	private String role;
	private List<Integer> usersId = new LinkedList<>();
	private int userId;
			
	public RoleDTO() {
		
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void addUserId(int userId) {
		usersId.add(Integer.valueOf(userId));
	}
	
	public List<Integer> getUsersId(){
		return usersId;
	}
	
	public String getRole() {
		return role;
	}

}
