package backEnd;

import dto.ProductDTO;

public class Product {

	private static int productCount = 0;
	
	private String title;
	private String description;
	private float price;
	private int productId;
	
	public Product(String title, String description, float price) {
		this.productId = productCount++;
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void modify(String title, String description, float price) {
		
		if(title != null) {			
			this.title = title;
		}
		if (description != null) {			
			this.description = description;
		}
		if (price >= 0) {			
			this.price = price;
		}
	}
	
	public ProductDTO toDTO() {
		return new ProductDTO(productId, title, description, price);
	}
}
