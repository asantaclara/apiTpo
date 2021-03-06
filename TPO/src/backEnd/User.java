package backEnd;

import dao.UserDAO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class User {

	
	private int id = 0;
	private String name;
	private Roles principalRole;
	private Roles secondaryRole;
	private boolean activeUser;
	private String userName;
	private String password;
	
	public User(String name, Roles principalRole, String userName, String password) throws InvalidUserException {
		if(name == null || name.length() == 0) {
			throw new InvalidUserException("Invalid name");
		}
		if(principalRole == null) {
			throw new InvalidUserException("Invalid role");
		}
		if (userName == null || userName.length() == 0) {
			throw new InvalidUserException("Invalid username");
		}
		if (password == null || password.length() == 0) {
			throw new InvalidUserException("Invalid password");
		}
		this.name = name;
		this.principalRole = principalRole;
		this.secondaryRole = principalRole;
		this.activeUser = true;
		this.userName = userName;
		this.password = password;
	}
	
	public void modify(String name, Roles principalRole, String userName, String password) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		
		if (name != null) {		
			if(name.length() == 0) {
				throw new InvalidUserException("Invalid name");
			}
			this.name = name;
		}
		if (principalRole != null) {
			this.principalRole = principalRole;
			this.secondaryRole = principalRole;
		}
		if (userName != null) {
			if (userName.length() == 0) {
				throw new InvalidUserException("Invalid username");
			}
			this.userName = userName;
		}
		if (password != null) {
			if (password.length() == 0) {
				throw new InvalidUserException("Invalid password");
			}
			this.password = password;
		}
		
		new UserDAO().modify(this);
	}
	
	public void setId(int userId) throws InvalidUserException {
		if (userId == 0) {
			throw new InvalidUserException("Invalid userId");
		}
		this.id = userId;
	}
	
	public void addRole(Roles role) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		if(role == null) {
			throw new InvalidUserException("Invalid role");
		}
		secondaryRole = role;
	}
	
	public void removeRole() {
		secondaryRole = principalRole;
	}
	
	public void deactivateUser() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		activeUser = false;
		new UserDAO().modify(this);
	}
	
	public void save() throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		new UserDAO().save(this);
	}
	
	public void modify() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		new UserDAO().modify(this);
	}
	
	public UserDTO toDTO() {
		
		UserDTO aux = new UserDTO();
		
		aux.setUserId(id);
		aux.setName(name);
		aux.setPrincipalRole(principalRole.name());
		aux.setSecondaryRole(secondaryRole.name());
		aux.setUserName(userName);
		//No devuelvo la password en el DTO
		
		return aux;
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
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
//--------------------------------------------------------------------GETERS END-------------------------------------------------------------------------------------
}
