package test;

import java.nio.channels.ClosedByInterruptException;
import java.util.List;

import backEnd.Client;
import backEnd.Product;
import backEnd.Zone;
import dao.ClientDAO;
import dao.ProductDAO;
import dao.SqlUtils;
import dao.ZoneDAO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException {
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
		
		List<Zone> zones = ZoneDAO.getAllZones();
		
		Zone zone = ZoneDAO.getZone(10);
		System.out.println(zone.getName());
		System.out.println(zone.getZoneId());
		
	}
}
