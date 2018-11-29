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
	public List<TransitionDTO> getTransitions() {
		return transitions;
	}

	@Override
	public void addTransition(TransitionDTO dto) {
		transitions.add(dto);
	}

	public String[] toDataRow() {
		String[] aux = new String[6];
		
		aux[0] = String.valueOf(claimId);
		aux[1] = String.valueOf(clientId);
		aux[2] = date.toString();
		aux[3] = description;
		aux[4] = state;
		aux[5] = claimType;
		
		return aux;
	}
	
	
}
