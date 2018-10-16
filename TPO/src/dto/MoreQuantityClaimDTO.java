package dto;

import java.util.LinkedList;
import java.util.List;

public class MoreQuantityClaimDTO extends ClaimDTO {

	private int invoiceId;
	private List<ProductItemDTO> products;
	private String claimType;
	
	public MoreQuantityClaimDTO(int claimId, int clientId, String description, String claimType, int invoiceId) {
		super(claimId, clientId, description);
		this.invoiceId = invoiceId;
		products = new LinkedList<>();
		
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
