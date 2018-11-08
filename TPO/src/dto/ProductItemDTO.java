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
	
	public String[] toDataRow() {
		String[] aux = new String[2];
		
		aux[0] = String.valueOf(productId);
		aux[1] = String.valueOf(quantity);
		
		return aux;
	}
}
