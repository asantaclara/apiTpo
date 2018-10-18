package test;

import backEnd.Client;
import dao.ClientDao;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException {
		ClientDao test = new ClientDao();
		
		Client aux = test.getClient(1);
		
		System.out.println(aux.getAddress());
	}
}
