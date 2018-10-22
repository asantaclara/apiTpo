package backEnd;

import dao.ProductDAO;
import dto.ProductDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidProductException;

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
	
	public void modify(String title, String description, float price) throws ConnectionException, AccessException, InvalidProductException {
		
		if(title != null) {			
			this.title = title;
		}
		if (description != null) {			
			this.description = description;
		}
		if (price >= 0) {			
			this.price = price;
		}
		new ProductDAO().modify(this);
	}
	
	public ProductDTO toDTO() {
		return new ProductDTO(productId, title, description, price);
	}
	
	public void saveInDB() throws ConnectionException, AccessException, InvalidProductException {
		new ProductDAO().save(this);
	}
	
	public void modifyInDB() throws ConnectionException, AccessException, InvalidProductException {
		new ProductDAO().modify(this);
	}
	

	public void deactivateProduct() throws ConnectionException, AccessException, InvalidProductException {
		activeProduct = false;
		new ProductDAO().modify(this);
		
	}
	
	public boolean isActive() {
		return activeProduct;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}

	public boolean isActiveProduct() {
		return activeProduct;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	
}
