package test;

import java.sql.SQLException;

import backEnd.Roles;
import controller.Controller;
import dto.UserDTO;
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

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidRoleException, InvalidUserException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException, InvalidProductItemException, InvalidTransitionException, SQLException {
		Controller c = Controller.getInstance();
// test addClient
//		ClientDTO dtoAddClient = new ClientDTO();
//		dtoAddClient.setCuit("33-33333333-3");
//		dtoAddClient.setName("Jimena");
//		dtoAddClient.setAddress("Lima 123");
//		dtoAddClient.setPhoneNumber("1234-4234");
//		dtoAddClient.setEmail("hola@hola.com");
//		dtoAddClient.setZone("Saavedra");
//		c.addClient(dtoAddClient);
		
// test modifyClient
//		ClientDTO dtoModifyClient = new ClientDTO();
//		dtoModifyClient.setId(2);
//		dtoModifyClient.setCuit("33-33333333-3");
//		dtoModifyClient.setName("Jimena");
//		dtoModifyClient.setAddress("Lima 123");
//		dtoModifyClient.setPhoneNumber("1234-4234");
//		dtoModifyClient.setEmail("chau@chau.com");
//		dtoModifyClient.setZone("Saavedra");
//		c.modifyClient(dtoModifyClient);
		
// test removeClient
//		ClientDTO dtoRemoveClient = new ClientDTO();
//		dtoRemoveClient.setId(4);
//		c.removeClient(dtoRemoveClient);
		
// test addUser
		UserDTO dtoAddUser = new UserDTO();
		dtoAddUser.setName("Matias");
		dtoAddUser.setPrincipalRole(Roles.CALL_CENTER_RESPONSABLE.name());
		dtoAddUser.setUserName("Matias3");
		dtoAddUser.setPassword("123");
		c.addUser(dtoAddUser);

// test modifyUser
//		UserDTO dtoUser = new UserDTO();
//		dtoUser.setUserId(3);
//		dtoUser.setName("Juan Pedro");
//		dtoUser.setPrincipalRole(Roles.CALL_CENTER_RESPONSABLE.name());
//		c.modifyUser(dtoUser);
		
// test removeUser
//		UserDTO dtoUser = new UserDTO();
//		dtoUser.setUserId(5);
//		c.removeUser(dtoUser);

// test addProduct
//		ProductDTO dtoProduct = new ProductDTO();
//		dtoProduct.setTitle("Banana");
//		dtoProduct.setDescription("Importadas de Argentina");
//		dtoProduct.setPrice(32);
//		c.addProduct(dtoProduct);
		
// test modifyProduct
//		ProductDTO dtoProduct = new ProductDTO();
//		dtoProduct.setProductId(1);
//		dtoProduct.setTitle("Kiwi");
//		dtoProduct.setDescription("Importadas de Colombia");
//		dtoProduct.setPrice(12);
//		c.modifyProduct(dtoProduct);

// test removeProduct
//		ProductDTO dtoProduct = new ProductDTO();
//		dtoProduct.setProductId(1);
//		c.removeProduct(dtoProduct);
		
// test addRole
//		RoleDTO dtoRole = new RoleDTO();
//		dtoRole.setRole(Roles.ADMINISTRATOR.name());
//		dtoRole.setUserId(3);
//		c.addRole(dtoRole);

// test removeRole
//		RoleDTO dtoRole = new RoleDTO();
//		dtoRole.setUserId(3);
//		c.removeRole(dtoRole);
		
// test addInvoice
//		InvoiceDTO dtoInvoice = new InvoiceDTO();
//		dtoInvoice.addProductItemDTO(3, 14);
//		dtoInvoice.addProductItemDTO(2, 9);
//		dtoInvoice.setClientId(2);
//		dtoInvoice.setDate(new Date());
//		c.addInvoice(dtoInvoice);
//		
// test removeInvoice
//		InvoiceDTO dtoInvoice = new InvoiceDTO();
//		dtoInvoice.setInvoiceId(1);
//		c.removeInvoice(dtoInvoice);
		
// test addWrongInvoicingClaim
//		WrongInvoicingClaimDTO dtoWrong = new WrongInvoicingClaimDTO();
//		dtoWrong.setClientId(3);
//		dtoWrong.setDescription("No me gusta nada");
//		dtoWrong.addInvoiceItemDTO(2, "No me gusta");
//		c.addWrongInvoicingClaim(dtoWrong);
		
// test treatClaim
//		TransitionDTO dtoTransition = new TransitionDTO();
//		dtoTransition.setResponsableId(1);
//		dtoTransition.setNewState(State.IN_TREATMENT.name());
//		dtoTransition.setDescription("Estoy haciendo una prueba");
//		dtoTransition.setClaimId(8);
//		c.treatClaim(dtoTransition);
		
// test getClaimState
//		System.out.println(c.getClaimState(1));

// test getInvoicesByClient
//		for (InvoiceDTO i : c.getInvoicesByClient(95)) {
//			System.out.println(i.getClientId());
//		}
	
// test userExists
//		UserDTO dto = new UserDTO();
//		dto.setUserId(12);
//		System.out.println(c.userExists(dto));
	
// test getInvoiceProducts
//		for (ProductDTO p : c.getInvoiceProducts(2)) {
//			System.out.println(p.getTitle());
//			System.out.println(p.getProductId());
//			System.out.println(p.getDescription());
//			System.out.println("");
//		}

// test getClientById
//		System.out.println(c.getClientById(9));
		
// test userExists
//		UserDTO dto1 = new UserDTO();
//		dto1.setUserId(1);
//		System.out.println(c.userExists(dto1));
//		UserDTO dto2 = new UserDTO();
//		dto2.setUserId(9);
//		System.out.println(c.userExists(dto2));
	
// test getClaimsFromClient
//		for (ClaimDTO claim : c.getClaimsFromClient(2)) {
//			System.out.println(claim.getDescription());
//		}
		
// test getUserById
//		UserDTO aux = c.getUserById(6);
//		if (aux != null) {
//			System.out.println(aux.getName());
//		} else {
//			System.out.println("Null return");
//		}
		
// test addMoreQuantityClaim
//		MoreQuantityClaimDTO dto = new MoreQuantityClaimDTO();
//		dto.setClientId(2);
//		dto.addProductItemDTO(3, 4);
//		dto.addProductItemDTO(2, 5);
//		dto.setClaimType(ClaimType.MORE_QUANTITY.name());
//		dto.setInvoiceId(3);
//		dto.setDescription("Necesito mas");
//		c.addMoreQuantityClaim(dto);
		
		
		
	System.out.println("END");
	}
}
