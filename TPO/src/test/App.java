package test;

import backEnd.WrongInvoicingClaim;
import controller.Controller;
import dao.WrongInvoicingClaimDAO;
import dto.InvoiceItemDTO;
import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidRoleException, InvalidUserException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException {
		Controller c = Controller.getInstance();
		
//		c.addUser(new UserDTO(0, "Alejandro",Roles.ADMINISTRATOR.name(), null));
//		c.modifyUser(new UserDTO(1, "Martin Abba", Roles.QUERY_USER.name(),Roles.CALL_CENTER_RESPONSABLE.name()));
//		c.removeUser(new UserDTO(1, null, null, null));
//		c.addClient(new ClientDTO(0, "11-11111111-1", "Pedro Picapiedra", "Siempreviva 123", "15-1515-1515","pedro@picapiedra.net", "Puerto Madero"));
//		c.removeClient(new ClientDTO(6, "11-11111111-1", "Pedro Picapiedra", "Siempreviva 123", "15-1515-1515","pedro@picapiedra.net", "Puerto Madero")); 
//		c.modifyClient(new ClientDTO(5, "22-22222222-2", "Pedro Picapiedra", "Siempreviva 123", "15-1515-1515","pedro@picapiedra.net", "Puerto Madero"));
//		c.addProduct(new ProductDTO(0, "Manzana", "Manzana de Rio Negro", 45f));
//		c.removeProduct(new ProductDTO(7, "Manzana", "Manzana de Rio Negro", 45f));
//		c.modifyProduct(new ProductDTO(6, "Manzana", "Manzana de Rio Negro", 72f));
//		InvoiceDTO invoice = new InvoiceDTO(0, 3, new Date());
//		invoice.addProductItemDTO(8, 2);
//		invoice.addProductItemDTO(5, 2);
//		c.addInvoice(invoice);
//		Invoice inv = InvoiceDAO.getInvoice(12);
//		c.removeInvoice(new InvoiceDTO(12, 3, new Date()));
//		c.addRole(new RoleDTO(Roles.QUERY_USER.name(), 5));
//		c.removeRole(new RoleDTO(Roles.QUERY_USER.name(), 5));
//		c.addIncompatibleZoneClaim(new IncompatibleZoneClaimDTO(0, 3, "Hola", 0));
//		WrongInvoicingClaimDTO dto = new WrongInvoicingClaimDTO(0, 4, "Tengo MUCHO suenio");
//		dto.addInvoiceItemDTO(new InvoiceItemDTO(8, "chau"));
//		c.addWrongInvoicingClaim(dto);
		WrongInvoicingClaim invoice = WrongInvoicingClaimDAO.getWrongInvoicingClaim(26);

		System.out.println(invoice.getClaimId());
		
	}
}
