package controller;

import java.util.List;
import java.util.Map;

import backEnd.Claim;
import backEnd.Client;
import backEnd.Invoice;
import backEnd.Product;
import backEnd.Role;
import backEnd.Roles;
import backEnd.User;
import backEnd.UserBoard;
import backEnd.Zone;
import dto.UserDTO;

public class Controller {

	private Map<Integer, Zone> zones;
	private Map<Integer, Client> clients;
	private Map<Integer, Product> products;
	private Map<Integer, Invoice> invoices;
	private Map<Integer, User> users;
	private Map<Integer, Role> roles;
	private Map<Roles, UserBoard> userBoards;
	private List<Claim> compositeClaims;
	
	private static Controller instance = null;
	 
	public static Controller getInstance() {
	
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	private Controller() {
		
	}
	
	public int addUser(UserDTO dto) {
		
	}
	
}
