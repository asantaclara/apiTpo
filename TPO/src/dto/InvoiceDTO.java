package dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class InvoiceDTO {

	private int invoiceId;
	private List<ProductItemDTO> productItems = new LinkedList<>();
	private int clientId;
	private Date date;
	
	public InvoiceDTO(int invoiceId, int clientId, Date date) {
		this.invoiceId = invoiceId;
		this.clientId = clientId;
		this.date = date;
	}

	public void addProductItemDTO(int productId, int quantity) {
		productItems.add(new ProductItemDTO(productId, quantity));
	}
	
	public int getInvoiceId() {
		return invoiceId;
	}
	public List<ProductItemDTO> getProductItems() {
		return productItems;
	}
	public int getClientId() {
		return clientId;
	}
	public Date getDate() {
		return date;
	}
	
	
}

