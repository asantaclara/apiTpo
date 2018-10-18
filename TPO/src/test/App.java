package test;

import backEnd.Client;
import dao.ClientDAO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException {
		ClientDAO test = new ClientDAO();
		
		Client aux = test.getClient(1);
		
		System.out.println(aux.getAddress());
	}
}
