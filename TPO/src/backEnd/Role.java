package backEnd;

import java.util.LinkedList;
import java.util.List;

import dto.RoleDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class Role {

	private int roleId = 0;
	private Roles description;
	private List<User> users;
	
	public Role(Roles description) {
		this.description = description;
		users = new LinkedList<>();
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
	public RoleDTO toDTO() {
		return new RoleDTO(description.name(), -1);
		//Chequear que onda con este constructor
	}
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
