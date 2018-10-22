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
	
	public void modify(String name, Roles principalRole) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		
		if (name != null) {			
			this.name = name;
		}
		if (principalRole != null) {			
			this.principalRole = principalRole;
			this.secondaryRole = principalRole;
		}
		
		new UserDAO().modify(this);
	}
	
	public void setId(int userId) {
		this.id = userId;
	}
	
	public void addRole(Roles role) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		secondaryRole = role;
	}
	
	public void removeRole() {
		secondaryRole = principalRole;
	}
	
	public void deactivateUser() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		activeUser = false;
		new UserDAO().modify(this);
	}
	
	public void saveInDB() throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		new UserDAO().save(this);
	}
	
	public void modifyInDB() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		new UserDAO().modify(this);
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
