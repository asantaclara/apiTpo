package dto;

public class ProductItemDTO {

	private ProductDTO product;
	private int quantity;
	
	public ProductItemDTO(ProductDTO product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public ProductDTO getProduct() {
		return product;
	}
	public int getQuantity() {
		return quantity;
	}
	
	public String[] toDataRow() {
		String[] aux = new String[2];
		
		aux[0] = product.getProductId() + "-" + String.valueOf(product.getTitle());
		aux[1] = String.valueOf(quantity);
		
		return aux;
	}
}
