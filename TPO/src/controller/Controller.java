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
import services.ClientService;
import services.InvoiceService;
import services.ProductService;
import services.RoleService;
import services.UserService;

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
	public int addClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException{
	
		return ClientService.getIntance().addClient(dto);
	}
	public void modifyClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException{
		
		ClientService.getIntance().modifyClient(dto);

	}
	public void removeClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException {
		
		ClientService.getIntance().removeClient(dto.getId());
	}
	//------------------------------------------------------------ END CLIENT ------------------------------------------------------------
	
	//------------------------------------------------------------ START USER ------------------------------------------------------------
	public int addUser(UserDTO dto) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		return UserService.getIntance().addUser(dto);
	}
	public void modifyUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		UserService.getIntance().modifyUser(dto);
	}
	public void removeUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		
		UserService.getIntance().removeUser(dto);
	}
	//------------------------------------------------------------ END USER ------------------------------------------------------------
	
	//------------------------------------------------------------ START PRODUCT ------------------------------------------------------------
	public int addProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		return ProductService.getIntance().addProduct(dto);
	}
	public void modifyProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		ProductService.getIntance().modifyProduct(dto);		
	}
	public void removeProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		ProductService.getIntance().removeProduct(dto);	
	}
	//------------------------------------------------------------ END PRODUCT ------------------------------------------------------------
	
	//------------------------------------------------------------ START ROLE ------------------------------------------------------------
	public void addRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		RoleService.getIntance().addRole(dto);	
	}
	public void removeRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
	
		RoleService.getIntance().removeRole(dto);
	}
	//------------------------------------------------------------ END ROLE ------------------------------------------------------------
	
	//------------------------------------------------------------ START INVOICE ------------------------------------------------------------
	public int addInvoice(InvoiceDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidInvoiceException, InvalidProductException, InvalidZoneException {
		return InvoiceService.getIntance().addInvoice(dto);
	}
	public void removeInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException {
		InvoiceService.getIntance().removeInvoice(dto);
	}
	//------------------------------------------------------------ END INVOICE ------------------------------------------------------------
	
	public String getClaimState(int claimNumber) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		Claim claim = new ClaimDAO().getClaim(claimNumber);
		return claim.getActualState().name();
	}
	public void treatClaim(TransitionDTO dto) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidTransitionException, InvalidUserException, InvalidRoleException, SQLException {
		Claim aux = new ClaimDAO().getClaim(dto.getClaimId());
		aux.treatClaim(new UserDAO().getUser(dto.getResponsableId()), State.valueOf(dto.getNewState()), dto.getDescription());
		
		
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
		for (Integer i : dto.getInidividualClaimsId()) {
			Claim claimAux = new ClaimDAO().getClaim(i.intValue());
			claim.addClaim(claimAux);
		}
		claim.save();
		return claim.getClaimId();
		
	}
}
