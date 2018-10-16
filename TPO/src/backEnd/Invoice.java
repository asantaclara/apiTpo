package backEnd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dto.InvoiceDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class Invoice {

	private static int invoiceCount = 0;
	
	private int invoiceId;
	private List<ProductItem> items;
	private Client client;
	private Date date;
	private boolean activeInvoice;
	
	public Invoice(Client client) {
		this.client = client;
		this.date = Calendar.getInstance().getTime(); //Intento guardar la fecha actual en la variable date, tengo que ver para solo me aguerda ddmmyyyy
		this.invoiceId = ++invoiceCount;
		activeInvoice = true;
	}
	
	public void addProductItem(Product product, int quantity) {
		items.add(new ProductItem(product, quantity));
	}
	
	public int getId() {
		return invoiceId;
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
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}
}
