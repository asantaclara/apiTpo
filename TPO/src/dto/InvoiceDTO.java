package dto;

import java.util.Date;
import java.util.List;

public class InvoiceDTO {

	private int invoiceId;
	private List<ProductItemDTO> productItems;
	private String clientCuit;
	private Date date;
	
	public InvoiceDTO(int invoiceId, String clientCuit, Date date) {
		this.invoiceId = invoiceId;
		this.clientCuit = clientCuit;
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
	public String getClientCuit() {
		return clientCuit;
	}
	public Date getDate() {
		return date;
	}
	
	
}

