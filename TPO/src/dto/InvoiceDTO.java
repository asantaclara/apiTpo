package dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class InvoiceDTO {

	private int invoiceId;
	private List<ProductItemDTO> productItems = new LinkedList<>();
	private int clientId;
	private Date date;
	
//	int invoiceId, int clientId, Date date
	
	public InvoiceDTO() {
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public void setDate(Date date) {
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

