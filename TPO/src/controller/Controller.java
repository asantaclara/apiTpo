package controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Claim;
import backEnd.ClaimType;
import backEnd.Client;
import backEnd.CompositeClaim;
import backEnd.IncompatibleZoneClaim;
import backEnd.Invoice;
import backEnd.MoreQuantityClaim;
import backEnd.Product;
import backEnd.Role;
import backEnd.Roles;
import backEnd.State;
import backEnd.User;
import backEnd.UserBoard;
import backEnd.WrongInvoicingClaim;
import backEnd.Zone;
import dao.ClaimDAO;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dao.RoleDAO;
import dao.UserDAO;
import dao.ZoneDAO;
import dto.ClientDTO;
import dto.CompositeClaimDTO;
import dto.IncompatibleZoneClaimDTO;
import dto.InvoiceDTO;
import dto.InvoiceItemDTO;
import dto.MoreQuantityClaimDTO;
import dto.ProductDTO;
import dto.ProductItemDTO;
import dto.RoleDTO;
import dto.TransitionDTO;
import dto.UserDTO;
import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
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
	public int addClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException{
	
		Client newClient = new Client(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), new Zone(dto.getZone()));
		newClient.saveInDB();
		
		return newClient.getId();
	}
	public void modifyClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException{
		
		int clientId = dto.getId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		if (existingClient != null) {
			existingClient.modify(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(),new Zone(dto.getZone()) );
		}
		
	}
	public void removeClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException {
		
		int clientId = dto.getId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		existingClient.deactivateClient();
		
	}
	//------------------------------------------------------------ END CLIENT ------------------------------------------------------------
	
	//------------------------------------------------------------ START USER ------------------------------------------------------------
	public int addUser(UserDTO dto) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		
		User newUser = new User(dto.getName(), Roles.valueOf(dto.getPrincipalRole()));
		newUser.saveInDB();
		// Aca tengo que ver como chequear si no tengo otro usuario con los mismos datos
		
		return newUser.getId();
	}
	public void modifyUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		
		int userId = dto.getUserId(); //Con este userId tengo que traer al user desde la BD y lo llamo existingUser.
		User existingUser = new UserDAO().getUser(userId);
		
		if (existingUser != null) {
			existingUser.modify(dto.getName(), Roles.valueOf(dto.getPrincipalRole()));
		}
		
	}
	public void removeUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		
		int userId = dto.getUserId(); //Con este id me traigo el user de la DB.
		User userToRemove = new UserDAO().getUser(userId);
	
		List<Role> roles = new LinkedList<>(); //Aca me traigo todos los roles de la DB y me hago una lista que es la que estoy simulando aca.
		
		for (Role role : roles) {
			role.removeUser(userToRemove);
		}
		
		userToRemove.deactivateUser(); //Aca desactivo al usuario para que no se pueda usar mas en el programa.
		
	}
	//------------------------------------------------------------ END USER ------------------------------------------------------------
	
	//------------------------------------------------------------ START PRODUCT ------------------------------------------------------------
	public int addProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		Product newProduct = new Product(dto.getTitle(), dto.getDescription(), dto.getPrice());
		newProduct.saveInDB();
		
		return newProduct.getProductId();
	}
	public void modifyProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		int productId = dto.getProductId(); //Este es el id que voy a usar para obtener el producto de la BD.
		Product existingProduct = new ProductDAO().getProduct(productId);
		
		if(existingProduct != null) {
			existingProduct.modify(dto.getTitle(), dto.getDescription(), dto.getPrice());
		}
			
	}
	public void removeProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		int productId = dto.getProductId(); //Con este id me traigo el product de la DB.
		Product productToRemove = new ProductDAO().getProduct(productId);
		
		productToRemove.deactivateProduct(); //Aca desactivo al product para que no se pueda usar mas en el programa.
		
	}
	//------------------------------------------------------------ END PRODUCT ------------------------------------------------------------
	
	//------------------------------------------------------------ START ROLE ------------------------------------------------------------
	public void addRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		int userId = dto.getUserId(); //Con este userId agarro el user de la base de datos.
		User existingUser =  new UserDAO().getUser(userId);
		
		if(existingUser != null) {
	
			existingUser.addRole(Roles.valueOf(dto.getRole()));
			new RoleDAO().getRole(Roles.valueOf(dto.getRole())).addUser(existingUser);
			existingUser.modifyInDB();
		}
		
	}
	public void removeRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
	
		int userId = dto.getUserId(); //Con este userId agarro el user de la base de datos.
		User existingUser =  new UserDAO().getUser(userId);
		
		Roles existingRole = Roles.valueOf(dto.getRole()); //Con este role lo voy a buscar a la BD
		Role existingRoleObjeto = new RoleDAO().getRole(existingRole);
		
		if(existingRole != null && existingRoleObjeto != null) {
			existingUser.removeRole();
			existingRoleObjeto.removeUser(existingUser);
			existingUser.modifyInDB();
		}
	}
	//------------------------------------------------------------ END ROLE ------------------------------------------------------------
	
	//------------------------------------------------------------ START INVOICE ------------------------------------------------------------
	public int addInvoice(InvoiceDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidInvoiceException, InvalidProductException, InvalidZoneException {
		List<ProductItemDTO> itemsDTO = dto.getProductItems(); //Esta lista de ProductItemDTO la tengo para despues traerme los product de la BD.
		int clientId = dto.getClientId(); //Este es el id que uso para traerme al cliente de la BD.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		Invoice newInvoice = new Invoice(existingClient, new Date());
		
		for (ProductItemDTO productItemDTO : itemsDTO) {
			int productId = productItemDTO.getProductId();
			Product existingProduct = new ProductDAO().getProduct(productId);
			
			int productQuantity = productItemDTO.getQuantity();
			
			newInvoice.addProductItem(existingProduct, productQuantity);
		}
		
		newInvoice.save();
		
		return newInvoice.getId();
		
	}
	public void removeInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException {
		int invoiceId = dto.getInvoiceId();
		Invoice existingInvoice = new InvoiceDAO().getInvoice(invoiceId);
		
		existingInvoice.deactivateInvoice();
	}
	//------------------------------------------------------------ END INVOICE ------------------------------------------------------------
	
	public String getClaimState(int claimNumber) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		Claim claim = new ClaimDAO().getClaim(claimNumber);
		return claim.getActualState().name();
	}
	public void treatClaim(TransitionDTO dto) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidTransitionException, InvalidUserException, InvalidRoleException, SQLException {
		Claim aux = new ClaimDAO().getClaim(dto.getClaimId());
		aux.treatClaim(new UserDAO().getUser(dto.getUserId()), State.valueOf(dto.getNewState()), dto.getDescription());
		
		
	}
	public int addWrongInvoicingClaim(WrongInvoicingClaimDTO dto) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidInvoiceItemException {
		
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		WrongInvoicingClaim newClaim = new WrongInvoicingClaim(existingClient, new Date(), dto.getDescription());
		
		List<InvoiceItemDTO> invoiceItems = dto.getInvoices();
		
		for (InvoiceItemDTO invoiceItemDTO : invoiceItems) {
			int invoiceId = invoiceItemDTO.getInvoiceId();
			Invoice existingInvoice = new InvoiceDAO().getInvoice(invoiceId);
			
			if(existingInvoice.validateClient(existingClient)) { //Si el cliente pertenece a la factura que me mandaron
				String inconsistency = invoiceItemDTO.getInconsistency();
				newClaim.addInovice(existingInvoice, inconsistency);
			}else {
				throw new InvalidClaimException("Invoice doesn't belong to client");
			}
		}
		newClaim.save();
		
		return newClaim.getClaimId();
		
		
	}
	public int addMoreQuantityClaim(MoreQuantityClaimDTO dto) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		List<ProductItemDTO> productItemsDTO = dto.getProducts();
		
		ClaimType claimType = ClaimType.valueOf(dto.getClaimType());
		
		int invoiceId = dto.getInvoiceId();
		Invoice existingInvoice = new InvoiceDAO().getInvoice(invoiceId);
		
		String description = dto.getDescription();
		
		MoreQuantityClaim newClaim = new MoreQuantityClaim(existingClient, new Date(), description, claimType, existingInvoice);
		
		for (ProductItemDTO productItemDTO : productItemsDTO) {
			newClaim.addProductItem(new ProductDAO().getProduct(productItemDTO.getProductId()), productItemDTO.getQuantity());
		}
		
		newClaim.save();
		
		return newClaim.getClaimId();
	}
	public int addIncompatibleZoneClaim(IncompatibleZoneClaimDTO dto) throws InvalidClientException, InvalidClaimException, ConnectionException, AccessException, InvalidZoneException, SQLException {
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		if(existingClient != null) {
			String description = dto.getDescription();
			
			IncompatibleZoneClaim newClaim = new IncompatibleZoneClaim(existingClient, new Date(), description, existingClient.getZone());
			newClaim.save();
			return newClaim.getClaimId();
		
		}
		throw new InvalidClientException("Client not found");
	}
	public int addCompositeClaim(CompositeClaimDTO dto) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		CompositeClaim claim = new CompositeClaim(new ClientDAO().getClient(dto.getClientId()), dto.getDate(), dto.getDescription());
		for (Integer i : dto.getClaimsId()) {
			Claim claimAux = new ClaimDAO().getClaim(i.intValue());
			claim.addClaim(claimAux);
		}
		claim.save();
		return claim.getClaimId();
		
	}
}
