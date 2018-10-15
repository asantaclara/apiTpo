package controller;

import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.Role;
import backEnd.Roles;
import backEnd.User;
import backEnd.Zone;
import dto.ClientDTO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidAddressException;
import exceptions.InvalidCuitException;
import exceptions.InvalidEmailException;
import exceptions.InvalidNameException;
import exceptions.InvalidPhoneNumberException;
import exceptions.InvalidZoneException;

public class Controller {
	
	private static Controller instance = null;
	 
	public static Controller getInstance() {
	
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	private Controller() {
		
	}
	//------------------------------------------------------------ START CLIENT ------------------------------------------------------------
	
	public int addClient(ClientDTO dto) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException, InvalidCuitException {
	
		Client newClient = new Client(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), new Zone(dto.getZone()));
		newClient.save();
		
		return newClient.getId();
	}
	public void modifyClient(ClientDTO dto) throws InvalidNameException, InvalidAddressException, InvalidPhoneNumberException, InvalidEmailException, InvalidZoneException, InvalidCuitException {
		
		int clientId = dto.getId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		if (existingClient != null) {
			existingClient.modify("cuit", "name", "address", "phoneNumber", "email", new Zone("zone"));
		}
		
	}
	
	
	
	//------------------------------------------------------------ END CLIENT ------------------------------------------------------------
	//------------------------------------------------------------ START USER ------------------------------------------------------------
	public int addUser(UserDTO dto) throws ConnectionException, AccessException {
		
		User newUser = new User(dto.getName(), Roles.valueOf(dto.getPrincipalRole()));
		newUser.save();
		// Aca tengo que ver como chequear si no tengo otro usuario con los mismos datos
		
		return newUser.getUserId();
	}
	
	public void modifyUser(UserDTO dto) {
		
		int userId = dto.getUserId(); //Con este userId tengo que traer al user desde la BD y lo llamo existingUser.
		User existingUser =  new User("Test", Roles.ADMINISTRATOR);
		
		if (existingUser != null) {
			existingUser.modifyUser(dto.getName(), Roles.valueOf(dto.getPrincipalRole()));
		}
		
	}
	
	public void removeUser(UserDTO dto) {
		
		int userId = dto.getUserId(); //Con este id me traigo el user de la DB.
		User userToRemove = new User("Test", Roles.ADMINISTRATOR); // Este es el usuario que me estaria trayendo de la base de datos con el userId.
		List<Role> roles = new LinkedList<>(); //Aca me traigo todos los roles de la DB y me hago una lista que es la que estoy simulando aca.
		
		for (Role role : roles) {
			role.removeUser(userToRemove);
		}
		
		userToRemove.deactivateUser(); //Aca desactivo al usuario para que no se pueda usar mas en el programa.
		
	}
	
	//------------------------------------------------------------ END USER ------------------------------------------------------------
	
	
	
	
}
