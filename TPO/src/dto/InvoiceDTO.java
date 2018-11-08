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
	
	public void addProductItemDTO(ProductDTO product, int quantity) {
		productItems.add(new ProductItemDTO(product, quantity));
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
	
	public String[] toDataRow() {
		String[] aux = new String[3];
		
		aux[0] = String.valueOf(invoiceId);
		aux[1] = date.toString();
		aux[1] = String.valueOf(clientId);
		
		return aux;
	}
	
}

