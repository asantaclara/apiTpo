package services;

import java.util.Date;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.Product;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dto.InvoiceDTO;
import dto.ProductItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidZoneException;

public class InvoiceService {
	private static InvoiceService  instance = null;
	
	public static InvoiceService getIntance() {
		if (instance == null) {
			instance = new InvoiceService();
		}
		return instance;
	}
	
	private InvoiceService() {
		
	}
	
	public int addInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidProductException, InvalidClientException, InvalidZoneException {
		List<ProductItemDTO> itemsDTO = dto.getProductItems(); //Esta lista de ProductItemDTO la tengo para despues traerme los product de la BD.
		int clientId = dto.getClientId(); //Este es el id que uso para traerme al cliente de la BD.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		Invoice newInvoice = new Invoice(existingClient, dto.getDate());
		
		for (ProductItemDTO productItemDTO : itemsDTO) {
			int productId = productItemDTO.getProductId();
			Product existingProduct = new ProductDAO().getProduct(productId);
			
			int productQuantity = productItemDTO.getQuantity();
			
			newInvoice.addProductItem(existingProduct, productQuantity);
		}		
		
		newInvoice.save();
		return newInvoice.getId();
	}
	public void removeInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException {
		new InvoiceDAO().getInvoice(dto.getInvoiceId()).deactivateInvoice();
	}
	

}