package test;

import backEnd.Client;
import backEnd.Zone;
import dao.ClientDAO;
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
		
		Client test1 = new Client("12-12346588-09", "Juan Pablo", "Rivadavia 4542", "15-54379876", "asdas@asdf.com", new Zone("Balvanera"));
		test1.modify();
	
	}
}
