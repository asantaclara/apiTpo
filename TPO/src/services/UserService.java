package services;

import java.util.LinkedList;
import java.util.List;

import backEnd.Roles;
import backEnd.User;
import dao.UserDAO;
import dto.RoleDTO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;
import observer.Observable;

public class UserService extends Observable{

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
			u.save();
			updateObservers();
			return u.getId();
		} else {
			throw new InvalidUserException("Parameters missing");
		}
	}
	
	public void modifyUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		User existingUser = new UserDAO().getActiveUser(dto.getUserId());
		
		
		if (existingUser != null) {
			existingUser.modify(dto.getName(), (dto.getPrincipalRole() == null) ? null : Roles.valueOf(dto.getPrincipalRole()), dto.getUserName(), dto.getPassword());
			updateObservers();
		}
	}

	public void removeUser(UserDTO dto) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		User userToRemove = new UserDAO().getActiveUser(dto.getUserId());
		
		userToRemove.deactivateUser(); //Aca desactivo al usuario para que no se pueda usar mas en el programa.
		updateObservers();
	}

	public boolean userExists(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		try {
			new UserDAO().getActiveUser(dto.getUserId());			
		} catch (InvalidUserException e) {
			return false;
		}
		return true;
	}

	public UserDTO getUserDTOById(int userId) throws ConnectionException, AccessException, InvalidRoleException {
		try {
			return new UserDAO().getUser(userId).toDTO();			
		} catch (InvalidUserException e) {
			System.out.println("User not found");
			return null;
		}
	}

	public UserDTO getUserByUsernameAndPassword(UserDTO dto) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
			User aux = new UserDAO().getUserByUsername(dto.getUserName());
			
			if(aux.getPassword().equals(dto.getPassword())) {
				return aux.toDTO();
			} else {
				throw new InvalidUserException("Password doesn't match");
			}
		
	}
	
	public void addRoleToUser(RoleDTO dto) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		if(dto.getUserId() == 0 || dto.getRole() == null) {
			throw new InvalidRoleException("Missing parameters");
		}
		User existingUser =  new UserDAO().getActiveUser(dto.getUserId());
		
		if(existingUser != null) {
	
			existingUser.addRole(Roles.valueOf(dto.getRole()));
			existingUser.modify();
			updateObservers();
		}
	}
	
	public void removeRoleToUser(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		if(dto.getUserId() == 0) {
			throw new InvalidRoleException("Missing userId");
		}
		
		User existingUser = new UserDAO().getActiveUser(dto.getUserId());
		
		if(existingUser != null) {			
			existingUser.removeRole();
			existingUser.modify();
			updateObservers();
		}
	}

	public User getUserById(int userId) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		return new UserDAO().getUser(userId);
	}

	public List<UserDTO> getAllUsers() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		List<UserDTO> returnList = new LinkedList<>();
		
		for (User u : new UserDAO().getAllUsers()) {
			returnList.add(u.toDTO());
		}
		return returnList;
	}
}
