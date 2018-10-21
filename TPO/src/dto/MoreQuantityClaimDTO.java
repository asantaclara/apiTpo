package dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MoreQuantityClaimDTO extends ClaimDTO {

	private int invoiceId;
	private List<ProductItemDTO> products;
	private String claimType;
	
	public MoreQuantityClaimDTO(int claimId, int clientId, String description, String claimType, int invoiceId, Date date) {
		super(claimId, clientId, description, date);
		this.invoiceId = invoiceId;
		products = new LinkedList<>();
		this.claimType = claimType;
		
	}
	
	public void addProductItemDTO(ProductItemDTO productItem) {
		products.add(productItem);
	}
	
	public int getInvoiceId() {
		return invoiceId;
	}
	public List<ProductItemDTO> getProducts() {
		return products;
	}
	
	public String getClaimType() {
		return claimType;
	}
	
	
}
