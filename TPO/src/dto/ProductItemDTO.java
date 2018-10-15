package dto;

public class ProductItemDTO {

	private int productId;
	private int quantity;
	
	public ProductItemDTO(int productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public int getProductId() {
		return productId;
	}
	public int getQuantity() {
		return quantity;
	}
	
}
