package backEnd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.InvoiceDAO;
import dto.InvoiceDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidInvoiceException;

public class Invoice {
	
	private int invoiceId=0;
	private List<ProductItem> items;
	private Client client;
	private Date date;
	private boolean activeInvoice;
	
	public Invoice(Client client, Date date) {
		this.client = client;
		this.date = date;
		activeInvoice = true;
	}
	
	public void addProductItem(Product product, int quantity) {
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

	public boolean isActiveInvoice() {
		return activeInvoice;
	}

	public int getId() {
		return invoiceId;
	}
	
	public void setId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public boolean isActive() {
		return activeInvoice;
	}
	
	public void deactivateInvoice() {
		activeInvoice = false;
	}
	
	public boolean validateClient(Client client) {
		return (this.client == client); //Si la factura es del cliente que me mandan devuelvo true
	}
	
	public boolean validateProductItem(Product product, int quantity) {
		return items.contains(new ProductItem(product, quantity)); //Hay que probar que esto funcione bien
	}
	
	public boolean validateDate(Date date) {
		return (this.date == date); //Hay que probar que las fechas coincidan.
	}
	
	public InvoiceDTO toDTO() {
		InvoiceDTO aux = new InvoiceDTO(invoiceId, client.getId(), date);
		
		for (ProductItem p : items) {
			aux.addProductItemDTO(p.getProduct().getProductId(), p.getQuantity());
		}
		
		return aux;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidInvoiceException {
		InvoiceDAO.save(this);
		
	}

	public void modify() throws ConnectionException, AccessException, InvalidInvoiceException {
		InvoiceDAO.modify(this);
	}
	
}
