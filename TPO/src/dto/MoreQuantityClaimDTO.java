package dto;

import java.util.LinkedList;
import java.util.List;

public class MoreQuantityClaimDTO extends ClaimDTO {

	private int invoiceId;
	private List<ProductItemDTO> products = new LinkedList<>();
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
	
	public void addProductItemDTO(ProductItemDTO productItem) {
		products.add(new ProductItemDTO(productItem.getProduct(), productItem.getQuantity()));
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
	
	@Override
	List<TransitionDTO> getTransitions() {
		return transitions;
	}

	@Override
	void addTransition(TransitionDTO dto) {
		transitions.add(dto);
	}
	
	
}
