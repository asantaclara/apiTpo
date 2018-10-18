package backEnd;

import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class User {

	
	private int userId=0;
	private String name;
	private Roles principalRole;
	private Roles secondaryRole;
	private boolean activeUser;
	
	public User(String name, Roles principalRole) {
		this.name = name;
		this.principalRole = principalRole;
		this.secondaryRole = principalRole;
		this.activeUser = true;
	}
	
	public void modifyUser(String name, Roles principalRole) {
		
		if (name != null) {			
			this.name = name;
		}
		if (principalRole != null) {			
			this.principalRole = principalRole;
			this.secondaryRole = principalRole;
		}
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void addRole(Roles role) {
		secondaryRole = role;
	}
	
	public void removeRole() {
		secondaryRole = principalRole;
	}
	
	public Roles actualRole() {
		return secondaryRole;
	}
	
	public UserDTO toDTO() {
		return new UserDTO(userId, name, principalRole.name(), secondaryRole.name());
	}
	
	public boolean isActive() {
		return activeUser;
	}
	
	public void deactivateUser() {
		activeUser = false;
	}
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}
}
