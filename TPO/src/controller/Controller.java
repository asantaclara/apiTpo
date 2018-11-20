package controller;

import java.sql.SQLException;
import java.util.List;

import dto.ClaimDTO;
import dto.ClientDTO;
import dto.CompositeClaimDTO;
import dto.IncompatibleZoneClaimDTO;
import dto.InvoiceDTO;
import dto.MoreQuantityClaimDTO;
import dto.ProductDTO;
import dto.RoleDTO;
import dto.TransitionDTO;
import dto.UserDTO;
import dto.WrongInvoicingClaimDTO;
import dto.ZoneDTO;
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
import services.ClaimService;
import services.ClientService;
import services.CompositeClaimService;
import services.IncompatibleZoneClaimService;
import services.InvoiceService;
import services.MoreQuantityClaimService;
import services.ProductService;
import services.UserService;
import services.WrongInvoicingClaimService;
import services.ZoneService;

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
	// cuit - name - address - phoneNumber - email - zone
		return ClientService.getIntance().addClient(dto);
	}
	public void modifyClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException{
	// id - los parametro que quiero modificar
		ClientService.getIntance().modifyClient(dto);

	}
	public void removeClient(ClientDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidZoneException {
	// id	
		ClientService.getIntance().removeClient(dto.getId());
	}
	//------------------------------------------------------------ END CLIENT ------------------------------------------------------------
	
	//------------------------------------------------------------ START USER ------------------------------------------------------------
	public int addUser(UserDTO dto) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
	// name - principalRole	
		return UserService.getIntance().addUser(dto);
		
	}
	public void modifyUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
	// id - lo que quiera cambiar
		UserService.getIntance().modifyUser(dto);
	}
	public void removeUser(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
	// id	
		UserService.getIntance().removeUser(dto);
	}
	//------------------------------------------------------------ END USER ------------------------------------------------------------
	
	//------------------------------------------------------------ START PRODUCT ------------------------------------------------------------
	public int addProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
	// title - description - price	
		return ProductService.getIntance().addProduct(dto);
	}
	public void modifyProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
	// id - lo que quiera cambiar	
		ProductService.getIntance().modifyProduct(dto);		
	}
	public void removeProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
	// id	
		ProductService.getIntance().removeProduct(dto);	
	}
	//------------------------------------------------------------ END PRODUCT ------------------------------------------------------------
	
	//------------------------------------------------------------ START ROLE ------------------------------------------------------------
	public void addRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
	// userId y role
		UserService.getIntance().addRoleToUser(dto);	
	}
	public void removeRole(RoleDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
	// userId
		UserService.getIntance().removeRoleToUser(dto);
	}
	//------------------------------------------------------------ END ROLE ------------------------------------------------------------
	
	//------------------------------------------------------------ START INVOICE ------------------------------------------------------------
	public int addInvoice(InvoiceDTO dto) throws InvalidClientException, ConnectionException, AccessException, InvalidInvoiceException, InvalidProductException, InvalidZoneException {
	// addProductItem(productId, quantity) / client / date
		return InvoiceService.getIntance().addInvoice(dto);
	}
	public void removeInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException {
	// invoiceId
		InvoiceService.getIntance().removeInvoice(dto);
	}
	//------------------------------------------------------------ END INVOICE ------------------------------------------------------------
	
	public String getClaimState(int claimNumber) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		// claimId
		return ClaimService.getIntance().getClaimState(claimNumber);
	}
	public void treatClaim(TransitionDTO dto) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidTransitionException, InvalidUserException, InvalidRoleException, SQLException {
	// responsable / newState / description / claimId
		ClaimService.getIntance().treatClaim(dto);	//Este metodo me esta devolviendo una List<ClaimDTO> con todas las claims que se vieron modificadas en el proceso.
	}
	public int addWrongInvoicingClaim(WrongInvoicingClaimDTO dto) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidInvoiceItemException {
	// client / description / list de invoiceitemDTO	
		return WrongInvoicingClaimService.getIntance().addWrongInvoicingClaim(dto);
	}
	public int addMoreQuantityClaim(MoreQuantityClaimDTO dto) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		return MoreQuantityClaimService.getIntance().addMoreQuantityClaim(dto);	
	}
	public int addIncompatibleZoneClaim(IncompatibleZoneClaimDTO dto) throws InvalidClientException, InvalidClaimException, ConnectionException, AccessException, InvalidZoneException, SQLException {
		return IncompatibleZoneClaimService.getIntance().addIncompatibleZoneClaim(dto);
	}
	public int addCompositeClaim(CompositeClaimDTO dto) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException {
		return CompositeClaimService.getIntance().addCompositeClaim(dto);	
	}
	public int addZone(String zoneName) throws ConnectionException, AccessException, InvalidZoneException {
		return ZoneService.getIntance().addZone(zoneName);
	}
	public void modifyZone(ZoneDTO dto) throws AccessException, InvalidZoneException, ConnectionException {
		ZoneService.getIntance().modifyZone(dto);
	}
	
	//------------------------------------------------------------ START GUI ------------------------------------------------------------
	
	public List<InvoiceDTO> getInvoicesByClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException {
		return InvoiceService.getIntance().getInvoicesByClient(clientId);
	}
	public boolean userExists(UserDTO dto) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		return UserService.getIntance().userExists(dto);
	}
	public List<ProductDTO> getInvoiceProducts(int invoiceId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidZoneException{
		return ProductService.getIntance().getInvoiceProducts(invoiceId);
	}
	public ClientDTO getClientById(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
		return ClientService.getIntance().getClientDTOById(clientId);
	}
	public boolean clientExists(int clientId) throws ConnectionException, AccessException, InvalidZoneException {
		return ClientService.getIntance().clientExists(clientId);
	}
	public List<ClaimDTO> getClaimsFromClient(int clientId) throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException{
		return ClaimService.getIntance().getClaimsFromClient(clientId);
	}
	public UserDTO getUserById(int userId) throws AccessException, InvalidRoleException, ConnectionException {
		return UserService.getIntance().getUserDTOById(userId);
	}
	public UserDTO getUserByUsernameAndPassword(UserDTO dto) throws AccessException, ConnectionException, InvalidRoleException {
		return UserService.getIntance().getUserByUsernameAndPassword(dto);
	}
	public List<ClientDTO> getAllClients() throws ConnectionException, AccessException, InvalidClientException{
		return ClientService.getIntance().getAllClients();
	}
	public List<ProductDTO> getAllProducts() throws ConnectionException, AccessException, InvalidProductException{
		return ProductService.getIntance().getAllProducts();
	}
	public List<ZoneDTO> getAllZones() throws ConnectionException, AccessException{
		return ZoneService.getIntance().getAllZones();
	}
	public ProductDTO getProductById(int productId) throws ConnectionException, AccessException, InvalidProductException{
		return ProductService.getIntance().getProductDTOById(productId);
	}
	
	//------------------------------
	// IncompatibleZoneClaim
	public List<IncompatibleZoneClaimDTO> getAllIncompatibleZoneClaimsDTO() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException{
		return IncompatibleZoneClaimService.getIntance().getAllIncompatibleZoneClaimsDTO();
	}
	// MoreQuantityClaim
	// WrongInvoicingClaim
	
	
}
