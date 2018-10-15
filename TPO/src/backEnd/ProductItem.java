package backEnd;

import dto.ProductItemDTO;

public class ProductItem {

	private Product product;
	private int quantity;
	
	public ProductItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public ProductItemDTO toDTO() {
		return new ProductItemDTO(product.getProductId(), quantity);
	}
	
	//Tengo que implementar el comparador
}
