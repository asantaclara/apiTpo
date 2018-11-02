package services;

import backEnd.Roles;
import backEnd.User;
import dao.UserDAO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class UserService {

	private static UserService  instance = null;
	
	public static UserService getIntance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	private UserService() {
		
	}
	
	public int addUser(UserDTO dto) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		User u = new User(dto.getName(),Roles.valueOf(dto.getPrincipalRole()));
		u.saveInDB();
		return u.getId();
	}
	
	public void modifyUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		User existingUser = new UserDAO().getUser(dto.getUserId());
		
		if (existingUser != null) {
			existingUser.modify(dto.getName(), Roles.valueOf(dto.getPrincipalRole()));
		}
	}

	public void removeUser(UserDTO dto) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		User userToRemove = new UserDAO().getUser(dto.getUserId());
		
		userToRemove.deactivateUser(); //Aca desactivo al usuario para que no se pueda usar mas en el programa.
	}
}
