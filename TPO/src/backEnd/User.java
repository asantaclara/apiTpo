package backEnd;

import dao.UserDAO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class User {

	
	private int id=0;
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
	
	public void modify(String name, Roles principalRole) {
		
		if (name != null) {			
			this.name = name;
		}
		if (principalRole != null) {			
			this.principalRole = principalRole;
			this.secondaryRole = principalRole;
		}
	}
	
	public void setId(int userId) {
		this.id = userId;
	}
	
	public void addRole(Roles role) {
		secondaryRole = role;
	}
	
	public void removeRole() {
		secondaryRole = principalRole;
	}
	
	public void deactivateUser() {
		activeUser = false;
	}
	
	public void saveInDB() throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		UserDAO.save(this);
	}
	
	public void modifyInDB() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		UserDAO.modify(this);
	}
	
	public UserDTO toDTO() {
		return new UserDTO(id, name, principalRole.name(), secondaryRole.name());
	}
	
	//--------------------------------------------------------------------GETERS START-------------------------------------------------------------------------------------	
	
	public int getId() {
		return id;
	}
	
	public Roles actualRole() {
		return secondaryRole;
	}
	
	public boolean isActive() {
		return activeUser;
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

	public boolean isActiveUser() {
		return activeUser;
	}
	
//--------------------------------------------------------------------GETERS END-------------------------------------------------------------------------------------
}
