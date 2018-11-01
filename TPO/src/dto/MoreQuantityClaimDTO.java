package dto;

import java.util.List;

public class MoreQuantityClaimDTO extends ClaimDTO {

	private int invoiceId;
	private List<ProductItemDTO> products;
	private String claimType;
	
//	int claimId, int clientId, String description, String claimType, int invoiceId, Date date
	
	public MoreQuantityClaimDTO() {
		super();
	}
	
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	public void addProductItemDTO(int productId, int quantity) {
		products.add(new ProductItemDTO(productId, quantity));
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
