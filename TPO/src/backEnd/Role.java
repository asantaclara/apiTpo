package backEnd;

import java.util.LinkedList;
import java.util.List;

import dto.RoleDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class Role {

	private int roleId = 0;
	private Roles description;
	private List<User> users = new LinkedList<>();
	
	public Role(Roles description) {
		this.description = description;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
	public RoleDTO toDTO() {
		
		RoleDTO aux = new RoleDTO();
		
		aux.setRole(description.name());
		
		for (User user : users) {
			aux.addUserId(user.getId());
		}
		return aux;
	}
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getRoleId() {
		return roleId;
	}

}
