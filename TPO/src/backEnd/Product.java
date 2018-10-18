package backEnd;

import dto.ProductDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class Product {
	
	private String title;
	private String description;
	private float price;
	private int productId=0;
	private boolean activeProduct;
	
	public Product(String title, String description, float price) {
		this.title = title;
		this.description = description;
		this.price = price;
		activeProduct = true;
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
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}

	public void deactivateProduct() {
		activeProduct = false;
		
	}
	
	public boolean isActive() {
		return activeProduct;
	}
}
