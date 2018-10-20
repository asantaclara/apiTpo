package test;

import java.nio.channels.ClosedByInterruptException;
import java.util.Date;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.Product;
import backEnd.Roles;
import backEnd.User;
import backEnd.Zone;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dao.RoleDAO;
import dao.SqlUtils;
import dao.UserDAO;
import dao.ZoneDAO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidRoleException, InvalidUserException {
//		ClientDAO test = new ClientDAO();
//		
//		Client aux = test.getClient(1);
//		
//		System.out.println(aux.getAddress());
//		
//		System.out.println(ZoneDAO.fixZone("San Cristobal"));
		
//		Client test1 = new Client("12-12346588-09", "Juan Pablo", "Rivadavia 4542", "15-54379876", "asdas@asdf.com", new Zone("Balvanera"));
//		test1.setId(2);
////		test1.modify();
//		
//		List<Client> aux = ClientDAO.getAllClients();
//		
//		System.out.println("Hola");
		
//		Product test = new Product("Agua", "Agua purificada", 1.5f);
//		
//		test.saveInDB();
//		
//		test.setProductId(1);
//		test.modify("Coca", "Coca Light", 1.6f);
//		test.modifyInDB();
//		
//		Product product1 = ProductDAO.getProduct(1);
//		List<Product> listProducts = ProductDAO.getAllProducts();
//		
//		System.out.println("hola");
		
//		List<Zone> zones = ZoneDAO.getAllZones();
//		
//		Zone zone = ZoneDAO.getZone(10);
//		System.out.println(zone.getName());
//		System.out.println(zone.getZoneId());
		
//		Zone zone = new Zone("Caballito");
//		zone.save();

//		Invoice invoice = new Invoice(ClientDAO.getClient(15), new Date());
//		invoice.save();
//		
//		invoice.deactivateInvoice();
//		invoice.modify();
//		
//		List<Invoice> invoices = InvoiceDAO.getAllInvoices();
//		
//		List<Invoice> invoices = InvoiceDAO.getAllInvoicesFromClient(3);
//		System.out.println(invoices.size());
		
//		Invoice invoice = InvoiceDAO.getInvoice(6);
//		System.out.println("Hola");
		
//		System.out.println(RoleDAO.idByRole(Roles.ADMINISTRATOR));
//		System.out.println(Roles.ADMINISTRATOR.toString());
		
//		User user = new User("Jimena", Roles.DISTRIBUTION_RESPONSABLE);
//		user.addRole(Roles.ADMINISTRATOR);
//		user.saveInDB();
//		
//		User user = UserDAO.getUser(1);
//		System.out.println(user.getName());
		
//		for (User u : UserDAO.getAllUserByRole(Roles.DISTRIBUTION_RESPONSABLE)) {
//			System.out.println(u.getName());
//		}
		
//		User user = new User("Pepe", Roles.INVOICING_RESPONSABLE);
//		user.deactivateUser();
//		user.modifyInDB();
		
		User user1 = new User("Pedro", Roles.ADMINISTRATOR);
		User user2 = new User("Pedro", Roles.ADMINISTRATOR);
		System.out.println(user1.equals(user2));
	}
}
