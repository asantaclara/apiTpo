package test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.Product;
import backEnd.Roles;
import controller.Controller;
import dao.ClientDAO;
import dao.ProductDAO;
import dto.InvoiceDTO;
import dto.ProductDTO;
import dto.ProductItemDTO;
import dto.RoleDTO;
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
//		UserDTO dtoAddUser = new UserDTO();
//		dtoAddUser.setName("Juan Ignacio");
//		dtoAddUser.setPrincipalRole(Roles.CALL_CENTER_RESPONSABLE.name());
//		c.addUser(dtoAddUser);

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
		InvoiceDTO dtoInvoice = new InvoiceDTO();
	// ProductItemDTO (lista de) / clienteId / Date / 
		dtoInvoice.addProductItemDTO(2, 14);
		dtoInvoice.addProductItemDTO(4, 9);
		dtoInvoice.addProductItemDTO(3, 5);
		dtoInvoice.setClientId(3);
		dtoInvoice.setDate(new Date());
		c.addInvoice(dtoInvoice);
	System.out.println("END");
	}
}
