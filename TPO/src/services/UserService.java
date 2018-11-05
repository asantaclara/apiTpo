package services;

import java.sql.SQLException;

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
		if(dto.getName() != null && dto.getPrincipalRole() != null && dto.getUserName() != null && dto.getPassword() != null) {			
			User u = new User(dto.getName(),Roles.valueOf(dto.getPrincipalRole()), dto.getUserName(), dto.getPassword());
			u.saveInDB();
			int i = u.getId();
			System.out.println("Termino con el addUser en el servicio");
			return i;
		} else {
			throw new InvalidUserException("Parameters missing");
		}
	}
	
	public void modifyUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		User existingUser = new UserDAO().getUser(dto.getUserId());
		
		
		if (existingUser != null) {
			existingUser.modify(dto.getName(), (dto.getPrincipalRole() == null) ? null : Roles.valueOf(dto.getPrincipalRole()), dto.getUserName(), dto.getPassword());
		}
	}

	public void removeUser(UserDTO dto) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		User userToRemove = new UserDAO().getUser(dto.getUserId());
		
		userToRemove.deactivateUser(); //Aca desactivo al usuario para que no se pueda usar mas en el programa.
	}

	public boolean userExists(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		try {
			new UserDAO().getUser(dto.getUserId());			
		} catch (InvalidUserException e) {
			return false;
		}
		return true;
	}

	public UserDTO getUserById(int userId) throws ConnectionException, AccessException, InvalidRoleException {
		try {
			return new UserDAO().getUser(userId).toDTO();			
		} catch (InvalidUserException e) {
			System.out.println("User not found");
			return null;
		}
	}

	public UserDTO getUserByUsernameAndPassword(UserDTO dto) throws AccessException, ConnectionException, InvalidRoleException {
		try {
			User aux = new UserDAO().getUserByUsername(dto.getUserName());
			
			if(aux.getPassword().equals(dto.getPassword())) {
				return aux.toDTO();
			} else {
				return null;
			}
		} catch (InvalidUserException e) {
			System.out.println("User not found");
			return null;
		}
	}

}
