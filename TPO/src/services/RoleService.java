package services;

import backEnd.Roles;
import backEnd.User;
import dao.UserDAO;
import dto.RoleDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class RoleService {
	private static RoleService  instance = null;
	
	public static RoleService getIntance() {
		if (instance == null) {
			instance = new RoleService();
		}
		return instance;
	}
	
	private RoleService() {
		
	}
	
	public void addRole(RoleDTO dto) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		if(dto.getUserId() == 0 || dto.getRole() == null) {
			throw new InvalidRoleException("Missing parameters");
		}
		User existingUser =  new UserDAO().getUser(dto.getUserId());
		
		if(existingUser != null) {
	
			existingUser.addRole(Roles.valueOf(dto.getRole()));
			existingUser.modifyInDB();
		}
	}
	
	public void removeRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		if(dto.getUserId() == 0) {
			throw new InvalidRoleException("Missing userId");
		}
		
		User existingUser = new UserDAO().getUser(dto.getUserId());
		
		if(existingUser != null) {			
			existingUser.removeRole();
			existingUser.modifyInDB();
		}
	}
}
