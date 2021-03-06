package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.InvoiceDAO;
import dto.InvoiceDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;

public class Invoice {
	
	private int invoiceId=0;
	private List<ProductItem> items = new LinkedList<>();
	private Client client;
	private Date date;
	private boolean activeInvoice;
	
	public Invoice(Client client, Date date) throws InvalidInvoiceException {
		if(client == null || date == null) {
			throw new InvalidInvoiceException("Invalid parameters");
		}
		
		this.client = client;
		this.date = date;
		activeInvoice = true;
	}
	
	public void addProductItem(Product product, int quantity) throws InvalidProductItemException {
		
		if(product == null || product.getProductId() == 0 || quantity <= 0) {
			throw new InvalidProductItemException("Invalid productItem");
		}
		
		items.add(new ProductItem(product, quantity));
	}
	
	public int getInvoiceId() {
		return invoiceId;
	}

	public List<ProductItem> getItems() {
		return items;
	}

	public Client getClient() {
		return client;
	}

	public Date getDate() {
		return date;
	}

	public int getId() {
		return invoiceId;
	}
	
	public void setId(int invoiceId) throws InvalidInvoiceException {
		if(invoiceId == 0) {
			throw new InvalidInvoiceException("Invalid invoiceId");
		}
		this.invoiceId = invoiceId;
	}
	
	public boolean isActive() {
		return activeInvoice;
	}
	
	public void deactivateInvoice() throws ConnectionException, AccessException, InvalidInvoiceException {
		activeInvoice = false;
		new InvoiceDAO().modify(this);
	}
	
	public boolean validateClient(Client client) throws InvalidClientException { //Si la factura es del cliente que me mandan devuelvo true
		if(client == null) {
			throw new InvalidClientException("Invalid client");
		}
		return (this.client.getId() == client.getId()); 
	}
	
	public boolean validateProductItem(Product product, int quantity) throws InvalidProductItemException {
		if(product == null | product.getProductId() == 0) {
			throw new InvalidProductItemException("Invalid productItem");
		}
		for (ProductItem p : items) {
			if (p.getProduct().getProductId() == product.getProductId()) {
				return true;
			}
		}
		return false;
		
		//Con este metodo me fijo si el producto que me mandan esta en la factura.
	}
	
	public boolean validateDate(Date date) {
		return (this.date == date); //Hay que probar que las fechas coincidan.
	}
	
	public InvoiceDTO toDTO() {
		
		InvoiceDTO aux = new InvoiceDTO();
		
		aux.setClientId(client.getId());
		aux.setInvoiceId(invoiceId);
		aux.setDate(date);
		
		for (ProductItem p : items) {
			aux.addProductItemDTO(p.getProduct().toDTO(), p.getQuantity());
		}
		
		return aux;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidInvoiceException, InvalidProductException {
		new InvoiceDAO().save(this);
		
	}

	public void modify() throws ConnectionException, AccessException, InvalidInvoiceException {
		new InvoiceDAO().modify(this);
	}
	
}
