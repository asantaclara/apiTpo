package controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
import dto.ClaimDTO;
import dto.ClientDTO;
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
	public int addClient(ClientDTO dto) throws InvalidClientException{
	
		Client newClient = new Client(dto.getCuit(), dto.getName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), new Zone(dto.getZone()));
		newClient.save();
		
		return newClient.getId();
	}
	public void modifyClient(ClientDTO dto) throws InvalidClientException{
		
		int clientId = dto.getId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		if (existingClient != null) {
			existingClient.modify("cuit", "name", "address", "phoneNumber", "email", new Zone("zone"));
		}
		
	}
	public void removeClient(ClientDTO dto) throws InvalidClientException {
		
		int clientId = dto.getId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		existingClient.deactivateClient();
		
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
	
	//------------------------------------------------------------ START PRODUCT ------------------------------------------------------------
	public int addProduct(ProductDTO dto) throws ConnectionException, AccessException {
		Product newProduct = new Product(dto.getTitle(), dto.getDescription(), dto.getPrice());
		newProduct.save();
		
		return newProduct.getProductId();
	}
	public void modifyProduct(ProductDTO dto) {
		int productId = dto.getProductId(); //Este es el id que voy a usar para obtener el producto de la BD.
		Product existingProduct = new Product("title", "description", 0); //Este es el producto que sacaria de la BD.
		
		if(existingProduct != null) {
			existingProduct.modify(dto.getTitle(), dto.getDescription(), dto.getPrice());
		}
			
	}
	public void removeProduct(ProductDTO dto) {
		int productId = dto.getProductId(); //Con este id me traigo el product de la DB.
		Product productToRemove = new Product("title", "description", 0); // Este es el product que me estaria trayendo de la base de datos con el userId.
		
		productToRemove.deactivateProduct(); //Aca desactivo al product para que no se pueda usar mas en el programa.
		
	}
	//------------------------------------------------------------ END PRODUCT ------------------------------------------------------------
	
	//------------------------------------------------------------ START ROLE ------------------------------------------------------------
	public void addRole(RoleDTO dto) {
		int userId = dto.getUserId(); //Con este userId agarro el user de la base de datos.
		User existingUser =  new User("Test", Roles.ADMINISTRATOR); //Este es el user que voy a agarrar de la base de datos.
		
		if(existingUser != null) {
			
			Roles existingRole = Roles.valueOf(dto.getRole()); //Con este role lo voy a buscar a la BD
			Role existingRoleObjeto = new Role(existingRole, new LinkedList<>()); //Y me traigo este Role.
			
			existingUser.addRole(existingRole);
			existingRoleObjeto.addUser(existingUser);
			
		}
		
	}
	public void removeRole(RoleDTO dto) {
	
		int userId = dto.getUserId(); //Con este userId agarro el user de la base de datos.
		User existingUser =  new User("Test", Roles.ADMINISTRATOR); //Este es el user que voy a agarrar de la base de datos.
		
		Roles existingRole = Roles.valueOf(dto.getRole()); //Con este role lo voy a buscar a la BD
		Role existingRoleObjeto = new Role(existingRole, new LinkedList<>()); //Y me traigo este Role.
		
		if(existingRole != null && existingRoleObjeto != null) {
			existingUser.removeRole();
			existingRoleObjeto.removeUser(existingUser);
		}
	}
	//------------------------------------------------------------ END ROLE ------------------------------------------------------------
	
	//------------------------------------------------------------ START INVOICE ------------------------------------------------------------
	public int addInvoice(InvoiceDTO dto) throws InvalidClientException, ConnectionException, AccessException {
		List<ProductItemDTO> itemsDTO = dto.getProductItems(); //Esta lista de ProductItemDTO la tengo para despues traerme los product de la BD.
		int clientId = dto.getClientId(); //Este es el id que uso para traerme al cliente de la BD.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		Invoice newInvoice = new Invoice(existingClient);
		
		for (ProductItemDTO productItemDTO : itemsDTO) {
			int productId = productItemDTO.getProductId();
			Product existingProduct = new Product("title", "description", 0); //Este es el producto que sacaria de la BD.
			
			int productQuantity = productItemDTO.getQuantity();
			
			newInvoice.addProductItem(existingProduct, productQuantity);
		}
		
		newInvoice.save();
		
		return newInvoice.getId();
		
	}
	public void removeInvoice(InvoiceDTO dto) {
		int inoviceId = dto.getInvoiceId();
		Invoice existingInvoice = new Invoice(null); // Aca traigo la Invoice de la BD con el idInvoice de arriba.
		
		existingInvoice.deactivateInvoice();
	}
	//------------------------------------------------------------ END INVOICE ------------------------------------------------------------
	
	public String getClaimState(int claimNumber) throws InvalidClaimException {
		//Me voy contra la BD de composite claims y me traigo la compositeClaim que corresponde con el claimNumber, me puede venir llena o null
		CompositeClaim compositeClaim = null;
		if(compositeClaim != null) {
			return compositeClaim.getActualState().name(); //Si no es null la compositeClaim directamente le pido el estado a ella.
		} else {
			List<UserBoard> boards = new LinkedList<>(); //Esta lista esta compuesta por todos los diferentes boards del sistema.
			
			for (UserBoard userBoard : boards) {
				String claimStatus = userBoard.getClaimState(claimNumber).name();
				if (claimStatus.length() > 0) {
					return claimStatus;
				}
			}
			
		}
		throw new InvalidClaimException("Claim not found");
	}
	public void treatClaim(TransitionDTO dto) {
		int userId = dto.getUserId(); //Con este userId tengo que traer al user desde la BD y lo llamo existingUser.
		User existingUser =  new User("Test", Roles.ADMINISTRATOR);
		
		UserBoard existingUserBoard = new UserBoard(new Role(null, null), null); //Este userBoard lo tomo en base al existingUser.actualRole();
		
		existingUserBoard.treatClaim(dto.getClaimId(), existingUser, State.valueOf(dto.getNewState()), dto.getDescription());
		
		
	}
	public int addWrongInvoicingClaim(WrongInvoicingClaimDTO dto) throws InvalidClaimException, InvalidClientException {
		
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		WrongInvoicingClaim newClaim = new WrongInvoicingClaim(existingClient, new Date(), dto.getDescription());
		
		List<InvoiceItemDTO> invoiceItems = dto.getInvoices();
		
		for (InvoiceItemDTO invoiceItemDTO : invoiceItems) {
			int invoiceId = invoiceItemDTO.getInvoiceId();
			Invoice existingInvoice = new Invoice(null); // Aca traigo la Invoice de la BD con el idInvoice de arriba.
			
			if(existingInvoice.validateClient(existingClient)) { //Si el cliente pertenece a la factura que me mandaron
				String inconsistency = invoiceItemDTO.getInconsistency();
				
				newClaim.addInovice(existingInvoice, inconsistency);
			}
		}
		
		UserBoard existingUserBoard = new UserBoard(new Role(null, null), null); //Este userBoard es el que guarda todas las WrongInvoicingClaims
		
		existingUserBoard.addClaim(newClaim);
		
		return newClaim.getClaimId();
		
		
	}
	public int addMoreQuantityClaim(MoreQuantityClaimDTO dto) throws InvalidClaimException, InvalidClientException {
		
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		List<ProductItemDTO> productItemsDTO = dto.getProducts();
		
		ClaimType claimType = ClaimType.valueOf(dto.getClaimType());
		
		int invoiceId = dto.getInvoiceId();
		Invoice existingInvoice = new Invoice(null); // Aca traigo la Invoice de la BD con el idInvoice de arriba.
		
		String description = dto.getDescription();
		
		MoreQuantityClaim newClaim = new MoreQuantityClaim(existingClient, new Date(), description, claimType, existingInvoice);
		
		for (ProductItemDTO productItemDTO : productItemsDTO) {
			int productId = productItemDTO.getProductId();
			Product existingProduct = new Product("title", "description", 0); //Este es el producto que sacaria de la BD.
			
			int quantity = productItemDTO.getQuantity();
			
			if(claimType == ClaimType.MISSING_PRODUCT) {		
				if(existingInvoice.validateClient(existingClient) != true)
					throw new InvalidClaimException("Invoice doesn't belong to the client");
				if(existingInvoice.validateProductItem(existingProduct, quantity) != true)
					throw new InvalidClaimException("ProductItem doesn't belong to the invoice");
			}
		
			newClaim.addProductItem(existingProduct, quantity);
		}
		
		UserBoard existingUserBoard = new UserBoard(new Role(null, null), null); //Este userBoard es el que guarda todas las MoreQuantityClaim
		
		existingUserBoard.addClaim(newClaim);
		
		return newClaim.getClaimId();
	}
	public int addIncompatibleZoneClaim(IncompatibleZoneClaimDTO dto) throws InvalidClientException, InvalidClaimException {
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new Client("cuit", "name", "address", "phoneNumber", "email", new Zone("zone")); //Este seria el client que me trae la BD
		
		if(existingClient != null) {
			String description = dto.getDescription();
			
			IncompatibleZoneClaim newClaim = new IncompatibleZoneClaim(existingClient, new Date(), description, existingClient.getZone());
		
			UserBoard existingUserBoard = new UserBoard(new Role(null, null), null); //Este userBoard es el que guarda todas las IncompatibleZoneClaim
		
			existingUserBoard.addClaim(newClaim);
		
			return newClaim.getClaimId();
		
		}
		throw new InvalidClientException("Client not found");
	}
	public int addCompositeClaim(List<ClaimDTO> listDTO) {
		return 1;
	}
}
