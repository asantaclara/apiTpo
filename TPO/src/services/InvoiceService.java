package services;

import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.Product;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dto.InvoiceDTO;
import dto.ProductDTO;
import dto.ProductItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;
import observer.Observable;

public class InvoiceService extends Observable{
	private static InvoiceService  instance = null;
	
	public static InvoiceService getIntance() {
		if (instance == null) {
			instance = new InvoiceService();
		}
		return instance;
	}
	
	private InvoiceService() {
		
	}
	
	public int addInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidProductException, InvalidClientException, InvalidZoneException, InvalidProductItemException {
		List<ProductItemDTO> itemsDTO = dto.getProductItems(); //Esta lista de ProductItemDTO la tengo para despues traerme los product de la BD.
		int clientId = dto.getClientId(); //Este es el id que uso para traerme al cliente de la BD.
		Client existingClient =  ClientService.getIntance().getClientById(clientId);
		
		Invoice newInvoice = new Invoice(existingClient, dto.getDate());
		
		for (ProductItemDTO productItemDTO : itemsDTO) {
			ProductDTO product = productItemDTO.getProduct();
			Product existingProduct = ProductService.getIntance().getProductById(product.getProductId());
			
			int productQuantity = productItemDTO.getQuantity();
			
			newInvoice.addProductItem(existingProduct, productQuantity);
		}		
		
		newInvoice.save();
		updateObservers();
		return newInvoice.getId();
	}
	public void removeInvoice(InvoiceDTO dto) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		Invoice existingInvoice = new InvoiceDAO().getInvoice(dto.getInvoiceId());
		existingInvoice.deactivateInvoice();
		updateObservers();
	}

	public List<InvoiceDTO> getInvoicesByClient(int clientId) throws ConnectionException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidInvoiceException, InvalidProductItemException {
		List<Invoice> invoices = new InvoiceDAO().getAllInvoicesFromClient(clientId);
		List<InvoiceDTO> aux = new LinkedList<>();
		
		for (Invoice invoice : invoices) {
			aux.add(invoice.toDTO());
		}
		return aux;
		
	}
	public Invoice getInvoiceById(int invoiceId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidProductItemException {
		return new InvoiceDAO().getInvoice(invoiceId);
	}
}
